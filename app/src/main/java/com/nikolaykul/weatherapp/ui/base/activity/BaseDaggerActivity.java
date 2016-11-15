package com.nikolaykul.weatherapp.ui.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nikolaykul.weatherapp.WeatherApp;
import com.nikolaykul.weatherapp.di.activity.ActivityComponent;
import com.nikolaykul.weatherapp.di.activity.ActivityModule;
import com.nikolaykul.weatherapp.di.activity.DaggerActivityComponent;
import com.nikolaykul.weatherapp.view.contract.HasComponent;

abstract class BaseDaggerActivity extends AppCompatActivity
        implements HasComponent<ActivityComponent> {

    private ActivityComponent mComponent;

    protected abstract void injectSelf(ActivityComponent activityComponent);

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComponent = createActivityComponent();
        injectSelf(mComponent);
    }

    @Override public ActivityComponent getComponent() {
        return mComponent;
    }

    private ActivityComponent createActivityComponent() {
        return DaggerActivityComponent.builder()
                .applicationComponent(WeatherApp.getAppComponent(this))
                .activityModule(new ActivityModule(this))
                .build();
    }

}