package com.proep.cliffin.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.proep.cliffin.activites.LoginActivity;
import com.proep.cliffin.activites.MainActivity;
import com.proep.cliffin.api.APIService;
import com.proep.cliffin.api.APIUtils;
import com.proep.cliffin.models.JumpingSite;
import com.proep.cliffin.models.JumpingSiteLog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JumpingSiteRepository extends ViewModel {

    private MutableLiveData<List<JumpingSite>> jumpingSiteLiveData = new MutableLiveData<>();
    private MutableLiveData<List<JumpingSiteLog>> jumpingSiteLogLiveData = new MutableLiveData<>();
    private APIService myAPIService = APIUtils.getAPIService();

    public MutableLiveData<List<JumpingSite>> searchJumpingSites(String searchTxt) {



        myAPIService.searchJumpingSite(searchTxt, "Bearer ").enqueue(new Callback<ArrayList<JumpingSite>>() {
            @Override
            public void onResponse(Call<ArrayList<JumpingSite>> call, Response<ArrayList<JumpingSite>> response) {
                Log.i("JumpingSitesResp:", String.valueOf(response));
                if (response.isSuccessful()) {
                    ArrayList<JumpingSite> JumpingSitesArray = response.body();
                    jumpingSiteLiveData.setValue(JumpingSitesArray);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<JumpingSite>> call, Throwable t) {
                Log.i("onFailure", "Failed getting all JumpingSites!: Call: " + call + " .T: " + t);
            }
        });

        return jumpingSiteLiveData;

    }

    public MutableLiveData<List<JumpingSite>> getAllJumpingSites() {

        //String authHeader = "Bearer " + MainActivity._obtainedToken;
        String authHeader = "Bearer " ;
        myAPIService.getAllJumpingSites(authHeader).enqueue(new Callback<ArrayList<JumpingSite>>() {
            @Override
            public void onResponse(Call<ArrayList<JumpingSite>> call, Response<ArrayList<JumpingSite>> response) {
                Log.i("JumpingSitesResp:", String.valueOf(response));
                if (response.isSuccessful()) {
                    ArrayList<JumpingSite> jumpingSitesArray = response.body();
                    jumpingSiteLiveData.setValue(jumpingSitesArray);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<JumpingSite>> call, Throwable t) {
                Log.i("onFailure", "Failed getting all JumpingSites!: Call: " + call + " .T: " + t);
            }
        });

        return jumpingSiteLiveData;
    }

    public MutableLiveData<List<JumpingSiteLog>> getJumpingSiteLogs (Integer id)
    {
       // String authHeader = "Bearer " + MainActivity._obtainedToken;
        String authHeader = "Bearer " ;

        myAPIService.getJumpingSiteLogs(id, authHeader).enqueue(new Callback<ArrayList<JumpingSiteLog>>() {
            @Override
            public void onResponse(Call<ArrayList<JumpingSiteLog>> call, Response<ArrayList<JumpingSiteLog>> response) {
                Log.i("getJumpingSiteLogs res:", String.valueOf(response));
                if (response.isSuccessful()) {
                    Log.i("getall log onSuccess", "succesful posting new log Call: " + call );

                    ArrayList<JumpingSiteLog> arr = response.body();
                    jumpingSiteLogLiveData.setValue(arr);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<JumpingSiteLog>> call, Throwable t) {
                Log.i("onFailure", "Failed getting all JumpingSites!: Call: " + call + " .T: " + t);
            }
        });

        return jumpingSiteLogLiveData;
    }

    public void postJumpingSiteLog (JumpingSiteLog log)
    {
        // String authHeader = "Bearer " + MainActivity._obtainedToken;
        String authHeader = "Bearer " ;

        myAPIService.postJumpingSiteLog(log.getJumpingSite_idJumpingSite(), LoginActivity.getCookie(),log.getLogDescription(), log.getRating(), authHeader).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("posting log response:", String.valueOf(response));
                if (response.isSuccessful()) {
                    //ArrayList<JumpingSiteLog> arr = response.body();
                    Log.i("posting log onSuccess", "succesful posting new log Call: " + call );
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("posting log onFailure", "Failed posting new log Call: " + call + " .T: " + t);
            }
        });


    }
}
