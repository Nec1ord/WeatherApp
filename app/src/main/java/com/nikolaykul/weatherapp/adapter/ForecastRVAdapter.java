package com.nikolaykul.weatherapp.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikolaykul.weatherapp.BR;
import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.databinding.ItemTodayWeatherBinding;
import com.nikolaykul.weatherapp.databinding.ItemWeatherBinding;
import com.nikolaykul.weatherapp.item.ItemWeather;

import java.util.ArrayList;
import java.util.List;

public class ForecastRVAdapter extends RecyclerView.Adapter<ForecastRVAdapter.ForecastVH> {
    private static final int TYPE_HEADER = 0;
    private final ForecastRVAdapter.OnItemClickListener mListener;
    private List<ItemWeather> mItems;

    public ForecastRVAdapter(List<ItemWeather> items,
                             OnItemClickListener listener) {
        super();
        mItems = items;
        mListener = listener;
    }

    @Override public int getItemCount() {
        return mItems.size();
    }

    @Override public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ForecastVH holder, int position) {
        holder.setItem(mItems.get(position));
    }

    @Override
    public ForecastVH onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (TYPE_HEADER == viewType) {
            final ItemTodayWeatherBinding binding =
                    DataBindingUtil.inflate(inflater, R.layout.item_today_weather, parent, false);
            return new ForecastVHHeader(binding.getRoot());
        }
        final ItemWeatherBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_weather, parent, false);
        return new ForecastVHContent(binding.getRoot());
    }

    public void replaceItems(List<ItemWeather> items) {
        mItems = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    abstract class ForecastVH<TBinding extends ViewDataBinding> extends RecyclerView.ViewHolder {
        private final TBinding mBinding;

        ForecastVH(View view) {
            super(view);
            mBinding = DataBindingUtil.bind(view);
        }

        public void setItem(ItemWeather item) {
            mBinding.setVariable(BR.item, item);
            mBinding.getRoot().setOnClickListener(v -> mListener.onItemClicked(item));
        }
    }

    private class ForecastVHHeader extends ForecastVH<ItemTodayWeatherBinding> {
        ForecastVHHeader(View view) {
            super(view);
        }
    }

    private class ForecastVHContent extends ForecastVH<ItemWeatherBinding> {
        ForecastVHContent(View view) {
            super(view);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(ItemWeather item);
    }

}
