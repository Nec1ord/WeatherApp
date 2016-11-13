package com.nikolaykul.weatherapp.ui.search;

import com.nikolaykul.weatherapp.di.activity.ActivityComponent;
import com.nikolaykul.weatherapp.ui.base.activity.BaseMvpNetworkActivity;

public class SearchActivity extends BaseMvpNetworkActivity<SearchMvpView, SearchPresenter> implements SearchMvpView {

    @Override protected SearchMvpView getMvpView() {
        return this;
    }

    @Override protected void injectSelf(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

}
