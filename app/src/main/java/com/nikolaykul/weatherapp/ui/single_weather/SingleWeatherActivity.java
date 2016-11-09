package com.nikolaykul.weatherapp.ui.single_weather;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.databinding.ActivitySingleWeatherBinding;
import com.nikolaykul.weatherapp.di.activity.ActivityComponent;
import com.nikolaykul.weatherapp.ui.base.activity.BaseMvpActivity;

public class SingleWeatherActivity
        extends BaseMvpActivity<SingleWeatherMvpView, SingleWeatherPresenter>
        implements SingleWeatherMvpView {
    private ActivitySingleWeatherBinding mBinding;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_single_weather);
    }

    @Override protected SingleWeatherMvpView getMvpView() {
        return this;
    }

    @Override protected void injectSelf(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

}
