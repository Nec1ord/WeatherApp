package com.nikolaykul.weatherapp.error;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.Status;
import com.nikolaykul.weatherapp.R;

public class LocationProviderThrowable extends Throwable implements HasLocalizedMessage {
    private Status mStatus;

    public LocationProviderThrowable(Status status) {
        this.mStatus = status;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    @Override public String getMessage(@NonNull Context context) {
        return context.getString(R.string.error_location_not_enabled);
    }

}