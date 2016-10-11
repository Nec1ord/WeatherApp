package com.nikolaykul.weatherapp.di.application;

import android.util.Log;

import com.nikolaykul.weatherapp.StubApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class NetworkModule {

    @Provides
    @Singleton
    StubApi provideApi() {
        return () -> Log.i(getClass().getName(), "STUB API CALL!");
    }

}
