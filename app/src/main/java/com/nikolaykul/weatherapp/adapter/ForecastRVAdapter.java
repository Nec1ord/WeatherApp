package com.nikolaykul.weatherapp.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.data.model.Forecast;
import com.nikolaykul.weatherapp.databinding.ItemWeatherBinding;

import java.util.ArrayList;
import java.util.List;

public class ForecastRVAdapter extends RecyclerView.Adapter<ForecastRVAdapter.ForecastViewHolder> {
    private List<Forecast> mItems;

    public ForecastRVAdapter(List<Forecast> items) {
        super();
        mItems = items;
    }

    @Override public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        holder.setItem(mItems.get(position));
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemWeatherBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_weather, parent, false);
        return new ForecastViewHolder(binding.getRoot());
    }

    public void replaceItems(List<Forecast> items) {
        mItems = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder {
        private final ItemWeatherBinding mBinding;

        public ForecastViewHolder(View view) {
            super(view);
            mBinding = DataBindingUtil.bind(view);
        }

        public void setItem(Forecast item) {
            mBinding.setItem(item);
        }

    }
}
