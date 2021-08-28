package com.proep.cliffin.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.proep.cliffin.activites.MainActivity;
import com.proep.cliffin.api.APIService;
import com.proep.cliffin.api.APIUtils;
import com.proep.cliffin.models.Event;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventRepository extends ViewModel {

    private MutableLiveData<List<Event>> eventLiveData = new MutableLiveData<>();
    private APIService myAPIService = APIUtils.getAPIService();

    public MutableLiveData<List<Event>> searchEvents(String searchTxt) {

        String authHeader = "Bearer " + MainActivity._obtainedToken;


        myAPIService.searchEvent(searchTxt, authHeader).enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                Log.i("EventResp:", String.valueOf(response));
                if (response.isSuccessful()) {
                    ArrayList<Event> EventsArray = response.body();
                    eventLiveData.setValue(EventsArray);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                Log.i("onFailure", "Failed getting all JumpingSites!: Call: " + call + " .T: " + t);
            }
        });

        return eventLiveData;

    }

    public MutableLiveData<List<Event>> getAllEvents() {

        //String authHeader = "Bearer " + MainActivity._obtainedToken;
        String authHeader = "Bearer " ;
        myAPIService.getAllEvents(authHeader).enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                Log.i("EventsResp:", String.valueOf(response));
                if (response.isSuccessful()) {
                    ArrayList<Event> eventsArray = response.body();
                    eventLiveData.setValue(eventsArray);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                Log.i("onFailure", "Failed getting all JumpingSites!: Call: " + call + " .T: " + t);
            }
        });

        return eventLiveData;
    }
}
