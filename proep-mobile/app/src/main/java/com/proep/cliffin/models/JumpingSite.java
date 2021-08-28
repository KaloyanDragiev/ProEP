package com.proep.cliffin.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JumpingSite {



    @SerializedName("idJumpingSite")
    @Expose
    int idJumpingSite;


    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("difficultyLevel")
    @Expose
    String difficultyLevel;


    @SerializedName("coordinates")
    @Expose
    String coordinates;

    @SerializedName("height")
    @Expose
    int height;

    @SerializedName("rate")
    @Expose
    String rate;

    @SerializedName("image")
    @Expose
    String image;


    public int getIdJumpingSite() {
        return idJumpingSite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public int getHeight() {
        return height;
    }

    public String getRate() {
        return rate;
    }
}
