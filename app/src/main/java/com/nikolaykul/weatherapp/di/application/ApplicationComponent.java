package com.nikolaykul.weatherapp.di.application;

import com.nikolaykul.weatherapp.StubApi;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = NetworkModule.class)
public interface ApplicationComponent {
    StubApi provideApi();
}
