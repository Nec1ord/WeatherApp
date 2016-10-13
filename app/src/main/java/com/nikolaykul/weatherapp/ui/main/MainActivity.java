package com.nikolaykul.weatherapp.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.databinding.ActivityMainBinding;
import com.nikolaykul.weatherapp.di.activity.ActivityComponent;
import com.nikolaykul.weatherapp.ui.base.BaseMvpNetworkActivity;

import javax.inject.Inject;

import timber.log.Timber;

public class MainActivity extends BaseMvpNetworkActivity<MainPresenter, ActivityMainBinding>
        implements MainMvpView {
    @Inject protected MainPresenter mPresenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override protected void injectSelf(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override protected ActivityMainBinding createBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override protected MainPresenter getPresenter() {
        return mPresenter;
    }

    @Override public void showWeather() {
        Timber.i("Should show weather");
    }

}