package com.nikolaykul.weatherapp.data.remote;

import com.nikolaykul.weatherapp.data.remote.model.GoogleRequest;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GooglePlacesApi {
    String HOST = "https://maps.googleapis.com/";
    String KEY_NAME = "key";
    String KEY_VALUE = "AIzaSyAywgNKCZ1gfGrlNGekrJxAKrHfHEAdewQ";
    String TYPES_NAME = "types";
    String TYPES_VALUE = "(cities)";

    @GET("maps/api/place/autocomplete/json")
    Observable<GoogleRequest> findSuggestions(@Query("input") String city);

}
