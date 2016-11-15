package com.nikolaykul.weatherapp.view.contract;

import android.support.annotation.StringRes;

public interface CanShowError {
    void showError(@StringRes int strId);

    void showError(Throwable throwable);

    void showError(String message);
}