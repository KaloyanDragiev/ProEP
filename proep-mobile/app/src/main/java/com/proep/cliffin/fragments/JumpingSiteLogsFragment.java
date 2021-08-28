package com.proep.cliffin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.proep.cliffin.activites.JumpingSiteDetailsActivity;
import com.proep.cliffin.activites.LoginActivity;
import com.proep.cliffin.adapters.JumpingSiteLogsAdapter;
import com.proep.cliffin.api.GlideHeader;
import com.proep.cliffin.dialoges.JumpingSiteAddLogDialog;
import com.proep.cliffin.models.JumpingSite;
import com.proep.cliffin.models.JumpingSiteLog;
import com.proep.cliffin.models.User;
import com.proep.cliffin.repositories.JumpingSiteRepository;

import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;


import com.proep.cliffin.R;


public class JumpingSiteLogsFragment extends Fragment {

    //Tag fragment for Dialog linking
    public static final String TAG = JumpingSitesFragment.class.getSimpleName();


    private RecyclerView _jumpingSiteLogsRV;

    private JumpingSiteLogsAdapter _jumpingSiteLogsAdapter;
    private ProgressBar _progressBar;
    JumpingSiteRepository _jumpingSiteRepository;
    JumpingSite chosenJumpingSite;


    TextView _name;
    TextView _difficultyLevel;
    TextView _height;
    TextView _rate;
    Button _addNewLog;
    ImageView _imageView;


    public JumpingSiteLogsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jumpingsite_detailsandlogs, container, false);

        _name = view.findViewById(R.id.xml_JumpingSiteNameValue);
        _difficultyLevel = view.findViewById(R.id.xml_JumpingSiteDifficultyLevelValue);
        _height = view.findViewById(R.id.xml_JumpingSiteHeightValue);
        _rate = view.findViewById(R.id.xml_JumpingSiteRateValue);
        _addNewLog = view.findViewById(R.id.xml_buttonAddNewLog);
        _imageView = view.findViewById(R.id.xml_imageView);

        chosenJumpingSite = ((JumpingSiteDetailsActivity) getActivity()).chosenJumpingSite;

        _name.setText(chosenJumpingSite.getName());
        _difficultyLevel.setText((chosenJumpingSite.getDifficultyLevel()));
        _height.setText(Integer.toString(chosenJumpingSite.getHeight()));
        _rate.setText(chosenJumpingSite.getRate());

        String imgPath = chosenJumpingSite.getImage() ;
        Glide.with(_imageView)
                .load(GlideHeader.getUrlWithHeaders(imgPath))
                .placeholder(R.drawable.cliffing_logo)
                .into(_imageView);


        _jumpingSiteLogsRV = view.findViewById(R.id.xml_jumpingSiteLogsRV);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        _jumpingSiteLogsRV.setLayoutManager(layoutManager);
        _jumpingSiteLogsAdapter = new JumpingSiteLogsAdapter(null);
        _jumpingSiteRepository = ViewModelProviders.of(this).get(JumpingSiteRepository.class);

        //populate

        _jumpingSiteLogsRV.setAdapter(null);

        _jumpingSiteRepository.getJumpingSiteLogs(chosenJumpingSite.getIdJumpingSite()).observe(getViewLifecycleOwner(), logsList -> {
            _jumpingSiteLogsAdapter = new JumpingSiteLogsAdapter((ArrayList<JumpingSiteLog>) logsList);
            _jumpingSiteLogsRV.setAdapter(_jumpingSiteLogsAdapter);
        });


        _addNewLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User currentUser = LoginActivity.getCurrentUser();  //uncomment this
                //User currentUser = new User(90,"David", "Gerges" , "d@gmail.com"); // and delete this


                JumpingSiteAddLogDialog dialog = new JumpingSiteAddLogDialog(chosenJumpingSite);
                dialog.setTargetFragment(JumpingSiteLogsFragment.this, 1);
                dialog.show(getFragmentManager(),"y");
            }
        });


        return view;
    }


}
