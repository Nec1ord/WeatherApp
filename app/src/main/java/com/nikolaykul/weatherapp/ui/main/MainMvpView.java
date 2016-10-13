package com.nikolaykul.weatherapp.ui.main;

import com.nikolaykul.weatherapp.data.model.Forecast;
import com.nikolaykul.weatherapp.ui.base.NetworkMvpView;

import java.util.List;

public interface MainMvpView extends NetworkMvpView {
    void showTodayForecast(List<Forecast> forecasts);
}
