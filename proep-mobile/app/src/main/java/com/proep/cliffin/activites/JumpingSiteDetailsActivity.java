package com.proep.cliffin.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.proep.cliffin.R;
import com.proep.cliffin.fragments.JumpingSiteLogsFragment;
import com.proep.cliffin.fragments.TokenFragment;
import com.proep.cliffin.models.JumpingSite;
import com.proep.cliffin.models.User;


public class JumpingSiteDetailsActivity extends AppCompatActivity {


    public JumpingSite chosenJumpingSite;
    FragmentTransaction transaction;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jumping_site_details);

        Gson gson = new Gson();
        Bundle extras = getIntent().getExtras();

        String s = extras.getString("sent jumping site");
        chosenJumpingSite = gson.fromJson(s,JumpingSite.class);

        //User currentUser = LoginActivity.getCurrentUser();  //uncomment this
        User currentUser = new User(90,"David", "Gerges" , "d@gmail.com"); // and delete this
        Log.d("test test",Integer.toString(currentUser.getIdUser())  );
        Log.d("test test",currentUser.getFirstName());
        Log.d("test test",currentUser.getLastName());
        Log.d("test test",currentUser.getEmail());


        if (findViewById(R.id.JumpingSiteLogsFragment) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create fragment and give it an argument specifying the article it should show
            JumpingSiteLogsFragment jumpingSiteLogsFragment = new JumpingSiteLogsFragment();
            transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.JumpingSiteLogsFragment, jumpingSiteLogsFragment);
            //transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }





     //   Log.i("OK hehehee", listOfChosenJumpingSiteLogs.toString());


    }




}
