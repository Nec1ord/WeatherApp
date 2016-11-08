package com.nikolaykul.weatherapp.di.application;

import android.app.Application;
import android.content.Context;

import com.nikolaykul.weatherapp.di.qualifier.AppContext;
import com.nikolaykul.weatherapp.util.RxLocationManager;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

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

    @Provides
    @Singleton
    RxPermissions provideRxPermissions(@AppContext Context context) {
        return RxPermissions.getInstance(context);
    }

    @Provides
    @Singleton
    ReactiveLocationProvider provideRxLocation(@AppContext Context context) {
        return new ReactiveLocationProvider(context);
    }

    @Provides
    @Singleton
    RxLocationManager provideRxLocationManager(RxPermissions rxPermissions,
                                               ReactiveLocationProvider rxLocation) {
        return new RxLocationManager(rxPermissions, rxLocation);
    }

}
