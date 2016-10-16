package com.nikolaykul.weatherapp.data.remote;

import com.google.gson.annotations.SerializedName;
import com.nikolaykul.weatherapp.data.model.Prediction;

import java.util.List;

public class GoogleRequest {

    @SerializedName("predictions")
    public List<Prediction> predictions;

}
