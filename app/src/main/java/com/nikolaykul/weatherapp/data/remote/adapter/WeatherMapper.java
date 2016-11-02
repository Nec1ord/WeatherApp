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
import com.nikolaykul.weatherapp.data.model.WeatherModel;
import com.nikolaykul.weatherapp.data.model.forecast.Forecast;

import java.lang.reflect.Type;
import java.util.List;

public class WeatherMapper implements JsonDeserializer<WeatherModel> {
    private final Configuration mConfiguration;
    private final TypeRef<List<Forecast>> mListForecastType;

    public WeatherMapper() {
        mConfiguration = Configuration.builder()
                .mappingProvider(new GsonMappingProvider())
                .jsonProvider(new GsonJsonProvider())
                .build();
        mListForecastType = new TypeRef<List<Forecast>>() {
        };
    }

    @Override
    public WeatherModel deserialize(JsonElement jsonElement,
                                    Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
        final String json = jsonElement.toString();
        final WeatherModel result = new WeatherModel();
        result.city = JsonPath
                .using(mConfiguration)
                .parse(json)
                .read("$.city.name", String.class);
        result.forecasts = JsonPath
                .using(mConfiguration)
                .parse(json)
                .read("$.list.*", mListForecastType);
        return result;
    }

}