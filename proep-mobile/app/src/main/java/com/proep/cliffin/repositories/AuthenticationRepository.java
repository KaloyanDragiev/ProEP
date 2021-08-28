package com.proep.cliffin.repositories;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.proep.cliffin.api.APIService;
import com.proep.cliffin.api.APIUtils;
import com.proep.cliffin.models.User;

public class AuthenticationRepository extends ViewModel {

    private APIService myAPIService = APIUtils.getAPIService();
    private MutableLiveData<User> loginResponse;

//    public MutableLiveData<User> login(LoginRequest body) {
//        myAPIService.isValidUser(body).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                Log.i("PeopleResp:", String.valueOf(response));
//                if (response.isSuccessful()) {
////                    ArrayList<JumpingSite> jumpingSitesArray = response.body();
////                    jumpingSiteLiveData.setValue(jumpingSitesArray);
//                    loginResponse.setValue(response.body());
//                }
//            }
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.i("onFailure", "Failed Login!: Call: " + call + " .T: " + t);
//            }
//        });
//        return loginResponse;
//    }

}
