package com.nikolaykul.weatherapp.di.activity;

import com.nikolaykul.weatherapp.di.application.ApplicationComponent;
import com.nikolaykul.weatherapp.di.scope.PerActivity;
import com.nikolaykul.weatherapp.ui.main.MainActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
}
