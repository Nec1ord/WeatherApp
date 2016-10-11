package com.nikolaykul.weatherapp.ui.main;

import android.os.Bundle;

import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.di.activity.ActivityComponent;
import com.nikolaykul.weatherapp.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override protected void injectSelf(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

}