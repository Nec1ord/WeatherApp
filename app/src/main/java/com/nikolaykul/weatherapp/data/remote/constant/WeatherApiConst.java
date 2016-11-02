package com.nikolaykul.weatherapp.data.remote.constant;

public final class WeatherApiConst {
    public static final String HOST = "http://api.openweathermap.org/data/2.5/forecast/";
    public static final String KEY_NAME = "appid";
    public static final String KEY_VALUE = "fefdc4cee609445b612ff5f836dc5375"; // private key
    public static final String METRIC_NAME = "units";
    public static final String METRIC_VALUE = "metric";
    public static final String ICON_URL = "http://openweathermap.org/img/w/%s.png";

    public final static class Route {
        public static final String DAILY = "daily/";
    }
}
