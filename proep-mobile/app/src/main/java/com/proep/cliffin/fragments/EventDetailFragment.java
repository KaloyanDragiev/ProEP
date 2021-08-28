package com.proep.cliffin.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.proep.cliffin.R;
import com.proep.cliffin.activites.EventDetailsActivity;
import com.proep.cliffin.api.APIService;
import com.proep.cliffin.api.APIUtils;
import com.proep.cliffin.api.GlideHeader;
import com.proep.cliffin.models.Event;
import com.proep.cliffin.models.JumpingSite;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailFragment extends Fragment {
    //Tag fragment for Dialog linking
    public static final String TAG = JumpingSitesFragment.class.getSimpleName();

    Event chosenEvent;

    private APIService myAPIService = APIUtils.getAPIService();


    TextView _name;
    TextView _date;
    TextView _description;
    TextView _location;
    ImageView _imageViewEvent;


    public EventDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_event_details, container, false);

        _name = view.findViewById(R.id.xml_EventNameValue);
        _date = view.findViewById(R.id.xml_EventDateValue);
        _description = view.findViewById(R.id.xml_EventDescriptionValue);
        _location = view.findViewById(R.id.xml_EventLocationValue);
        _imageViewEvent = view.findViewById(R.id.xml_imageViewEvents);

        chosenEvent = ((EventDetailsActivity)getActivity()).chosenEvent;
        _name.setText(chosenEvent.getName());
        _date.setText(chosenEvent.getDate().toString());
        _description.setText(chosenEvent.getDescription());

        String imgPath = chosenEvent.getImage() ;
        Glide.with(_imageViewEvent)
                .load(GlideHeader.getUrlWithHeaders(imgPath))
                .placeholder(R.drawable.cliffing_logo)
                .into(_imageViewEvent);

        myAPIService.getJumpingSiteByID(chosenEvent.getJumpingSiteID()).enqueue(new Callback<ArrayList<JumpingSite>>() {
            @Override
            public void onResponse(Call<ArrayList<JumpingSite>> call, Response<ArrayList<JumpingSite>> response) {
                Log.i("JumpingSitesResp:", String.valueOf(response));
                if (response.isSuccessful()) {
                    ArrayList<JumpingSite> arr = response.body();
                    Log.i("SuccessByID", arr.toString());
                    _location.setText(arr.get(0).getName());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<JumpingSite>> call, Throwable t) {
                Log.i("onFailure", "Failed getting all JumpingSites!: Call: " + call + " .T: " + t);
            }
        });
        return  view;
    }
}
