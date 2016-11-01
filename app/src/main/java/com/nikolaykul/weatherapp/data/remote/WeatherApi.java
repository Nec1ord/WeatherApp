package com.nikolaykul.weatherapp.data.remote;

import com.nikolaykul.weatherapp.data.remote.model.ForecastRequest;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherApi {

    @GET("data/2.5/forecast/daily")
    Observable<ForecastRequest> fetchForecast(@Query("lat") double lat,
                                              @Query("lon") double lon,
                                              @Query("cnt") int dayCount);

    @GET("data/2.5/forecast/daily")
    Observable<ForecastRequest> fetchForecast(@Query("q") String city,
                                              @Query("cnt") int dayCount);

}
