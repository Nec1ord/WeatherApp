package com.nikolaykul.weatherapp.util;

import android.Manifest;
import android.location.Location;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.nikolaykul.weatherapp.error.LocationProviderThrowable;
import com.nikolaykul.weatherapp.error.PermissionThrowable;
import com.tbruyelle.rxpermissions.RxPermissions;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.schedulers.Schedulers;

public class RxLocationManager {
    private static final long MAX_WAIT_TIME = 1000 * 60 * 2;       // 2 min
    private final RxPermissions mRxPermissions;
    private final ReactiveLocationProvider mRxLocation;

    public RxLocationManager(RxPermissions rxPermissions,
                             ReactiveLocationProvider rxLocation) {
        mRxPermissions = rxPermissions;
        mRxLocation = rxLocation;
    }

    @SuppressWarnings({"ResourceType"})
    public Observable<Location> getLocation() {
        final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        final LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(MAX_WAIT_TIME)
                .setNumUpdates(1);
        return checkLocationPermissions(permissions)
                .flatMap(any -> checkLocationSettings(locationRequest))
                .flatMap(any -> mRxLocation.getLastKnownLocation())
                .switchIfEmpty(mRxLocation.getUpdatedLocation(locationRequest))
                .observeOn(Schedulers.io());    // issue: https://github.com/mcharmas/Android-ReactiveLocation/issues/136
    }

    private Observable<Void> checkLocationPermissions(String... permissions) {
        return mRxPermissions
                .request(permissions)
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
                .map(LocationSettingsResult::getStatus)
                .flatMap(status -> LocationSettingsStatusCodes.SUCCESS == status.getStatusCode()
                        ? Observable.just(null)
                        : Observable.error(new LocationProviderThrowable(status)));
    }

}