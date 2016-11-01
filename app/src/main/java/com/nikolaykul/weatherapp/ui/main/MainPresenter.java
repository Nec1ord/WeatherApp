package com.nikolaykul.weatherapp.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

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
public class MainPresenter extends RxPresenter<MainMvpView> implements LocationListener {
    private static final int FORECAST_COUNT = 7;
    private final LocationManager mLocationManager;
    private final Context mContext;
    private final WeatherApi mApi;
    private String mCity;

    @Inject public MainPresenter(Context context, WeatherApi api) {
        mContext = context;
        mApi = api;
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
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

    @Override
    @SuppressWarnings({"ResourceType"})
    public void onLocationChanged(Location location) {
        // remove updates & try again
        if (!hasLocationPermissions()) {
            return;
        }
        mLocationManager.removeUpdates(this);
        loadTodayForecast();
    }

    @Override public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override public void onProviderEnabled(String s) {
    }

    @Override public void onProviderDisabled(String s) {
        getMvpView().askToEnableGps();
    }

    @SuppressWarnings({"ResourceType"})
    private Observable<ForecastRequest> fetchForecastFromLocation() {
        // check permissions
        if (!hasLocationPermissions()) {
            return null;
        }
        // check if gps enabled
        final String gpsProvider = LocationManager.GPS_PROVIDER;
        if (!mLocationManager.isProviderEnabled(gpsProvider)) {
//            getMvpView().hideLoading();
            getMvpView().askToEnableGps();
            return null;
        }
        // check location
        final Location location = mLocationManager.getLastKnownLocation(gpsProvider);
        if (null == location) {
            getMvpView().showError(R.string.error_location);
            mLocationManager.requestLocationUpdates(gpsProvider, 0, 0, this);
            return null;
        }
        // create request observable
        return mApi.fetchForecast(location.getLatitude(), location.getLongitude(), FORECAST_COUNT)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(request -> getMvpView().showCity(request.city.name));
    }

    private boolean hasLocationPermissions() {
        if (Build.VERSION.SDK_INT >= 23 &&
                getMvpView().checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            getMvpView().hideLoading();
            getMvpView().askLocationPermissions();
            return false;
        }
        return true;
    }

    private Observable<ForecastRequest> fetchForecastFromCity() {
        return mApi.fetchForecast(mCity, FORECAST_COUNT);
    }

    private void showError(Throwable t) {
        Timber.e(t, "Some error occurred");
        getMvpView().showError(R.string.error_default);
    }

}