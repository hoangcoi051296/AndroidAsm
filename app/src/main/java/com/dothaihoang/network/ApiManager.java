package com.dothaihoang.network;

import com.dothaihoang.model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiManager {
    String API_KEY = "km2A6yePAapJ5GjZryM9CBpJmLLwTFB8";

    String DOMAIN = "http://dataservice.accuweather.com/forecasts/v1/";
    String URL_ICON = "https://developer.accuweather.com/sites/default/files/";

    @GET("hourly/12hour/353412?apikey=km2A6yePAapJ5GjZryM9CBpJmLLwTFB8&language=vi-vn&metric=true")
    Call<List<Item>> getData();
}
