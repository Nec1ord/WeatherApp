package com.nikolaykul.weatherapp;

import android.app.Application;
import android.content.Context;

import com.nikolaykul.weatherapp.di.application.ApplicationComponent;
import com.nikolaykul.weatherapp.di.application.ApplicationModule;
import com.nikolaykul.weatherapp.di.application.DaggerApplicationComponent;

import timber.log.Timber;

public class WeatherApp extends Application {
    private ApplicationComponent mComponent;

    public static ApplicationComponent getAppComponent(Context context) {
        return ((WeatherApp) context.getApplicationContext()).mComponent;
    }

    @Override public void onCreate() {
        super.onCreate();
        initLogger();
        initDi();
    }

    private void initLogger() {
        if (BuildConfig.ENABLE_LOGGING) {
            Timber.plant(new Timber.DebugTree());
        } // else plant production tree
    }

    private void initDi() {
        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

}
