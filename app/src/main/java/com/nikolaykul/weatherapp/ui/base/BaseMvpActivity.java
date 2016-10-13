package com.nikolaykul.weatherapp.ui.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nikolaykul.weatherapp.R;

public abstract class BaseMvpActivity<TPresenter extends Presenter, TBinding extends ViewDataBinding>
        extends BaseActivity implements NetworkMvpView {
    protected TBinding mBinding;
    private MaterialDialog mStub;

    protected abstract TPresenter getPresenter();

    protected abstract TBinding createBinding();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStub = createStub();
        mBinding = createBinding();
    }

    @Override protected void onResume() {
        super.onResume();
        if (getPresenter() != null) {
            getPresenter().onResume();
        }
    }

    @Override protected void onDestroy() {
        if (getPresenter() != null) {
            getPresenter().onResume();
        }
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
