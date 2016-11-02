package com.nikolaykul.weatherapp.data.remote;

import com.nikolaykul.weatherapp.data.model.PlacesModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GooglePlacesApi {

    @GET(PlacesApiConst.Route.PLACE)
    Observable<PlacesModel> findSuggestions(@Query("input") String city);

}
