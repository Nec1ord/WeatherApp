package com.nikolaykul.weatherapp.ui.main;

import com.nikolaykul.weatherapp.di.activity.PerActivity;
import com.nikolaykul.weatherapp.ui.base.Presenter;

import javax.inject.Inject;

import timber.log.Timber;

@PerActivity
public class MainPresenter extends Presenter<MainMvpView> {

    @Inject public MainPresenter() {
        Timber.i("Created");
    }

    @Override protected void onResume() {
        Timber.i("onResume");
    }

    @Override protected void onDestroy() {
        Timber.i("onDestroy");
    }

}
