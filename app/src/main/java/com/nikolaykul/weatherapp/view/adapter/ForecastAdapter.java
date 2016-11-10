package com.nikolaykul.weatherapp.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.data.model.forecast.Forecast;
import com.nikolaykul.weatherapp.databinding.ItemWeatherBinding;
import com.nikolaykul.weatherapp.view.item.ItemWeather;
import com.nikolaykul.weatherapp.view.listener.OnForecastClickListener;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private final OnForecastClickListener mListener;
    private List<Forecast> mItems;

    public ForecastAdapter(List<Forecast> items,
                           OnForecastClickListener listener) {
        super();
        mItems = items;
        mListener = listener;
    }

    @Override public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(mItems.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemWeatherBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_weather, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    public void replaceItems(List<Forecast> items) {
        mItems = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemWeatherBinding mBinding;

        ViewHolder(View view) {
            super(view);
            mBinding = DataBindingUtil.bind(view);
        }

        public void setItem(Forecast forecast) {
            final ItemWeather item = new ItemWeather(forecast);
            mBinding.setItem(item);
            mBinding.getRoot().setOnClickListener(v -> mListener.onForecastClicked(forecast));
        }
    }

}