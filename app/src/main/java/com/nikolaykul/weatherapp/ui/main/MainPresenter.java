package com.nikolaykul.weatherapp.ui.main;

import com.nikolaykul.weatherapp.data.remote.WeatherApi;
import com.nikolaykul.weatherapp.di.activity.PerActivity;
import com.nikolaykul.weatherapp.ui.base.RxPresenter;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@PerActivity
public class MainPresenter extends RxPresenter<MainMvpView> {
    private WeatherApi mApi;

    @Inject public MainPresenter(WeatherApi api) {
        mApi = api;
        Timber.i("Created");
    }

    @Override protected void onResume() {
        Timber.i("onResume");
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        Timber.i("onDestroy");
    }

    public void loadTodayForecast() {

        // TODO: handle errors

        mApi.fetchForecast()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(getMvpView()::showLoading)
                .doAfterTerminate(getMvpView()::hideLoading)
                .map(forecastRequestModel -> forecastRequestModel.forecastRequest.textForecast.forecasts)
                .subscribe(
                        getMvpView()::showTodayForecast,
                        throwable -> {
                            Timber.e(throwable, "Some error occurred");
                        });
    }

}
