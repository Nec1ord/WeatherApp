package com.nikolaykul.weatherapp.ui.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nikolaykul.weatherapp.ui.base.presenter.Presenter;
import com.nikolaykul.weatherapp.ui.base.view.MvpView;

import javax.inject.Inject;

public abstract class BaseMvpActivity<TView extends MvpView, TPresenter extends Presenter<TView>>
        extends BaseDaggerActivity {
    @Inject protected TPresenter mPresenter;

    protected abstract TView getMvpView();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachMvpView(getMvpView());
    }

    @Override protected void onResume() {
        super.onResume();
        if (getPresenter() != null) {
            getPresenter().onResume();
        }
    }

    @Override protected void onDestroy() {
        if (getPresenter() != null) {
            getPresenter().onResume();
        }
        super.onDestroy();
    }

    protected TPresenter getPresenter() {
        return mPresenter;
    }

}
