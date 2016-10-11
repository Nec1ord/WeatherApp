package com.nikolaykul.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nikolaykul.weatherapp.di.DaggerApplicationComponent;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @Inject StubApi mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        injectSelf();
        mApi.stubApiCall();
    }

    private void injectSelf() {
        DaggerApplicationComponent.builder()
                .build()
                .inject(this);
    }

}