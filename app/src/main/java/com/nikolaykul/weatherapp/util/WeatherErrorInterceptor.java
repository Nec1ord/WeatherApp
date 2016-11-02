package com.nikolaykul.weatherapp.util;

import com.jayway.jsonpath.JsonPath;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class WeatherErrorInterceptor implements Interceptor {

    @Override public Response intercept(Chain chain) throws IOException {
        final Response response = chain.proceed(chain.request());
        final String body = response.body().source().readUtf8();
        final int statusCode = JsonPath.parse(body).read("$.cod", Integer.class);
        if (statusCode >= 400) {
            throw new RuntimeException(body);
        }
        return response;
    }

}