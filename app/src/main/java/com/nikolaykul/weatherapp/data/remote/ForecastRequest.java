package com.nikolaykul.weatherapp.data.remote;

import com.google.gson.annotations.SerializedName;

public class ForecastRequest {
    @SerializedName("txt_forecast")
    public TextForecast textForecast;
}