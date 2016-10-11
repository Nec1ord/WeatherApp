package com.nikolaykul.weatherapp.ui.base;

public abstract class Presenter<T extends MvpView> {
    private T mMvpView;

    void attachMvpView(T mvpView) {
        mMvpView = mvpView;
    }

    protected T getMvpView() {
        return mMvpView;
    }

    protected abstract void onResume();

    protected abstract void onDestroy();

}
