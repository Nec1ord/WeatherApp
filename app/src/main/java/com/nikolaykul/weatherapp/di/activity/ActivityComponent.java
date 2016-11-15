package com.nikolaykul.weatherapp.di.activity;

import com.nikolaykul.weatherapp.di.application.ApplicationComponent;
import com.nikolaykul.weatherapp.di.scope.PerActivity;
import com.nikolaykul.weatherapp.ui.main.MainActivity;
import com.nikolaykul.weatherapp.ui.search.SuggestSearchView;
import com.nikolaykul.weatherapp.ui.single_weather.SingleWeatherActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(SingleWeatherActivity activity);

    void inject(SuggestSearchView view);
}
