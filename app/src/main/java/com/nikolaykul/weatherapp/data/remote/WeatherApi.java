package com.nikolaykul.weatherapp.data.remote;

import com.nikolaykul.weatherapp.data.model.WeatherModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherApi {
    String FORECAST = "data/2.5/forecast/";
    String DAILY = "daily";

    @GET(FORECAST + DAILY)
    Observable<WeatherModel> fetchForecast(@Query("lat") double lat,
                                           @Query("lon") double lon,
                                           @Query("cnt") int dayCount);

    @GET(FORECAST + DAILY)
    Observable<WeatherModel> fetchForecast(@Query("q") String city,
                                           @Query("cnt") int dayCount);

}
