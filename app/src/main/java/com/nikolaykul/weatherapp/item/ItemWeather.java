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
    public String description;
    public double pressure;
    public double humidity;

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
            description = weather.description;
        }
        pressure = forecast.pressure;
        humidity = forecast.humidity;
    }

    @Override public String toString() {
        return String.format(Locale.getDefault(),
                "ts: %d, min: %f, max: %f, icon: %s, title: %s, pressure: %f, humidity: %f",
                timestamp, tempMax, tempMin, icon, title, pressure, humidity);
    }
}