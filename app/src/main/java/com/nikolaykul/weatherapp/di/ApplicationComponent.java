package com.nikolaykul.weatherapp.di;

import com.nikolaykul.weatherapp.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = NetworkModule.class)
public interface ApplicationComponent {

    void inject(MainActivity activity);

}
