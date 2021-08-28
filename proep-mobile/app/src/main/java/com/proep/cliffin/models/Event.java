package com.proep.cliffin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Event {

    @SerializedName("idEvent")
    @Expose
    int idEvent;


    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("date")
    @Expose
    Date date;


    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("JumpingSite_idJumpingSite")
    @Expose
    int jumpingSiteID;



    @SerializedName("image")
    @Expose
    private String image;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public int getJumpingSiteID() {
        return jumpingSiteID;
    }
}
