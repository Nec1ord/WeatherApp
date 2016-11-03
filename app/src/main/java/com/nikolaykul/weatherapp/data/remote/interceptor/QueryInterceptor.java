package com.nikolaykul.weatherapp.data.remote.interceptor;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.schedulers.Schedulers;

public class QueryInterceptor implements Interceptor {
    private final Map<String, String> mQueries;

    public QueryInterceptor(Map<String, String> queries) {
        mQueries = queries;
    }

    @Override public Response intercept(Chain chain) throws IOException {
        final Request origin = chain.request();

        final HttpUrl.Builder builder = origin.url().newBuilder();
        Observable.from(mQueries.entrySet())
                .subscribeOn(Schedulers.immediate())
                .subscribe(entry -> builder.addQueryParameter(entry.getKey(), entry.getValue()));

        final Request request = origin.newBuilder()
                .url(builder.build())
                .build();
        return chain.proceed(request);
    }

}