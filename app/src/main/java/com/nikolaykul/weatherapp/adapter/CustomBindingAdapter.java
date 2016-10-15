package com.nikolaykul.weatherapp.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.data.remote.WeatherApi;
import com.nikolaykul.weatherapp.util.StringUtil;

public class CustomBindingAdapter {

    @BindingAdapter("imageUrl")
    public static void uploadImage(final ImageView imageView, final String url) {
        if (StringUtil.isNullOrEmpty(url)) return;
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.forecast_placeholder)
                .error(R.drawable.forecast_error)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    @BindingAdapter("iconUrl")
    public static void uploadIcon(final ImageView imageView, final String icon) {
        if (StringUtil.isNullOrEmpty(icon)) return;
        final String url = WeatherApi.ICON_URL + icon + ".png";
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.forecast_placeholder)
                .error(R.drawable.forecast_error)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

}
