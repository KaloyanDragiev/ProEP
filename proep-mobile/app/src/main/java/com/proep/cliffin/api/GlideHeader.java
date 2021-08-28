package com.proep.cliffin.api;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.proep.cliffin.activites.MainActivity;

public class GlideHeader {

    //Add Authorization header for Glide request
    public static GlideUrl getUrlWithHeaders(String url){
        return new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " )
                .build());
    }
}
