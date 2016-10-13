package com.nikolaykul.weatherapp.ui.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nikolaykul.weatherapp.R;
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

    @Override public void showLoading() {
        mStub.show();
    }

    @Override public void showError(@StringRes int strId) {
        Toast.makeText(this, strId, Toast.LENGTH_SHORT).show();
    }

    @Override public void hideLoading() {
        mStub.dismiss();
    }

    private MaterialDialog createStub() {
        return new MaterialDialog.Builder(this)
                .content(R.string.message_loading)
                .cancelable(false)
                .progress(true, 0)
                .build();
    }

}
