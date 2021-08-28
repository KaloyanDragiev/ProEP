package com.proep.cliffin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*
Rettofit is the class through which your API Interfaces are turned into callable objects.
*/

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static Gson gson = null;

    public static Retrofit getClient(String baseUrl) {

        if(gson == null) {
            gson = new GsonBuilder()
                    .setLenient()
                    .create();
        }

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
