package com.nikolaykul.weatherapp.di.application;

import android.content.Context;

import com.nikolaykul.weatherapp.data.WeatherApi;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, ApplicationModule.class})
public interface ApplicationComponent {
    @AppContext Context context();

    WeatherApi api();
}
