package com.nikolaykul.weatherapp.data.remote;

import com.google.gson.annotations.SerializedName;
import com.nikolaykul.weatherapp.data.model.forecast.Forecast;

import java.util.List;

public class ForecastRequest {

    @SerializedName("list")
    public List<Forecast> forecasts;

}