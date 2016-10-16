package com.nikolaykul.weatherapp.data.model;

import com.google.gson.annotations.SerializedName;

public class ForecastWeather {

    @SerializedName("main")
    public String title;

    @SerializedName("icon")
    public String icon;

    @SerializedName("description")
    public String description;

}
