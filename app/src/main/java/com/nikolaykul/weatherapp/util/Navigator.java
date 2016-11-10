package com.nikolaykul.weatherapp.util;

import android.content.Context;
import android.content.Intent;

import com.nikolaykul.weatherapp.di.scope.PerActivity;
import com.nikolaykul.weatherapp.ui.single_weather.SingleWeatherActivity;

import javax.inject.Inject;

@PerActivity
public class Navigator {
    private final Context mContext;

    @Inject public Navigator(Context context) {
        mContext = context;
    }

    public void navigateToSingleWeather() {
        mContext.startActivity(new Intent(mContext, SingleWeatherActivity.class));
    }

}