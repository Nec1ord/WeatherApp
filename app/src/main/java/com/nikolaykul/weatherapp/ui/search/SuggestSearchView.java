package com.nikolaykul.weatherapp.ui.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.databinding.SearchViewBinding;
import com.nikolaykul.weatherapp.di.activity.ActivityComponent;
import com.nikolaykul.weatherapp.view.contract.HasComponent;

import javax.inject.Inject;

import timber.log.Timber;

public class SuggestSearchView extends RelativeLayout implements SearchMvpView {
    @Inject protected SearchPresenter mPresenter;
    private SearchViewBinding mBinding;

    public SuggestSearchView(Context context) {
        super(context);
        init();
    }

    public SuggestSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SuggestSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SuggestSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override public void hideLoading() {
        Timber.i("should hide loading");
    }

    @Override public void showLoading() {
        Timber.i("should show loading");
    }

    @SuppressWarnings("unchecked")
    private void init() {
        final ActivityComponent component = getActivityComponent();
        if (null == component) {
            Timber.e("Context doesn\'t has dagger component!");
            return;
        }
        component.inject(this);

        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()), R.layout.search_view, this, true);
        // TODO: get component from activity & inject dependencies
        // TODO: view model
    }

    @SuppressWarnings("unchecked")
    private ActivityComponent getActivityComponent() {
        final Context context = getContext();
        return context instanceof HasComponent
                ? ((HasComponent<ActivityComponent>) context).getComponent()
                : null;
    }

}