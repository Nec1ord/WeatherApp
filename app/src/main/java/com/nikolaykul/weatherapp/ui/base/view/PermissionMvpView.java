package com.nikolaykul.weatherapp.ui.base.view;

import android.support.annotation.NonNull;

public interface PermissionMvpView extends MvpView {

    int checkPermission(@NonNull String permission);

}
