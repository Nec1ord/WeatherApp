package com.nikolaykul.weatherapp.ui.base;

abstract class Presenter<T extends MvpView> {
    private T mMvpView;

    void attachMvpView(T mvpView) {
        mMvpView = mvpView;
    }

    protected T getMvpView() {
        return mMvpView;
    }

    abstract void onResume();

    abstract void onDestroy();

}
