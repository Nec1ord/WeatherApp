package com.nikolaykul.weatherapp.ui.main;

import com.nikolaykul.weatherapp.item.ItemWeather;
import com.nikolaykul.weatherapp.ui.base.view.NetworkMvpView;
import com.nikolaykul.weatherapp.ui.base.view.PermissionMvpView;

import java.util.List;

public interface MainMvpView extends NetworkMvpView, PermissionMvpView {

    void showTodayForecast(List<ItemWeather> forecasts);

    void askLocationPermissions();

    void askToEnableGps();

    void showCity(String city);

}
