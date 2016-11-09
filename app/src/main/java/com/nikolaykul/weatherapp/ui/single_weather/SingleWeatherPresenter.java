package com.nikolaykul.weatherapp.ui.single_weather;

import com.nikolaykul.weatherapp.di.scope.PerActivity;
import com.nikolaykul.weatherapp.ui.base.presenter.RxPresenter;

import javax.inject.Inject;

@PerActivity
public class SingleWeatherPresenter extends RxPresenter<SingleWeatherMvpView> {

    @Inject public SingleWeatherPresenter() {
    }

}
