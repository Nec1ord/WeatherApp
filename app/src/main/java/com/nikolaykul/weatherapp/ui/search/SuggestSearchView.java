package com.nikolaykul.weatherapp.ui.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.databinding.SearchViewBinding;

import timber.log.Timber;

public class SuggestSearchView extends LinearLayout implements SearchMvpView {
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

    private void init() {
        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()), R.layout.search_view, this, true);

    }

}