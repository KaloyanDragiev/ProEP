package com.proep.cliffin.api;

public class APIUtils {

    private APIUtils() {}


    public static final String BASE_URL = "http://cliffing.kn01.fhict.nl/";
   // public static final String BASE_URL = "http://10.0.2.2:3000/";


    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
