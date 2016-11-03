package com.nikolaykul.weatherapp.ui.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.data.remote.error.HasLocalizedMessage;
import com.nikolaykul.weatherapp.ui.base.presenter.Presenter;
import com.nikolaykul.weatherapp.ui.base.view.MvpView;
import com.nikolaykul.weatherapp.ui.base.view.NetworkMvpView;

public abstract class BaseMvpNetworkActivity<TView extends MvpView, TPresenter extends Presenter<TView>>
        extends BaseMvpActivity<TView, TPresenter> implements NetworkMvpView {
    private MaterialDialog mStub;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStub = createStub();
    }

    @Override protected void onDestroy() {
        if (mStub.isShowing()) {
            mStub.dismiss();
        }
        super.onDestroy();
    }

    @Override public void showLoading() {
        mStub.show();
    }

    @Override public void hideLoading() {
        mStub.dismiss();
    }

    @Override public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override public void showError(@StringRes int strId) {
        showError(getString(strId));
    }

    @Override public void showError(Throwable throwable) {
        if (throwable instanceof HasLocalizedMessage) {
            showError(((HasLocalizedMessage) throwable).getMessage(this));
            return;
        }
        showError(R.string.error_default);
    }

    private MaterialDialog createStub() {
        return new MaterialDialog.Builder(this)
                .content(R.string.message_loading)
                .cancelable(false)
                .progress(true, 0)
                .build();
    }

}
