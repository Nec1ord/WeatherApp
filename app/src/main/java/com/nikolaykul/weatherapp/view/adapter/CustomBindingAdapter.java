package com.nikolaykul.weatherapp.view.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.data.remote.constant.WeatherApiConst;
import com.nikolaykul.weatherapp.util.StringUtil;

import java.util.Locale;

public class CustomBindingAdapter {

    @BindingAdapter("iconUrl")
    public static void uploadIcon(final ImageView imageView, final String icon) {
        if (StringUtil.isNullOrEmpty(icon)) return;
        final String url = String.format(Locale.getDefault(), WeatherApiConst.ICON_URL, icon);
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.forecast_placeholder)
                .error(R.drawable.forecast_error)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

}
