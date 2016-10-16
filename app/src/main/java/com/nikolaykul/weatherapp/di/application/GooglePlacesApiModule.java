package com.nikolaykul.weatherapp.di.application;

import android.content.Context;

import com.nikolaykul.weatherapp.data.remote.GooglePlacesApi;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

@Module
class GooglePlacesApiModule {
    private static final String API_NAME = "googleApi";

    @Provides
    @Singleton
    @Named(API_NAME)
    Cache provideCache(@AppContext Context context) {
        final File cacheDir = new File(context.getCacheDir(), "cacheGoogleApi");
        return new Cache(cacheDir, 10 * 1024 * 1024);
    }

    @Provides
    @Singleton
    @Named(API_NAME)
    OkHttpClient provideClient(@Named(API_NAME) Cache cache) {
        final Interceptor authInterceptor = chain -> {
            final Request origin = chain.request();
            final HttpUrl url = origin.url().newBuilder()
                    .addQueryParameter(GooglePlacesApi.KEY_NAME, GooglePlacesApi.KEY_VALUE)
                    .addQueryParameter(GooglePlacesApi.TYPES_NAME, GooglePlacesApi.TYPES_VALUE)
                    .build();
            final Request request = origin.newBuilder()
                    .url(url)
                    .build();
            return chain.proceed(request);
        };
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(authInterceptor)
                .build();
    }

    @Provides
    @Singleton
    @Named(API_NAME)
    Retrofit provideRetrofit(@Named(API_NAME) OkHttpClient client,
                             Converter.Factory converterFactory,
                             CallAdapter.Factory callAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(GooglePlacesApi.HOST)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    GooglePlacesApi provideApi(@Named(API_NAME) Retrofit retrofit) {
        return retrofit.create(GooglePlacesApi.class);
    }

}
