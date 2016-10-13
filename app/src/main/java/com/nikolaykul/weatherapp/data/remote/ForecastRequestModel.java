package com.nikolaykul.weatherapp.data.remote;

import com.google.gson.annotations.SerializedName;

public class ForecastRequestModel {

    @SerializedName("forecast")
    public ForecastRequest forecastRequest;

}