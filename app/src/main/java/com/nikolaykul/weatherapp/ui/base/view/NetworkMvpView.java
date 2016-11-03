package com.nikolaykul.weatherapp.ui.base.view;

import android.support.annotation.StringRes;

public interface NetworkMvpView extends MvpView {
    void showLoading();

    void hideLoading();

    void showError(@StringRes int strId);

    void showError(Throwable throwable);

    void showError(String message);
}