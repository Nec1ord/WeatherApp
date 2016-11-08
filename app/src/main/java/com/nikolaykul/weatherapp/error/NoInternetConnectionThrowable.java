package com.nikolaykul.weatherapp.error;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nikolaykul.weatherapp.R;

import java.io.IOException;

public class NoInternetConnectionThrowable extends IOException implements HasLocalizedMessage {
    @Override public String getMessage(@NonNull Context context) {
        return context.getString(R.string.error_network);
    }
}