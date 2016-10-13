package com.nikolaykul.weatherapp.ui.base.presenter;

import com.nikolaykul.weatherapp.ui.base.view.MvpView;

public abstract class Presenter<T extends MvpView> {
    private T mMvpView;

    public final void attachMvpView(T mvpView) {
        mMvpView = mvpView;
    }

    protected final T getMvpView() {
        return mMvpView;
    }

    public abstract void onResume();

    public abstract void onDestroy();

}
