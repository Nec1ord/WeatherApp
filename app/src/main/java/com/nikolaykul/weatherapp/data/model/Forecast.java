package com.nikolaykul.weatherapp.data.model;

import com.google.gson.annotations.SerializedName;

public class Forecast {

    @SerializedName("period") private String period;

    @SerializedName("icon_url") private String iconUrl;

    @SerializedName("title") private String title;

    @SerializedName("fcttext") private String description;

    @Override public String toString() {
        return period + ", " +
                iconUrl + ", " +
                title + ", " +
                description + ", ";
    }
}
