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
    private final LocationManager mLocationManager;
    private String mCity;

    @Inject public MainPresenter(Context context, WeatherApi api) {
        mApi = api;
        mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void setCity(String city) {
        mCity = city;
    }

    public void clearCity() {
        mCity = null;
    }

    public void loadTodayForecast() {
        if (!NetworkUtil.isNetworkEnabled(mContext)) {
            getMvpView().showError(R.string.error_network);
            return;
        }
        if (mCity != null) {
            fetchForecastFromCity();
        } else {
            findLocationAndFetch();
        }
    }

    private void findLocationAndFetch() {
        final String gpsProvider = LocationManager.GPS_PROVIDER;
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            getMvpView().hideLoading();
            getMvpView().askLocationPermissions();
            return;
        }
        if (!mLocationManager.isProviderEnabled(gpsProvider)) {
            getMvpView().hideLoading();
            getMvpView().askToEnableGps();
            return;
        }
        fetchForecastFromLocation(mLocationManager.getLastKnownLocation(gpsProvider));
    }

    private void fetchForecastFromLocation(Location location) {
        final Subscription sub =
                mApi.fetchForecast(location.getLatitude(), location.getLongitude(), FORECAST_COUNT)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(getMvpView()::showLoading)
                        .doAfterTerminate(getMvpView()::hideLoading)
                        .map(request -> request.forecasts)
                        .flatMap(Observable::from)
                        .map(ItemWeather::new)
                        .toList()
                        .subscribe(
                                getMvpView()::showTodayForecast,
                                this::showError);
        addSubscription(sub);
    }

    private void fetchForecastFromCity() {
        final Subscription sub = mApi.fetchForecast(mCity, FORECAST_COUNT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(getMvpView()::showLoading)
                .doAfterTerminate(getMvpView()::hideLoading)
                .map(request -> request.forecasts)
                .flatMap(Observable::from)
                .map(ItemWeather::new)
                .toList()
                .subscribe(
                        getMvpView()::showTodayForecast,
                        this::showError);
        addSubscription(sub);
    }

    private void showError(Throwable t) {
        Timber.e(t, "Some error occurred");
        getMvpView().showError(R.string.error_default);
    }

}