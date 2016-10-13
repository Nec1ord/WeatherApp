package com.nikolaykul.weatherapp.ui.main;

import com.nikolaykul.weatherapp.data.remote.WeatherApi;
import com.nikolaykul.weatherapp.di.activity.PerActivity;
import com.nikolaykul.weatherapp.ui.base.presenter.RxPresenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@PerActivity
public class MainPresenter extends RxPresenter<MainMvpView> {
    private final WeatherApi mApi;

    @Inject public MainPresenter(WeatherApi api) {
        mApi = api;
    }

    public void loadTodayForecast() {

        // TODO: handle errors

        final Subscription sub = mApi.fetchForecast()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(getMvpView()::showLoading)
                .doAfterTerminate(getMvpView()::hideLoading)
                .map(forecastRequestModel ->
                        forecastRequestModel.forecastRequest.textForecast.forecasts)
                .subscribe(
                        getMvpView()::showTodayForecast,
                        throwable -> {
                            Timber.e(throwable, "Some error occurred");
                        });
        addSubscription(sub);
    }

}