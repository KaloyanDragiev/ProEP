package com.proep.cliffin.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.proep.cliffin.R;

import com.proep.cliffin.activites.EventDetailsActivity;
import com.proep.cliffin.adapters.EventAdapter;
import com.proep.cliffin.models.Event;
import com.proep.cliffin.repositories.EventRepository;

import java.util.ArrayList;

public class EventsFragment  extends Fragment {

    private RecyclerView _eventRV;

    private EventAdapter _eventAdapter;
    private ProgressBar _progressBar;
    EventRepository _eventRepository;

    private ImageButton searchButton, resetButton;
    private EditText searchText;

    public EventsFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_event, container, false);

        _eventRV = view.findViewById(R.id.xml_eventRV);
        _progressBar = view.findViewById(R.id.xml_progress_bar);
        searchButton = view.findViewById(R.id.xml_searchButton);
        resetButton = view.findViewById(R.id.xml_resetButton);
        searchText = view.findViewById(R.id.xml_searchText);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        _eventRV.setLayoutManager(layoutManager);
        _eventAdapter = new EventAdapter(null);
        _eventRepository = ViewModelProviders.of(this).get(EventRepository.class);
        populateEventsList();

        searchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    searchText.setText("");
                }
            }
        });


        searchButton.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            String searchTxt = searchText.getText().toString();
            _eventRV.setAdapter(null);

            _eventRepository.searchEvents(searchTxt).observe(getViewLifecycleOwner(), EventsArray -> {
                _eventAdapter = new EventAdapter((ArrayList<Event>) EventsArray);
                _eventRV.setAdapter(_eventAdapter);
                searchText.clearFocus();
            });
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _eventRV.setAdapter(null);
                searchText.setText("Search...");
                populateEventsList();
            }
        });

        _eventAdapter.setOnItemClickListener((position, v) -> {
            Event selectedEvent = _eventAdapter.events.get(position);

//            JumpingSiteDetailsDialog dialog = JumpingSiteDetailsDialog.newInstance(selectedJumpingSite);
//            dialog.setTargetFragment(JumpingSitesFragment.this, 1);
//            dialog.show(getFragmentManager(), JumpingSiteDetailsDialog.TAG);

            Intent detailsActivityIntent = new Intent(this.getContext(), EventDetailsActivity.class);
            Gson gson = new Gson();
            String eventToBeSentToNextActivity = gson.toJson(selectedEvent);
            detailsActivityIntent.putExtra("sent jumping site", eventToBeSentToNextActivity);

//            User currentUser = LoginActivity.getCurrentUser();
//            String userToBeSentToNextActivity = gson.toJson(currentUser);
//            detailsActivityIntent.putExtra("sent user", userToBeSentToNextActivity);

            startActivity(detailsActivityIntent);
        });


        return view;
    }

    private void populateEventsList() {
        _eventRV.setAdapter(null);
        _progressBar.setVisibility(View.VISIBLE);
        _eventRepository.getAllEvents().observe(getViewLifecycleOwner(), peopleList -> {
            _eventAdapter = new EventAdapter((ArrayList<Event>) peopleList);
            _eventRV.setAdapter(_eventAdapter);
            _progressBar.setVisibility(View.GONE);
        });

    }
}
