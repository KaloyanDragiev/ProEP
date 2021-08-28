package com.proep.cliffin.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class JumpingSiteLog {


    public JumpingSiteLog(int user_idUser, String logDescription, Integer rating, int jumpingSite_idJumpingSite) {
        User_idUser = user_idUser;
        this.logDescription = logDescription;
        this.rating = rating;
        JumpingSite_idJumpingSite = jumpingSite_idJumpingSite;
    }

    public JumpingSiteLog(String logDescription, Integer rating, int jumpingSite_idJumpingSite) {
        this.logDescription = logDescription;
        this.rating = rating;
        JumpingSite_idJumpingSite = jumpingSite_idJumpingSite;
    }

    public int getUser_idUser() {
        return User_idUser;
    }

    @SerializedName("User_idUser")
    @Expose
    int User_idUser;

    @SerializedName("logId")
    @Expose
    int logId;

    @SerializedName("logDescription")
    @Expose
    String logDescription;

    @SerializedName("rating")
    @Expose
    Integer rating;

    @SerializedName("dateOfLog")
    @Expose
    Date dateOfLog;

    public int getJumpingSite_idJumpingSite() {
        return JumpingSite_idJumpingSite;
    }


    @SerializedName("JumpingSite_idJumpingSite")
    @Expose
    int JumpingSite_idJumpingSite;

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getLogDescription() {
        return logDescription;
    }

    public void setLogDescription(String logDescription) {
        this.logDescription = logDescription;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Date getDateOfLog() {
        return dateOfLog;
    }

    public void setDateOfLog(Date dateOfLog) {
        this.dateOfLog = dateOfLog;
    }
}
