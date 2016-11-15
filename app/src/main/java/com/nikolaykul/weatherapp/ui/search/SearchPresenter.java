package com.nikolaykul.weatherapp.ui.search;

import com.nikolaykul.weatherapp.di.scope.PerActivity;
import com.nikolaykul.weatherapp.ui.base.presenter.RxPresenter;

import javax.inject.Inject;

@PerActivity
public class SearchPresenter extends RxPresenter<SearchMvpView> {

    @Inject public SearchPresenter() {
    }

}
