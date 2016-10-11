package com.nikolaykul.weatherapp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nikolaykul.weatherapp.R;

import javax.inject.Inject;

public abstract class BaseMvpActivity<P extends Presenter<? extends MvpView>>
        extends BaseActivity implements NetworkMvpView {
    @Inject protected P mPresenter;
    private MaterialDialog mStub;

    protected abstract <V extends MvpView> V getMvpView();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStub = createStub();
        mPresenter.attachMvpView(getMvpView());
    }

    @Override protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
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
                .content(R.string.R_string_message_loading)
                .cancelable(false)
                .progress(true, 0)
                .build();
    }

}
