package com.proep.cliffin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("userType")
    @Expose
    private String userType;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    public  RegisterRequest(String email, String password, String firstName, String lastName, String userType){
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;

    }
}
