package com.nikolaykul.weatherapp.ui.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

abstract class BaseMvpActivity<TPresenter extends Presenter, TBinding extends ViewDataBinding>
        extends BaseDaggerActivity {
    protected TBinding mBinding;

    protected abstract TPresenter getPresenter();

    protected abstract TBinding createBinding();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}
