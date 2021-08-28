package com.proep.cliffin.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.proep.cliffin.activites.MainActivity;
import com.proep.cliffin.api.APIService;
import com.proep.cliffin.api.APIUtils;
import com.proep.cliffin.models.Article;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository extends ViewModel {
    private MutableLiveData<List<Article>> newsLiveData = new MutableLiveData<>();
    private APIService myAPIService = APIUtils.getAPIService();


    public MutableLiveData<List<Article>> getAllNews() {

        myAPIService.getAllNews().enqueue(new Callback<ArrayList<Article>>() {
            @Override
            public void onResponse(Call<ArrayList<Article>> call, Response<ArrayList<Article>> response) {
                Log.i("EventsResp:", String.valueOf(response));
                if (response.isSuccessful()) {
                    ArrayList<Article> newsArray = response.body();
                    newsLiveData.setValue(newsArray);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Article>> call, Throwable t) {
                Log.i("onFailure", "Failed getting all news!: Call: " + call + " .T: " + t);
            }
        });

        return newsLiveData;
    }
}
