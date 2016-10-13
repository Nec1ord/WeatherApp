package com.nikolaykul.weatherapp.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.databinding.ActivityMainBinding;
import com.nikolaykul.weatherapp.di.activity.ActivityComponent;
import com.nikolaykul.weatherapp.ui.base.BaseMvpNetworkActivity;

import timber.log.Timber;

public class MainActivity extends BaseMvpNetworkActivity<MainMvpView, MainPresenter>
        implements MainMvpView {
    private ActivityMainBinding mBinding;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override protected void injectSelf(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override protected MainMvpView getMvpView() {
        return this;
    }

    @Override public void showWeather() {
        Timber.i("Should show weather");
    }

}