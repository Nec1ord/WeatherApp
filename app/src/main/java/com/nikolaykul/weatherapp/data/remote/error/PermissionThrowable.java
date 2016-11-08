package com.nikolaykul.weatherapp.data.remote.error;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nikolaykul.weatherapp.R;

public class PermissionThrowable extends Throwable implements HasLocalizedMessage {
    @Override public String getMessage(@NonNull Context context) {
        return context.getString(R.string.error_permission);
    }
}
