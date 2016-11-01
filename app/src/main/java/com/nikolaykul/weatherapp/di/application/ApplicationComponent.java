package com.nikolaykul.weatherapp.di.application;

import android.content.Context;
import android.location.LocationManager;

import com.nikolaykul.weatherapp.data.remote.GooglePlacesApi;
import com.nikolaykul.weatherapp.data.remote.WeatherApi;
import com.nikolaykul.weatherapp.util.NetworkManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, GooglePlacesApiModule.class, ApplicationModule.class})
public interface ApplicationComponent {

    @AppContext Context context();

    WeatherApi api();

    GooglePlacesApi googleApi();

    LocationManager locationManager();

    NetworkManager networkManager();

}
