package com.nikolaykul.weatherapp.ui.base;

import android.support.annotation.StringRes;

public interface NetworkMvpView extends MvpView {
    void showLoading();

    void hideLoading();

    void showError(@StringRes int strId);
}
