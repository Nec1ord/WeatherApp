package com.nikolaykul.weatherapp.data.model;

import com.google.gson.annotations.SerializedName;

public class ForecastTemperature {

    @SerializedName("min")
    public double min;

    @SerializedName("max")
    public double max;

}