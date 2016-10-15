package com.nikolaykul.weatherapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Locale;

public class DayForecast {

    @SerializedName("dt")
    public long timestamp;

    @SerializedName("temp")
    public ForecastTemperature temperature;

    @SerializedName("humidity")
    public int humidity;

    @SerializedName("weather")
    public List<ForecastWeather> weatherList;

    @Override public String toString() {
        String weatherStr = "";
        for (ForecastWeather w : weatherList) {
            weatherStr += String.format("{ title: %s, icon: %s }", w.title, w.icon);
        }
        return String.format(Locale.getDefault(),
                "timestamp: %d, temp.min: %f, temp.max: %f, humidity: %d, weather: [%s]\n",
                timestamp, temperature.min, temperature.max, humidity, weatherStr);
    }
}
