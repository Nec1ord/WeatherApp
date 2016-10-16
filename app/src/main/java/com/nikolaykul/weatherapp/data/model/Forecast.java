package com.nikolaykul.weatherapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {

    @SerializedName("dt")
    public long timestamp;

    @SerializedName("temp")
    public ForecastTemperature temperature;

    @SerializedName("weather")
    public List<ForecastWeather> weatherList;

}
