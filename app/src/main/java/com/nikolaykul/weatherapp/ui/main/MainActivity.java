package com.nikolaykul.weatherapp.ui.main;

import android.content.Intent;
import android.content.IntentSender;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.common.api.Status;
import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.data.model.forecast.Forecast;
import com.nikolaykul.weatherapp.databinding.ActivityMainBinding;
import com.nikolaykul.weatherapp.di.activity.ActivityComponent;
import com.nikolaykul.weatherapp.ui.base.activity.BaseMvpNetworkActivity;
import com.nikolaykul.weatherapp.util.StringUtil;
import com.nikolaykul.weatherapp.view.adapter.CitiesAdapter;
import com.nikolaykul.weatherapp.view.adapter.ForecastAdapter;
import com.nikolaykul.weatherapp.view.decoration.ItemSpaceDecoration;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class MainActivity extends BaseMvpNetworkActivity<MainMvpView, MainPresenter>
        implements MainMvpView {
    private static final int REQUEST_CODE_LOCATION = 42;
    @Inject protected CitiesAdapter mCitiesAdapter;
    private ActivityMainBinding mBinding;
    private ForecastAdapter mAdapter;
    private boolean isLoading;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mAdapter = new ForecastAdapter(Collections.emptyList(), mPresenter::onItemSelected);
        initRecyclerView(mBinding.recyclerView);
        initToolbar(mBinding.includeToolbar.toolbar);
        setListeners();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_LOCATION == requestCode && RESULT_OK == resultCode) {
            mPresenter.loadTodayForecast();
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // search
        final MenuItem itemSearch = menu.findItem(R.id.action_search);
        final AutoCompleteTextView actv =
                (AutoCompleteTextView) getLayoutInflater().inflate(R.layout.search_view, null);
        itemSearch.setActionView(actv);
        actv.setThreshold(1);
        actv.setAdapter(mCitiesAdapter);
        actv.setOnItemClickListener((adapterView, view, i, l) -> {
            if (isLoading) {
                return;
            }
            mPresenter.onCitySelected(mCitiesAdapter.getItem(i));
        });
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (isLoading) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_geo:
                mPresenter.onGeoSelected();
                return true;
            case R.id.action_search:
                item.expandActionView();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override protected void injectSelf(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override protected MainMvpView getMvpView() {
        return this;
    }

    @Override public void showLoading() {
        mBinding.swipeRefreshLayout.setRefreshing(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().collapseActionView();
        }
        isLoading = true;
    }

    @Override public void hideLoading() {
        mBinding.swipeRefreshLayout.setRefreshing(false);
        isLoading = false;
    }

    @Override public void showCity(String city) {
        if (null == getSupportActionBar()) return;
        final String title = StringUtil.isNullOrEmpty(city)
                ? getString(R.string.title_main)
                : city;
        getSupportActionBar().setTitle(title);
    }

    @Override public void showTodayForecast(List<Forecast> forecasts) {
        if (null == forecasts) return;
        mAdapter.replaceItems(forecasts);
        mBinding.setHasItems(!forecasts.isEmpty());
    }

    @Override public void askToEnableGps(Status status) {
        try {
            status.startResolutionForResult(this, REQUEST_CODE_LOCATION);
        } catch (IntentSender.SendIntentException e) {
            Timber.e(e, "Ask to enable gps error");
        }
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        final ItemSpaceDecoration spaceDecoration =
                new ItemSpaceDecoration(ItemSpaceDecoration.Side.BOTTOM,
                        getResources().getDimensionPixelSize(R.dimen.item_space_small));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(spaceDecoration);
        recyclerView.setAdapter(mAdapter);
    }

    private void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_main);
        }
    }

    private void setListeners() {
        mBinding.swipeRefreshLayout.setOnRefreshListener(mPresenter::loadTodayForecast);
        mBinding.fab.setOnClickListener(v -> mPresenter.onGeoSelected());
    }

}