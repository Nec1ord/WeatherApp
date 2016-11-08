package com.nikolaykul.weatherapp.ui.main;

import com.nikolaykul.weatherapp.data.model.WeatherModel;
import com.nikolaykul.weatherapp.data.remote.WeatherApi;
import com.nikolaykul.weatherapp.di.scope.PerActivity;
import com.nikolaykul.weatherapp.error.LocationProviderThrowable;
import com.nikolaykul.weatherapp.item.ItemWeather;
import com.nikolaykul.weatherapp.ui.base.presenter.RxPresenter;
import com.nikolaykul.weatherapp.util.RxLocationManager;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@PerActivity
public class MainPresenter extends RxPresenter<MainMvpView> {
    private static final int FORECAST_COUNT = 7;
    private final RxLocationManager mRxLocationManager;
    private final WeatherApi mApi;
    private String mCity;

    @Inject public MainPresenter(RxLocationManager rxLocationManager,
                                 WeatherApi api) {
        mRxLocationManager = rxLocationManager;
        mApi = api;
    }

    public void onCitySelected(String city) {
        mCity = city;
        getMvpView().showCity(city);
        loadTodayForecast();
    }

    public void onGeoSelected() {
        mCity = null;
        getMvpView().showCity(null);
        loadTodayForecast();
    }

    public void loadTodayForecast() {
        final Observable<WeatherModel> apiObservable = mCity != null
                ? fetchForecastFromCity()
                : fetchForecastFromLocation();

        if (null == apiObservable) {
            return;
        }

        final Subscription sub = apiObservable
                .map(request -> request.forecasts)
                .flatMap(Observable::from)
                .map(ItemWeather::new)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(getMvpView()::showLoading)
                .doAfterTerminate(getMvpView()::hideLoading)
                .subscribe(
                        getMvpView()::showTodayForecast,
                        this::handleError);
        addSubscription(sub);
    }

    private Observable<WeatherModel> fetchForecastFromCity() {
        return mApi.fetchForecast(mCity, FORECAST_COUNT);
    }

    private Observable<WeatherModel> fetchForecastFromLocation() {
        return mRxLocationManager.getLocation()
                .flatMap(location -> mApi.fetchForecast(
                        location.getLatitude(),
                        location.getLongitude(),
                        FORECAST_COUNT))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(request -> getMvpView().showCity(request.city));
    }

    private void handleError(Throwable t) {
        Timber.e(t, "MainPresenter");
        if (t instanceof LocationProviderThrowable) {
            getMvpView().askToEnableGps();
            return;
        }
        getMvpView().showError(t);
    }

}