package com.nikolaykul.weatherapp.data.remote.model;

import com.google.gson.annotations.SerializedName;
import com.nikolaykul.weatherapp.data.model.forecast.Forecast;
import com.nikolaykul.weatherapp.data.model.forecast.ForecastCity;

import java.util.List;

public class ForecastRequest {

    @SerializedName("city")
    public ForecastCity city;

    @SerializedName("list")
    public List<Forecast> forecasts;

}