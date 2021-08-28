package com.proep.cliffin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Article {
    @SerializedName("newsId")
    @Expose
    int idNews;


    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("body")
    @Expose
    String body;


    @SerializedName("timePublished")
    @Expose
    Date timePublished;

    @SerializedName("User_idUser")
    @Expose
    int userID;

    @SerializedName("image")
    @Expose
    private String image;

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Date getTimePublished() {
        return timePublished;
    }
}
