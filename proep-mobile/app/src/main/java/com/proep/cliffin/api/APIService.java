package com.proep.cliffin.api;

import com.proep.cliffin.models.*;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Header;
import retrofit2.http.Body;
import retrofit2.http.Path;

public interface APIService {

    @GET("jumpingsites")
    Call<ArrayList<JumpingSite>> getAllJumpingSites(@Header("Authorization") String authHeader);

    @GET("jumpingsites/{name}")
    Call<ArrayList<JumpingSite>> searchJumpingSite(@Path("name") String searchTxt, @Header("Authorization")String authHeader);

    @GET("jumpingsites/{id}/logs")
    Call<ArrayList<JumpingSiteLog>> getJumpingSiteLogs(@Path("id") Integer id, @Header("Authorization")String authHeader);

    @POST("/Login")
    Call<ArrayList<User>> isValidUser(@Body LoginRequest login);

    @FormUrlEncoded
    @POST("jumpingsites/{id}/logs")
    Call<Void> postJumpingSiteLog(@Path("id") Integer id, @Header("Cookie") String cookie, @Field("logDescription") String desc,@Field("rating") Integer rating, @Header("Authorization")String authHeader);


    @POST("/register")
    Call<Void> registerAccount(@Body RegisterRequest register);

    @GET("events")
    Call<ArrayList<Event>> getAllEvents(@Header("Authorization") String authHeader);

    @GET("events/{name}")
    Call<ArrayList<Event>> searchEvent(@Path("name") String searchTxt, @Header("Authorization")String authHeader);

    @GET("jumpingsite/{id}")
    Call<ArrayList<JumpingSite>> getJumpingSiteByID(@Path("id") Integer id);

    @GET("news")
    Call<ArrayList<Article>> getAllNews();

}

