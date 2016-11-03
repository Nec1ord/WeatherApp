package com.nikolaykul.weatherapp.di.application;

import android.content.Context;
import android.net.ConnectivityManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nikolaykul.weatherapp.data.model.WeatherModel;
import com.nikolaykul.weatherapp.data.remote.WeatherApi;
import com.nikolaykul.weatherapp.data.remote.constant.WeatherApiConst;
import com.nikolaykul.weatherapp.data.remote.interceptor.NetworkErrorInterceptor;
import com.nikolaykul.weatherapp.data.remote.interceptor.QueryInterceptor;
import com.nikolaykul.weatherapp.data.remote.interceptor.WeatherErrorInterceptor;
import com.nikolaykul.weatherapp.data.remote.mapper.WeatherMapper;
import com.nikolaykul.weatherapp.di.qualifier.AppContext;
import com.nikolaykul.weatherapp.di.qualifier.WeatherApiQualifier;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

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
    List<Interceptor> provideInterceptors(ConnectivityManager connectivityManager) {
        final HashMap<String, String> queryMap = new HashMap<>(2);
        queryMap.put(WeatherApiConst.KEY_NAME, WeatherApiConst.KEY_VALUE);
        queryMap.put(WeatherApiConst.METRIC_NAME, WeatherApiConst.METRIC_VALUE);

        final ArrayList<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new QueryInterceptor(queryMap));
        interceptors.add(new WeatherErrorInterceptor());
        interceptors.add(new NetworkErrorInterceptor(connectivityManager));
        return interceptors;
    }

    @Provides
    @WeatherApiQualifier
    OkHttpClient provideClient(@WeatherApiQualifier Cache cache,
                               @WeatherApiQualifier List<Interceptor> interceptors) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder().cache(cache);
        Observable.from(interceptors)
                .subscribeOn(Schedulers.immediate())
                .subscribe(builder::addInterceptor);
        return builder.build();
    }

    @Provides
    @WeatherApiQualifier
    Gson provideGson(WeatherMapper weatherMapper) {
        return new GsonBuilder()
                .registerTypeAdapter(WeatherModel.class, weatherMapper)
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