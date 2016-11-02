package com.nikolaykul.weatherapp.data.remote.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import com.nikolaykul.weatherapp.data.model.PlacesModel;

import java.lang.reflect.Type;
import java.util.List;

public class PlacesMapper implements JsonDeserializer<PlacesModel> {
    private final Configuration mConfiguration;
    private final TypeRef<List<String>> mListStringType;

    public PlacesMapper() {
        mConfiguration = Configuration.builder()
                .mappingProvider(new GsonMappingProvider())
                .jsonProvider(new GsonJsonProvider())
                .build();
        mListStringType = new TypeRef<List<String>>() {
        };
    }

    @Override
    public PlacesModel deserialize(JsonElement json,
                                   Type typeOfT,
                                   JsonDeserializationContext context) throws JsonParseException {
        final PlacesModel result = new PlacesModel();
        result.suggestions = JsonPath
                .using(mConfiguration)
                .parse(json.toString())
                .read("$.predictions[*].terms[0].value", mListStringType);
        return result;
    }

}