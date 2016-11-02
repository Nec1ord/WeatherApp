package com.nikolaykul.weatherapp.data.remote.constant;

public final class PlacesApiConst {
    public static final String HOST = "https://maps.googleapis.com/";
    public static final String KEY_NAME = "key";
    public static final String KEY_VALUE = "AIzaSyAywgNKCZ1gfGrlNGekrJxAKrHfHEAdewQ"; // private key
    public static final String TYPES_NAME = "types";
    public static final String TYPES_VALUE = "(cities)";

    public final static class Route {
        public static final String PLACE = "maps/api/place/autocomplete/json";
    }
}
