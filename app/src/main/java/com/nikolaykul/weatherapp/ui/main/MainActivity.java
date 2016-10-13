package com.nikolaykul.weatherapp.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.adapter.ForecastRVAdapter;
import com.nikolaykul.weatherapp.data.model.Forecast;
import com.nikolaykul.weatherapp.databinding.ActivityMainBinding;
import com.nikolaykul.weatherapp.di.activity.ActivityComponent;
import com.nikolaykul.weatherapp.ui.base.BaseMvpNetworkActivity;
import com.nikolaykul.weatherapp.util.ItemSpaceDecoration;

import java.util.Collections;
import java.util.List;

public class MainActivity extends BaseMvpNetworkActivity<MainMvpView, MainPresenter>
        implements MainMvpView {
    private ActivityMainBinding mBinding;
    private ForecastRVAdapter mAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mAdapter = new ForecastRVAdapter(Collections.emptyList());
        initRecyclerView(mBinding.recyclerView);
    }

    @Override protected void injectSelf(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override protected MainMvpView getMvpView() {
        return this;
    }

    @Override public void showLoading() {
        mBinding.swipeRefreshLayout.setRefreshing(true);
    }

    @Override public void hideLoading() {
        mBinding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override public void showTodayForecast(List<Forecast> forecasts) {
        if (null == forecasts) return;
        mAdapter.replaceItems(forecasts);
        mBinding.setHasItems(!forecasts.isEmpty());
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        final ItemSpaceDecoration spaceDecoration =
                new ItemSpaceDecoration(ItemSpaceDecoration.Side.BOTTOM,
                        (int) getResources().getDimension(R.dimen.item_space_small));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(spaceDecoration);
        recyclerView.setAdapter(mAdapter);
        mBinding.swipeRefreshLayout.setOnRefreshListener(mPresenter::loadTodayForecast);
    }

}