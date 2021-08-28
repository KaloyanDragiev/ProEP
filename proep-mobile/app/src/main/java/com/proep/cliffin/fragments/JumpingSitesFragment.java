package com.proep.cliffin.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.proep.cliffin.activites.JumpingSiteDetailsActivity;
import com.proep.cliffin.adapters.JumpingSiteAdapter;
import com.proep.cliffin.repositories.JumpingSiteRepository;
import androidx.lifecycle.ViewModelProviders;
import com.proep.cliffin.R;
import com.proep.cliffin.models.JumpingSite;

import java.util.ArrayList;


public class JumpingSitesFragment extends Fragment {

    //Tag fragment for Dialog linking
    public static final String TAG = JumpingSitesFragment.class.getSimpleName();


    private RecyclerView _jumpingSiteRV;

    private JumpingSiteAdapter _jumpingSiteAdapter;
    private ProgressBar _progressBar;
    JumpingSiteRepository _jumpingSiteRepository;

    private ImageButton searchButton, resetButton;
    private EditText searchText;

    public JumpingSitesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_jumpingsite, container, false);

        _jumpingSiteRV = view.findViewById(R.id.xml_jumpingSiteRV);
        _progressBar = view.findViewById(R.id.xml_progress_bar);
        searchButton = view.findViewById(R.id.xml_searchButton);
        resetButton = view.findViewById(R.id.xml_resetButton);
        searchText = view.findViewById(R.id.xml_searchText);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        _jumpingSiteRV.setLayoutManager(layoutManager);
        _jumpingSiteAdapter = new JumpingSiteAdapter(null);
        _jumpingSiteRepository = ViewModelProviders.of(this).get(JumpingSiteRepository.class);
        populateJumpinSitesList();

        searchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    searchText.setText("");
                }
            }
        });


        searchButton.setOnClickListener(v -> {
            Log.i("OK", "GOT IT");
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            String searchTxt = searchText.getText().toString();
            _jumpingSiteRV.setAdapter(null);

            _jumpingSiteRepository.searchJumpingSites(searchTxt).observe(getViewLifecycleOwner(), JumpingSitesArray -> {
                _jumpingSiteAdapter = new JumpingSiteAdapter((ArrayList<JumpingSite>) JumpingSitesArray);
                _jumpingSiteRV.setAdapter(_jumpingSiteAdapter);
                searchText.clearFocus();
            });
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _jumpingSiteRV.setAdapter(null);
                searchText.setText("Search...");
                populateJumpinSitesList();
            }
        });

        _jumpingSiteAdapter.setOnItemClickListener((position, v) -> {
            JumpingSite selectedJumpingSite = _jumpingSiteAdapter.jumpingSites.get(position);

//            JumpingSiteAddLogDialog dialog = JumpingSiteAddLogDialog.newInstance(selectedJumpingSite);
//            dialog.setTargetFragment(JumpingSitesFragment.this, 1);
//            dialog.show(getFragmentManager(), JumpingSiteAddLogDialog.TAG);

            Intent detailsActivityIntent = new Intent(this.getContext(), JumpingSiteDetailsActivity.class);
            Gson gson = new Gson();
            String jumpingSiteToBeSentToNextActivity = gson.toJson(selectedJumpingSite);
            detailsActivityIntent.putExtra("sent jumping site", jumpingSiteToBeSentToNextActivity);

//            User currentUser = LoginActivity.getCurrentUser();
//            String userToBeSentToNextActivity = gson.toJson(currentUser);
//            detailsActivityIntent.putExtra("sent user", userToBeSentToNextActivity);

            startActivity(detailsActivityIntent);
        });


        return view;
    }

    private void populateJumpinSitesList() {
        _jumpingSiteRV.setAdapter(null);
        _progressBar.setVisibility(View.VISIBLE);
        _jumpingSiteRepository.getAllJumpingSites().observe(getViewLifecycleOwner(), peopleList -> {
            _jumpingSiteAdapter = new JumpingSiteAdapter((ArrayList<JumpingSite>) peopleList);
            _jumpingSiteRV.setAdapter(_jumpingSiteAdapter);
            _progressBar.setVisibility(View.GONE);
        });

    }

}
