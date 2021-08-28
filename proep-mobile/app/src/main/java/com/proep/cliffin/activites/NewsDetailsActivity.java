package com.proep.cliffin.activites;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.proep.cliffin.R;
import com.proep.cliffin.fragments.NewsDetailsFragment;
import com.proep.cliffin.models.Article;

public class NewsDetailsActivity extends AppCompatActivity {
    public Article chosenArticle;
    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Gson gson = new Gson();
        Bundle extras = getIntent().getExtras();

        String s = extras.getString("sent news article");
        chosenArticle = gson.fromJson(s, Article.class);



        if (findViewById(R.id.NewsDetailsFragment) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create fragment and give it an argument specifying the article it should show
            NewsDetailsFragment newsDetailsFragment = new NewsDetailsFragment();
            transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.NewsDetailsFragment, newsDetailsFragment);
            //transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
}
