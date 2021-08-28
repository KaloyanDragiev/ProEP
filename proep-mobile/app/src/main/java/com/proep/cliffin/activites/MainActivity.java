package com.proep.cliffin.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.proep.cliffin.R;
import com.proep.cliffin.fragments.EventsFragment;
import com.proep.cliffin.fragments.JumpingSitesFragment;
import com.proep.cliffin.fragments.NewsFragment;
import com.proep.cliffin.fragments.TokenFragment;
import com.proep.cliffin.fragments.WelcomeFragment;
import com.proep.cliffin.models.User;

public class MainActivity extends AppCompatActivity  implements TokenFragment.OnFragmentInteractionListener{

    public ChipNavigationBar _bottomNavigation;

    public static String _obtainedToken;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _bottomNavigation = findViewById(R.id.bottom_navigation);

        if (findViewById(R.id.mainFragment) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            // In this case, Schedule Fragment is showed first
            WelcomeFragment welcomeFragment = new WelcomeFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            welcomeFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainFragment, welcomeFragment).commit();
        }

        _bottomNavigation.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.menu_home:
                        WelcomeFragment welcomeFragment = new WelcomeFragment();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.mainFragment, welcomeFragment);
                        transaction.commit();
                        break;

                    case R.id.menu_jumpingsites:
                        JumpingSitesFragment jumpingSitesFragment = new JumpingSitesFragment();
                        transaction = getSupportFragmentManager().beginTransaction();

                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        transaction.replace(R.id.mainFragment, jumpingSitesFragment);
                        //transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();
                        break;

                    case R.id.menu_events:
                        // Create fragment and give it an argument specifying the article it should show
                        EventsFragment eventsFragment = new EventsFragment();
                        transaction = getSupportFragmentManager().beginTransaction();

                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        transaction.replace(R.id.mainFragment, eventsFragment);
                        //transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();
                        break;

                    case R.id.menu_news:
                        // Create fragment and give it an argument specifying the article it should show
                        NewsFragment newsFragment = new NewsFragment();
                        transaction = getSupportFragmentManager().beginTransaction();

                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        transaction.replace(R.id.mainFragment, newsFragment);
                        //transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();
                        break;
                }
            }
        });
        _bottomNavigation.setItemSelected(R.id.menu_home,true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }


    @Override
    public void onFragmentInteraction(String token) {
        _obtainedToken = token;
        System.out.println(token);
    }
}
