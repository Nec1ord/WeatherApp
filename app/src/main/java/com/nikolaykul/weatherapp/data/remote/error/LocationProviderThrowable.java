package com.nikolaykul.weatherapp.data.remote.error;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nikolaykul.weatherapp.R;

public class LocationProviderThrowable extends Throwable implements HasLocalizedMessage {
    @Override public String getMessage(@NonNull Context context) {
        return context.getString(R.string.error_location_not_enabled);
    }
}