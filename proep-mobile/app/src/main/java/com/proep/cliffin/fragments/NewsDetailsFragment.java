package com.proep.cliffin.fragments;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.proep.cliffin.R;
import com.proep.cliffin.activites.NewsDetailsActivity;
import com.proep.cliffin.api.APIService;
import com.proep.cliffin.api.APIUtils;
import com.proep.cliffin.models.Article;


public class NewsDetailsFragment extends Fragment {
    //Tag fragment for Dialog linking
    public static final String TAG = JumpingSitesFragment.class.getSimpleName();

    Article chosenArticle;

    private APIService myAPIService = APIUtils.getAPIService();


    TextView _title;
    TextView _date;
    TextView _body;



    public NewsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news_details, container, false);

        _title = view.findViewById(R.id.xml_Article_title);
        _date = view.findViewById(R.id.xml_Article_date);
        _body = view.findViewById(R.id.xml_Article_body);

        chosenArticle = ((NewsDetailsActivity)getActivity()).chosenArticle;
        _title.setText(chosenArticle.getTitle());
        ;
        _date.setText(java.text.DateFormat.getDateTimeInstance().format(chosenArticle.getTimePublished()));
        _body.setText(chosenArticle.getBody());

        return  view;
    }
}
