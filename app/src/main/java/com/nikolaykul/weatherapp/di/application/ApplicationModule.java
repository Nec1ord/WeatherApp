package com.nikolaykul.weatherapp.di.application;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application mApp;

    public ApplicationModule(Application app) {
        mApp = app;
    }

    @Provides
    @AppContext
    Context provideApplicationContext() {
        return mApp;
    }

}
