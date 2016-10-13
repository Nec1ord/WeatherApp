package com.nikolaykul.weatherapp.data.remote;

import com.google.gson.annotations.SerializedName;
import com.nikolaykul.weatherapp.data.model.Forecast;

import java.util.List;

public class TextForecast {
    @SerializedName("forecastday")
    public List<Forecast> forecasts;
}