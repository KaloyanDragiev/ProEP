package com.proep.cliffin.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.proep.cliffin.R;
import com.proep.cliffin.activites.LoginActivity;
import com.proep.cliffin.models.User;


public class WelcomeFragment extends Fragment {

    User currentUser;
    TextView _welcomeText;

    public WelcomeFragment() {


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_welcome, container, false);

        _welcomeText =  view.findViewById(R.id.xml_welcomeText);

        //currentUser = LoginActivity.getCurrentUser();  uncomment this
        currentUser = new User(90,"David", "Gerges" , "d@gmail.com"); // and delete this
        _welcomeText.setText("Welcome, " + currentUser.getFirstName());
    return view;
    }
}
