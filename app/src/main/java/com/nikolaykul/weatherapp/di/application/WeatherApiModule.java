package com.nikolaykul.weatherapp.di.application;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nikolaykul.weatherapp.data.model.WeatherModel;
import com.nikolaykul.weatherapp.data.remote.WeatherApi;
import com.nikolaykul.weatherapp.data.remote.WeatherApiConst;
import com.nikolaykul.weatherapp.data.remote.adapter.WeatherMapper;
import com.nikolaykul.weatherapp.util.QueryInterceptior;
import com.nikolaykul.weatherapp.util.WeatherErrorInterceptor;

import java.io.File;
import java.util.HashMap;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class WeatherApiModule {

    @Provides
    @WeatherApiQualifier
    Cache provideCache(@AppContext Context context) {
        final File cacheDir = new File(context.getCacheDir(), "weatherCache");
        return new Cache(cacheDir, 10 * 1024 * 1024);
    }

    @Provides
    @WeatherApiQualifier
    OkHttpClient provideClient(@WeatherApiQualifier Cache cache) {
        final HashMap<String, String> queryMap = new HashMap<>(2);
        queryMap.put(WeatherApiConst.KEY_NAME, WeatherApiConst.KEY_VALUE);
        queryMap.put(WeatherApiConst.METRIC_NAME, WeatherApiConst.METRIC_VALUE);

        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new QueryInterceptior(queryMap))
                .addInterceptor(new WeatherErrorInterceptor())
                .build();
    }

    @Provides
    @WeatherApiQualifier
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(WeatherModel.class, new WeatherMapper())
                .create();
    }

    @Provides
    @WeatherApiQualifier
    Converter.Factory provideConverterFactory(@WeatherApiQualifier Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @WeatherApiQualifier
    Retrofit provideRetrofit(@WeatherApiQualifier OkHttpClient client,
                             @WeatherApiQualifier Converter.Factory converterFactory,
                             CallAdapter.Factory callAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(WeatherApiConst.HOST)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    WeatherApi provideApi(@WeatherApiQualifier Retrofit retrofit) {
        return retrofit.create(WeatherApi.class);
    }

}