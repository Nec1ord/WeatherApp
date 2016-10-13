package com.nikolaykul.weatherapp.ui.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

abstract class BaseMvpActivity<
        TPresenter extends Presenter<TView>,
        TView extends MvpView,
        TBinding extends ViewDataBinding>
        extends BaseDaggerActivity {
    protected TBinding mBinding;
    @Inject protected TPresenter mPresenter;

    protected abstract TView getMvpView();

    protected abstract TBinding createBinding();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = createBinding();
        mPresenter.attachMvpView(getMvpView());
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

    protected TPresenter getPresenter() {
        return mPresenter;
    }

}
