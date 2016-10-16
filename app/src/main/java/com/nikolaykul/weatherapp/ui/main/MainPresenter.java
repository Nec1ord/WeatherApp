package com.nikolaykul.weatherapp.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.data.remote.WeatherApi;
import com.nikolaykul.weatherapp.data.remote.model.ForecastRequest;
import com.nikolaykul.weatherapp.di.activity.PerActivity;
import com.nikolaykul.weatherapp.item.ItemWeather;
import com.nikolaykul.weatherapp.ui.base.presenter.RxPresenter;
import com.nikolaykul.weatherapp.util.NetworkUtil;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@PerActivity
public class MainPresenter extends RxPresenter<MainMvpView> {
    private static final int FORECAST_COUNT = 7;
    private final Context mContext;
    private final WeatherApi mApi;
    private String mCity;

    @Inject public MainPresenter(Context context, WeatherApi api) {
        mContext = context;
        mApi = api;
    }

    public void setCity(String city) {
        mCity = city;
        getMvpView().showCity(city);
    }

    public void clearCity() {
        mCity = null;
        getMvpView().showCity(null);
    }

    public void loadTodayForecast() {
        if (!NetworkUtil.isNetworkEnabled(mContext)) {
            getMvpView().showError(R.string.error_network);
            return;
        }

        final Observable<ForecastRequest> apiObservable = mCity != null
                ? fetchForecastFromCity()
                : fetchForecastFromLocation();

        if (null == apiObservable) {
            return;
        }

        final Subscription sub = apiObservable
                .map(request -> request.forecasts)
                .flatMap(Observable::from)
                .map(ItemWeather::new)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(getMvpView()::showLoading)
                .doAfterTerminate(getMvpView()::hideLoading)
                .subscribe(
                        getMvpView()::showTodayForecast,
                        this::showError);
        addSubscription(sub);
    }

    private Observable<ForecastRequest> fetchForecastFromLocation() {
        final LocationManager locationManager =
                (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        // check permissions
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            getMvpView().hideLoading();
            getMvpView().askLocationPermissions();
            return null;
        }
        // check if gps enabled
        final String gpsProvider = LocationManager.GPS_PROVIDER;
        if (!locationManager.isProviderEnabled(gpsProvider)) {
            getMvpView().hideLoading();
            getMvpView().askToEnableGps();
            return null;
        }
        // create request observable
        final Location location = locationManager.getLastKnownLocation(gpsProvider);
        return mApi.fetchForecast(location.getLatitude(), location.getLongitude(), FORECAST_COUNT);
    }

    private Observable<ForecastRequest> fetchForecastFromCity() {
        return mApi.fetchForecast(mCity, FORECAST_COUNT);
    }

    private void showError(Throwable t) {
        Timber.e(t, "Some error occurred");
        getMvpView().showError(R.string.error_default);
    }

}