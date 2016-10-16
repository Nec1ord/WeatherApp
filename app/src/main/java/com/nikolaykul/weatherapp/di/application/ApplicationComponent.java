package com.nikolaykul.weatherapp.di.application;

import android.content.Context;

import com.nikolaykul.weatherapp.data.remote.GooglePlacesApi;
import com.nikolaykul.weatherapp.data.remote.WeatherApi;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, GooglePlacesApiModule.class, ApplicationModule.class})
public interface ApplicationComponent {
    @AppContext Context context();

    WeatherApi api();

    GooglePlacesApi googleApi();
}
