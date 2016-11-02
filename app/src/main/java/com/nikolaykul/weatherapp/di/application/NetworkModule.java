package com.nikolaykul.weatherapp.di.application;

import android.content.Context;
import android.net.ConnectivityManager;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import com.nikolaykul.weatherapp.util.NetworkManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.CallAdapter;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

@Module
class NetworkModule {

    @Provides
    @Singleton
    Configuration provideGsonConfiguration() {
        return Configuration.builder()
                .mappingProvider(new GsonMappingProvider())
                .jsonProvider(new GsonJsonProvider())
                .build();
    }

    @Provides
    @Singleton
    CallAdapter.Factory provideCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    @Singleton
    ConnectivityManager provideConnectivityManager(@AppContext Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    NetworkManager provideNetworkManager(ConnectivityManager cm) {
        return new NetworkManager(cm);
    }

}
