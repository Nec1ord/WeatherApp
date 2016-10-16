package com.nikolaykul.weatherapp.data.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {

    @SerializedName("dt")
    public long timestamp;

    @SerializedName("temp")
    public ForecastTemperature temperature;

    @SerializedName("weather")
    public List<ForecastWeather> weatherList;

    @SerializedName("pressure")
    public double pressure;

    @SerializedName("humidity")
    public double humidity;

}
