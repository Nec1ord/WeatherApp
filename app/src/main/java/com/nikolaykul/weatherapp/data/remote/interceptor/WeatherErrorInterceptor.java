package com.nikolaykul.weatherapp.data.remote.interceptor;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.nikolaykul.weatherapp.data.remote.error.PlaceNotFoundThrowable;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class WeatherErrorInterceptor implements Interceptor {
    private final Configuration mConfiguration;

    public WeatherErrorInterceptor() {
        mConfiguration = Configuration
                .defaultConfiguration()
                .addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
    }

    @Override public Response intercept(Chain chain) throws IOException {
        final Response response = chain.proceed(chain.request());
        final String body = response.body().source().readUtf8();
        final Integer statusCode = JsonPath
                .using(mConfiguration)
                .parse(body)
                .read("$.cod", Integer.class);
        if (null != statusCode && statusCode >= 400) {
            switch (statusCode) {
                case 404:
                    throw new PlaceNotFoundThrowable();
                default:
                    throw new RuntimeException(body);
            }
        }
        return chain.proceed(chain.request());
    }

}