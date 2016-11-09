package com.nikolaykul.weatherapp.view.listener;

import com.nikolaykul.weatherapp.data.model.forecast.Forecast;

public interface OnForecastClickListener {
    void onForecastClicked(Forecast item);
}