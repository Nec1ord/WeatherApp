package com.nikolaykul.weatherapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.nikolaykul.weatherapp.R;
import com.nikolaykul.weatherapp.data.remote.GooglePlacesApi;
import com.nikolaykul.weatherapp.di.activity.PerActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@PerActivity
public class CitiesAdapter extends ArrayAdapter<String> {
    private final GooglePlacesApi mApi;
    private List<String> mCities;

    @Inject
    public CitiesAdapter(Context context, GooglePlacesApi api) {
        super(context, R.layout.item_city);
        mCities = Collections.emptyList();
        mApi = api;
    }

    @Override public int getCount() {
        return mCities.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mCities.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override protected FilterResults performFiltering(CharSequence charSequence) {
                if (null != charSequence) {
                    updateCities(charSequence.toString());
                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            }
        };
    }

    private void updateCities(String input) {
        mApi.findSuggestions(input)
                .map(googleRequest -> googleRequest.predictions)
                .flatMap(Observable::from)
                .map(prediction -> prediction.description)
                .map(string -> string.split(",")[0])        // get only 1st name
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(suggestions -> {
                    mCities = new ArrayList<>(suggestions);
                    notifyDataSetChanged();
                }, throwable -> Timber.e(throwable, "Couldn't fetch cities"));
    }

}
