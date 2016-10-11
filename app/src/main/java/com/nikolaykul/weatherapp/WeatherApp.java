package com.nikolaykul.weatherapp;

import android.app.Application;

import timber.log.Timber;

public class WeatherApp extends Application {

    @Override public void onCreate() {
        super.onCreate();
        initLogger();
    }

    private void initLogger() {
        if (BuildConfig.ENABLE_LOGGING) {
            Timber.plant(new Timber.DebugTree());
        } // else plant production tree
    }

}
