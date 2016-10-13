package com.nikolaykul.weatherapp.ui.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nikolaykul.weatherapp.WeatherApp;
import com.nikolaykul.weatherapp.di.activity.ActivityComponent;
import com.nikolaykul.weatherapp.di.activity.ActivityModule;
import com.nikolaykul.weatherapp.di.activity.DaggerActivityComponent;

abstract class BaseDaggerActivity extends AppCompatActivity {

    protected abstract void injectSelf(ActivityComponent activityComponent);

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectSelf(createActivityComponent());
    }

    private ActivityComponent createActivityComponent() {
        return DaggerActivityComponent.builder()
                .applicationComponent(WeatherApp.getAppComponent(this))
                .activityModule(new ActivityModule(this))
                .build();
    }

}
