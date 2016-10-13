package com.nikolaykul.weatherapp.data.remote;

import retrofit2.http.GET;
import rx.Observable;

public interface WeatherApi {
    String HOST = "http://some_host.com";

    @GET("http://api.wunderground.com/api/b161249aa0d2ef58/geolookup/conditions/forecast/q/Russuia/Krasnodar.json")
    Observable<ForecastRequestModel> fetchForecast();

}
