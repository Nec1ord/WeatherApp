package com.nikolaykul.weatherapp.di.application;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.JsonPath;
import com.nikolaykul.weatherapp.data.model.WeatherModel;
import com.nikolaykul.weatherapp.data.remote.WeatherApi;
import com.nikolaykul.weatherapp.data.remote.WeatherApiConst;
import com.nikolaykul.weatherapp.data.remote.adapter.WeatherMapper;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        // TODO: inject interceptors
        final Interceptor queryInterceptor = chain -> {
            final Request origin = chain.request();
            final HttpUrl url = origin.url().newBuilder()
                    .addQueryParameter(WeatherApiConst.KEY_NAME, WeatherApiConst.KEY_VALUE)
                    .addQueryParameter(WeatherApiConst.METRIC_NAME, WeatherApiConst.METRIC_VALUE)
                    .build();
            final Request request = origin.newBuilder()
                    .url(url)
                    .build();
            return chain.proceed(request);
        };
        final Interceptor errorNotFoundInterceptor = chain -> {
            final Response response = chain.proceed(chain.request());
            final String body = response.body().source().readUtf8();
            final int statusCode = JsonPath.parse(body).read("$.cod", Integer.class);
            if (statusCode >= 400) {
                throw new RuntimeException(body);
            }
            return response;
        };
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(queryInterceptor)
                .addInterceptor(errorNotFoundInterceptor)
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