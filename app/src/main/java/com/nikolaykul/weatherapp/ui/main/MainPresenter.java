package com.nikolaykul.weatherapp.ui.main;

import android.Manifest;
import android.location.Location;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.nikolaykul.weatherapp.data.model.WeatherModel;
import com.nikolaykul.weatherapp.data.remote.WeatherApi;
import com.nikolaykul.weatherapp.data.remote.error.LocationProviderThrowable;
import com.nikolaykul.weatherapp.data.remote.error.PermissionThrowable;
import com.nikolaykul.weatherapp.di.scope.PerActivity;
import com.nikolaykul.weatherapp.item.ItemWeather;
import com.nikolaykul.weatherapp.ui.base.presenter.RxPresenter;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Inject;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@PerActivity
public class MainPresenter extends RxPresenter<MainMvpView> {
    private static final int FORECAST_COUNT = 7;
    private final WeatherApi mApi;
    private final RxPermissions mRxPermissions;
    private final ReactiveLocationProvider mRxLocation;
    private String mCity;

    @Inject public MainPresenter(WeatherApi api,
                                 RxPermissions rxPermissions,
                                 ReactiveLocationProvider rxLocation) {
        mApi = api;
        mRxPermissions = rxPermissions;
        mRxLocation = rxLocation;
    }

    public void onCitySelected(String city) {
        mCity = city;
        getMvpView().showCity(city);
        loadTodayForecast();
    }

    public void onGeoSelected() {
        mCity = null;
        getMvpView().showCity(null);
        loadTodayForecast();
    }

    public void loadTodayForecast() {
        final Observable<WeatherModel> apiObservable = mCity != null
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

    private Observable<WeatherModel> fetchForecastFromCity() {
        return mApi.fetchForecast(mCity, FORECAST_COUNT);
    }

    private Observable<WeatherModel> fetchForecastFromLocation() {
        return getLocation()
                .observeOn(Schedulers.io())
                .flatMap(location -> mApi.fetchForecast(
                        location.getLatitude(),
                        location.getLongitude(),
                        FORECAST_COUNT))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(request -> getMvpView().showCity(request.city));
    }

    @SuppressWarnings({"ResourceType"})
    private Observable<Location> getLocation() {
        final LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(7 * 1000) // 7 sec
                .setNumUpdates(1);
        return checkLocationPermissions()
                .flatMap(any -> checkLocationSettings(locationRequest))
                .flatMap(any -> mRxLocation.getLastKnownLocation())
                .switchIfEmpty(mRxLocation.getUpdatedLocation(locationRequest));
    }

    private Observable<Void> checkLocationPermissions() {
        return mRxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .flatMap(granted -> granted
                        ? Observable.just(null)
                        : Observable.error(new PermissionThrowable()));
    }

    private Observable<Void> checkLocationSettings(LocationRequest locationRequest) {
        final LocationSettingsRequest settingsRequest = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)
                .build();
        return mRxLocation.checkLocationSettings(settingsRequest)
                .map(result -> result.getStatus().getStatusCode())
                .map(statusCode -> statusCode == LocationSettingsStatusCodes.SUCCESS)
                .flatMap(isProviderEnabled -> isProviderEnabled
                        ? Observable.just(null)
                        : Observable.error(new LocationProviderThrowable()));
    }

    private void showError(Throwable t) {
        Timber.e(t, "MainPresenter");
        if (t instanceof LocationProviderThrowable) {
            getMvpView().askToEnableGps();
            return;
        }
        getMvpView().showError(t);
    }

}