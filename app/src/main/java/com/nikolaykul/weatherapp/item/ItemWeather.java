package com.nikolaykul.weatherapp.item;

import com.nikolaykul.weatherapp.data.model.Forecast;
import com.nikolaykul.weatherapp.data.model.ForecastWeather;

import java.util.Locale;

public class ItemWeather {
    public long timestamp;
    public double tempMax;
    public double tempMin;
    public String icon;
    public String title;

    public ItemWeather() {
    }

    public ItemWeather(Forecast forecast) {
        timestamp = forecast.timestamp * 1000;
        tempMax = forecast.temperature.max;
        tempMin = forecast.temperature.min;
        final ForecastWeather weather = forecast.weatherList.get(0);
        if (weather != null) {
            icon = weather.icon;
            title = weather.title;
        }
    }

    @Override public String toString() {
        return String.format(Locale.getDefault(),
                "ts: %d, min: %f, max: %f, icon: %s, title: %s",
                timestamp, tempMax, tempMin, icon, title);
    }
}