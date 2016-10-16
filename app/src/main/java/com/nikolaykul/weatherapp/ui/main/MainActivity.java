package com.nikolaykul.weatherapp.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.adapter.CitiesAdapter;
import com.nikolaykul.weatherapp.adapter.ForecastRVAdapter;
import com.nikolaykul.weatherapp.databinding.ActivityMainBinding;
import com.nikolaykul.weatherapp.di.activity.ActivityComponent;
import com.nikolaykul.weatherapp.item.ItemSpaceDecoration;
import com.nikolaykul.weatherapp.item.ItemWeather;
import com.nikolaykul.weatherapp.ui.base.activity.BaseMvpNetworkActivity;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseMvpNetworkActivity<MainMvpView, MainPresenter>
        implements MainMvpView {
    private static final int PERMISSION_LOCATION_CODE = 1;
    @Inject protected CitiesAdapter mCitiesAdapter;
    private ActivityMainBinding mBinding;
    private ForecastRVAdapter mAdapter;
    private MaterialDialog mGpsDialog;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mAdapter = new ForecastRVAdapter(Collections.emptyList());
        mGpsDialog = createGpsDialog();
        initRecyclerView(mBinding.recyclerView);
        initToolbar(mBinding.includeToolbar.toolbar);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_LOCATION_CODE &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
        actv.setAdapter(mCitiesAdapter);
        actv.setThreshold(1);
        actv.setOnItemClickListener((adapterView, view, i, l) -> {
            if (itemSearch.isActionViewExpanded()) {
                itemSearch.collapseActionView();
            }
            mPresenter.setCity(mCitiesAdapter.getItem(i));
            mPresenter.loadTodayForecast();
        });
        // geo
        final MenuItem itemGeo = menu.findItem(R.id.action_geo);
        itemGeo.setOnMenuItemClickListener(menuItem -> {
            if (itemSearch.isActionViewExpanded()) {
                itemSearch.collapseActionView();
            }
            mPresenter.clearCity();
            mPresenter.loadTodayForecast();
            return false;
        });
        return true;
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

    @Override public void showTodayForecast(List<ItemWeather> forecasts) {
        if (null == forecasts) return;
        mAdapter.replaceItems(forecasts);
        mBinding.setHasItems(!forecasts.isEmpty());
    }

    @Override public void askLocationPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_LOCATION_CODE);
    }

    @Override public void askToEnableGps() {
        mGpsDialog.show();
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        final ItemSpaceDecoration spaceDecoration =
                new ItemSpaceDecoration(ItemSpaceDecoration.Side.BOTTOM,
                        getResources().getDimensionPixelSize(R.dimen.item_space_small));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(spaceDecoration);
        recyclerView.setAdapter(mAdapter);
        mBinding.swipeRefreshLayout.setOnRefreshListener(mPresenter::loadTodayForecast);
    }

    private void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_main);
        }
    }

    private MaterialDialog createGpsDialog() {
        return new MaterialDialog.Builder(this)
                .title(R.string.dialog_gps_title)
                .content(R.string.dialog_gps_content)
                .positiveText(R.string.ok)
                .negativeText(R.string.no)
                .onPositive((dialog, which) ->
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .build();
    }

}