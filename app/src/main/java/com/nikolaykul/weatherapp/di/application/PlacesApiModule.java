package com.nikolaykul.weatherapp.di.application;

import android.content.Context;

import com.nikolaykul.weatherapp.data.remote.GooglePlacesApi;
import com.nikolaykul.weatherapp.util.Const;

import java.io.File;

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
class PlacesApiModule {

    @Provides
    @PlacesApiQualifier
    Cache provideCache(@AppContext Context context) {
        final File cacheDir = new File(context.getCacheDir(), "cachePlacesApi");
        return new Cache(cacheDir, 10 * 1024 * 1024);
    }

    @Provides
    @PlacesApiQualifier
    OkHttpClient provideClient(@PlacesApiQualifier Cache cache) {
        final Interceptor authInterceptor = chain -> {
            final Request origin = chain.request();
            final HttpUrl url = origin.url().newBuilder()
                    .addQueryParameter(Const.API_PLACES_KEY_NAME, Const.API_PLACES_KEY_VALUE)
                    .addQueryParameter(Const.API_PLACES_TYPES_NAME, Const.API_PLACES_TYPES_VALUE)
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
    @PlacesApiQualifier
    Retrofit provideRetrofit(@PlacesApiQualifier OkHttpClient client,
                             Converter.Factory converterFactory,
                             CallAdapter.Factory callAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(Const.API_PLACES_HOST)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    GooglePlacesApi provideApi(@PlacesApiQualifier Retrofit retrofit) {
        return retrofit.create(GooglePlacesApi.class);
    }

}
