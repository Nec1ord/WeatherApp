package com.nikolaykul.weatherapp.util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NetworkManager {
    private final ConnectivityManager mConnectivityManager;

    @Inject public NetworkManager(ConnectivityManager connectivityManager) {
        mConnectivityManager = connectivityManager;
    }

    public boolean isNetworkEnabled() {
        final NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}