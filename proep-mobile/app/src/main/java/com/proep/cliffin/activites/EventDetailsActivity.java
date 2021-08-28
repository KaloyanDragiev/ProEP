package com.proep.cliffin.activites;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.proep.cliffin.R;
import com.proep.cliffin.fragments.EventDetailFragment;
import com.proep.cliffin.models.Event;
import com.proep.cliffin.models.User;

public class EventDetailsActivity extends AppCompatActivity {
    public Event chosenEvent;
    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Gson gson = new Gson();
        Bundle extras = getIntent().getExtras();

        String s = extras.getString("sent jumping site");
        chosenEvent = gson.fromJson(s, Event.class);

        User currentUser = LoginActivity.getCurrentUser();


        if (findViewById(R.id.EventDetailsFragment) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create fragment and give it an argument specifying the article it should show
            EventDetailFragment eventDetailFragment = new EventDetailFragment();
            transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.EventDetailsFragment, eventDetailFragment);
            //transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
}
