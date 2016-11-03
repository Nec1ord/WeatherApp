package com.nikolaykul.weatherapp.di.application;

import android.content.Context;
import android.net.ConnectivityManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nikolaykul.weatherapp.data.model.PlacesModel;
import com.nikolaykul.weatherapp.data.remote.GooglePlacesApi;
import com.nikolaykul.weatherapp.data.remote.constant.PlacesApiConst;
import com.nikolaykul.weatherapp.data.remote.interceptor.NetworkErrorInterceptor;
import com.nikolaykul.weatherapp.data.remote.interceptor.QueryInterceptor;
import com.nikolaykul.weatherapp.data.remote.mapper.PlacesMapper;
import com.nikolaykul.weatherapp.di.qualifier.AppContext;
import com.nikolaykul.weatherapp.di.qualifier.PlacesApiQualifier;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

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
    List<Interceptor> provideInterceptors(ConnectivityManager connectivityManager) {
        final HashMap<String, String> queryMap = new HashMap<>(2);
        queryMap.put(PlacesApiConst.KEY_NAME, PlacesApiConst.KEY_VALUE);
        queryMap.put(PlacesApiConst.TYPES_NAME, PlacesApiConst.TYPES_VALUE);

        final LinkedList<Interceptor> interceptors = new LinkedList<>();
        interceptors.add(new QueryInterceptor(queryMap));
        interceptors.add(new NetworkErrorInterceptor(connectivityManager));
        interceptors.add(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC));
        return interceptors;
    }

    @Provides
    @PlacesApiQualifier
    OkHttpClient provideClient(@PlacesApiQualifier Cache cache,
                               @PlacesApiQualifier List<Interceptor> interceptors) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder().cache(cache);
        Observable.from(interceptors)
                .subscribeOn(Schedulers.immediate())
                .subscribe(builder::addInterceptor);
        return builder.build();
    }

    @Provides
    @PlacesApiQualifier
    Gson provideGson(PlacesMapper placesMapper) {
        return new GsonBuilder()
                .registerTypeAdapter(PlacesModel.class, placesMapper)
                .create();
    }

    @Provides
    @PlacesApiQualifier
    Converter.Factory provideConverterFactory(@PlacesApiQualifier Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @PlacesApiQualifier
    Retrofit provideRetrofit(@PlacesApiQualifier OkHttpClient client,
                             @PlacesApiQualifier Converter.Factory converterFactory,
                             CallAdapter.Factory callAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(PlacesApiConst.HOST)
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
