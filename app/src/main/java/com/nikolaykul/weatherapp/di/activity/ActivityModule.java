package com.nikolaykul.weatherapp.di.activity;

import android.app.Activity;
import android.content.Context;

import com.nikolaykul.weatherapp.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    Context provideContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return mActivity;
    }

}
