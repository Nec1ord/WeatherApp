package com.nikolaykul.weatherapp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

public abstract class BaseMvpActivity<P extends Presenter<V>, V extends MvpView>
        extends BaseActivity {
    @Inject protected P mPresenter;

    protected abstract V getMvpView();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachMvpView(getMvpView());
    }

    @Override protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

}
