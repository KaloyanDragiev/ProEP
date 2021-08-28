package com.proep.cliffin.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.proep.cliffin.R;
import com.proep.cliffin.activites.MainActivity;

;
//import android.support.v4.app.Fragment;

public class UserFragment extends Fragment {

    TextView _firstName;
    TextView _lastName;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //android.webkit.CookieManager.getInstance().removeAllCookie();

        View view =  inflater.inflate(R.layout.fragment_user, container, false);

        _firstName = view.findViewById(R.id.xml_UserFirstNameValue);
        _lastName = view.findViewById(R.id.xml_UserLastNameValue);



        return view;
    }

}
