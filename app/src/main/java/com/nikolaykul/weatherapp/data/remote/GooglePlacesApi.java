package com.nikolaykul.weatherapp.data.remote;

import com.nikolaykul.weatherapp.data.remote.model.GoogleRequest;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GooglePlacesApi {

    @GET(PlacesApiConst.Route.PLACE)
    Observable<GoogleRequest> findSuggestions(@Query("input") String city);

}
