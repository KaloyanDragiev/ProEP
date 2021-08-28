package com.proep.cliffin.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.proep.cliffin.R;
import com.proep.cliffin.activites.NewsDetailsActivity;
import com.proep.cliffin.adapters.ArticleAdapter;
import com.proep.cliffin.models.Article;
import com.proep.cliffin.repositories.NewsRepository;

import java.util.ArrayList;

public class NewsFragment  extends Fragment {
    private RecyclerView _newsRV;

    private ArticleAdapter _newsAdapter;
    private ProgressBar _progressBar;
    NewsRepository _newsRepository;


    public NewsFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news, container, false);

        _newsRV = view.findViewById(R.id.xml_newsRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        _newsRV.setLayoutManager(layoutManager);
        _newsAdapter = new ArticleAdapter(null);
        _newsRepository = ViewModelProviders.of(this).get(NewsRepository.class);
        populateNewsList();


        _newsAdapter.setOnItemClickListener((position, v) -> {
            Article selectedArticle = _newsAdapter.news.get(position);

//            JumpingSiteDetailsDialog dialog = JumpingSiteDetailsDialog.newInstance(selectedJumpingSite);
//            dialog.setTargetFragment(JumpingSitesFragment.this, 1);
//            dialog.show(getFragmentManager(), JumpingSiteDetailsDialog.TAG);

            Intent newsActivityIntent = new Intent(this.getContext(), NewsDetailsActivity.class);
            Gson gson = new Gson();
            String articleToBeSentToNextActivity = gson.toJson(selectedArticle);
            newsActivityIntent.putExtra("sent news article", articleToBeSentToNextActivity);

//            User currentUser = LoginActivity.getCurrentUser();
//            String userToBeSentToNextActivity = gson.toJson(currentUser);
//            detailsActivityIntent.putExtra("sent user", userToBeSentToNextActivity);

            startActivity(newsActivityIntent);
        });


        return view;
    }

    private void populateNewsList() {
        _newsRV.setAdapter(null);
        _newsRepository.getAllNews().observe(getViewLifecycleOwner(), newsList -> {
            _newsAdapter = new ArticleAdapter((ArrayList<Article>) newsList);
            _newsRV.setAdapter(_newsAdapter);
        });

    }
}
