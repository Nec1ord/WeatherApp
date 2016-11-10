package com.nikolaykul.weatherapp.ui.main;

import com.google.android.gms.common.api.Status;
import com.nikolaykul.weatherapp.data.model.forecast.Forecast;
import com.nikolaykul.weatherapp.ui.base.view.NetworkMvpView;

import java.util.List;

public interface MainMvpView extends NetworkMvpView {

    void showTodayForecast(List<Forecast> forecasts);

    void askToEnableGps(Status status);

    void showCity(String city);

}
