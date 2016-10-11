package com.nikolaykul.weatherapp.ui.main;

import android.os.Bundle;

import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.StubApi;
import com.nikolaykul.weatherapp.di.activity.ActivityComponent;
import com.nikolaykul.weatherapp.ui.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {
    @Inject StubApi mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApi.stubApiCall();
    }

    @Override protected void injectSelf(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

}