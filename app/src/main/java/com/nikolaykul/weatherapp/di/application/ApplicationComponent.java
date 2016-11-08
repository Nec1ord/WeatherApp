package com.nikolaykul.weatherapp.di.application;

import android.content.Context;
import android.location.LocationManager;

import com.nikolaykul.weatherapp.data.remote.GooglePlacesApi;
import com.nikolaykul.weatherapp.data.remote.WeatherApi;
import com.nikolaykul.weatherapp.di.qualifier.AppContext;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class,
        NetworkModule.class,
        PlacesApiModule.class,
        WeatherApiModule.class})
public interface ApplicationComponent {

    @AppContext Context context();

    WeatherApi api();

    GooglePlacesApi googleApi();

    LocationManager locationManager();

    RxPermissions rxPermissions();

}
