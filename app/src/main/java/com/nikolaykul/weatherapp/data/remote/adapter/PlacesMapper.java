package com.nikolaykul.weatherapp.data.remote.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.nikolaykul.weatherapp.data.model.Prediction;

import java.lang.reflect.Type;
import java.util.List;

public class PlacesMapper implements JsonDeserializer<List<Prediction>> {

    @Override
    public List<Prediction> deserialize(JsonElement json,
                                        Type typeOfT,
                                        JsonDeserializationContext context) throws JsonParseException {
        return null;
    }

}