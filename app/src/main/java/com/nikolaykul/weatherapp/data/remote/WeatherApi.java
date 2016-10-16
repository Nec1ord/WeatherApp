package com.nikolaykul.weatherapp.data.remote;

import com.nikolaykul.weatherapp.data.remote.model.ForecastRequest;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherApi {
    String HOST = "http://api.openweathermap.org/";
    String KEY_NAME = "appid";
    String KEY_VALUE = "fefdc4cee609445b612ff5f836dc5375";
    String METRIC_NAME = "units";
    String METRIC_VALUE = "metric";
    String ICON_URL = "http://openweathermap.org/img/w/";

    @GET("data/2.5/forecast/daily")
    Observable<ForecastRequest> fetchForecast(@Query("lat") double lat,
                                              @Query("lon") double lon,
                                              @Query("cnt") int dayCount);

    @GET("data/2.5/forecast/daily")
    Observable<ForecastRequest> fetchForecast(@Query("q") String city,
                                              @Query("cnt") int dayCount);

}
