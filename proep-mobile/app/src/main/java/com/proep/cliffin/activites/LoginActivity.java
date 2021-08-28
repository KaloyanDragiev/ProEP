package com.proep.cliffin.activites;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.lifecycle.MutableLiveData;
        import androidx.lifecycle.ViewModelProviders;

        import com.google.gson.Gson;
        import com.proep.cliffin.R;
        import com.proep.cliffin.api.APIService;
        import com.proep.cliffin.api.APIUtils;
        import com.proep.cliffin.models.LoginRequest;
        import com.proep.cliffin.models.RegisterRequest;
        import com.proep.cliffin.models.User;
        import com.proep.cliffin.repositories.AuthenticationRepository;

        import android.content.Intent;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.View;
        import android.os.Bundle;
        import android.widget.Button;
        import android.widget.RelativeLayout;
        import android.widget.RelativeLayout.LayoutParams;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {

    Button _btnSubmit;
    Button _btnChange;
    TextView _loginTextView;
    TextView _firstNameTextView;
    TextView _lastNameTextView;
    TextView _usernameTextView;
    TextView _passwordTextView;

    Boolean signUpModeActive = true;
    private AuthenticationRepository _authRepository;
    private APIService myAPIService = APIUtils.getAPIService();
    private MutableLiveData<ArrayList<User>> loginResponse = new MutableLiveData<>();
    public static String myCookie="";
//

    public static User currentUser;

    public static User getCurrentUser()
    {
        return currentUser;
    }
    public static String getCookie()
    {
        return myCookie;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginTextView) {

            Button signUpButton = findViewById(R.id.signUpButton);

            if (signUpModeActive) {
                signUpModeActive = false;
                signUpButton.setText("Login");
                _loginTextView.setText("or, Sign Up");
                _firstNameTextView.setVisibility(View.INVISIBLE);
                _lastNameTextView.setVisibility(View.INVISIBLE);
                LayoutParams params = (LayoutParams) _usernameTextView.getLayoutParams();
                params.addRule(RelativeLayout.BELOW, R.id.imageView2);
                _usernameTextView.setLayoutParams(params);
            } else {
                signUpModeActive = true;
                signUpButton.setText("Sign Up");
                _loginTextView.setText("or, Login");
                _firstNameTextView.setVisibility(View.VISIBLE);
                _lastNameTextView.setVisibility(View.VISIBLE);
                LayoutParams params = (LayoutParams) _usernameTextView.getLayoutParams();
                params.addRule(RelativeLayout.BELOW, R.id.lastNameEditText);
                _usernameTextView.setLayoutParams(params);
            }

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _loginTextView = findViewById(R.id.loginTextView);
        _loginTextView.setOnClickListener(this);

        _firstNameTextView = findViewById(R.id.firstNameEditText);
        _lastNameTextView = findViewById(R.id.lastNameEditText);
        _usernameTextView = findViewById(R.id.usernameEditText);
        _passwordTextView = findViewById(R.id.passwordEditText);


        _authRepository = ViewModelProviders.of(this).get(AuthenticationRepository.class);


        _btnSubmit = findViewById(R.id.signUpButton);
//        _loginTextView = findViewById(R.id.loginTextView);
//
//        if (_btnSubmit != null){
//            Log.i("tag", "not null");
//        }
//        else{
//            Log.i("tag", "null");
//        }
//
////

        _btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(signUpModeActive){
                    //_authRepository.login(new LoginRequest("m.bergs@student.fontys.nl", "3456789"));
                    myAPIService.registerAccount(new RegisterRequest(_usernameTextView.getText().toString(),_passwordTextView.getText().toString(),_firstNameTextView.getText().toString(),_lastNameTextView.getText().toString(),"User")).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.i("PeopleResp:", String.valueOf(response));
                            if (response.isSuccessful()) {

//                            registerResponse.setValue(registerArray);

                                Intent i = new Intent(getApplicationContext(),MainActivity.class);

                                //Pass first/last Name to the next activity

                                startActivity(i);
                            }
                            else{
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Server Responded with error when registering!",
                                        Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 0);
                                toast.show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.i("onFailure", "Failed Login!: Call: " + call + " .T: " + t);

                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "A connection or network error has occurred !",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 0);
                            toast.show();

                        }
                    });


                }

                else {
                //_authRepository.login(new LoginRequest("m.bergs@student.fontys.nl", "3456789"));
                    myAPIService.isValidUser(new LoginRequest(_usernameTextView.getText().toString(), _passwordTextView.getText().toString())).enqueue(new Callback<ArrayList<User>>() {
                    @Override
                    public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                        Log.i("PeopleResp:", String.valueOf(response));
                        if (response.isSuccessful()) {
                            ArrayList<User> loginArray = response.body();

                            myCookie = response.headers().get("Set-Cookie");
                            Log.i("tesssst:", myCookie);


                            loginResponse.setValue(loginArray);
                            Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            //Pass first/last Name to the next activity

                            User u = new User(loginArray.get(0).getIdUser(),loginArray.get(0).getFirstName(),loginArray.get(0).getLastName(),loginArray.get(0).getEmail());

                            currentUser = u;


                            startActivity(i);
                        }
                        else{
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Wrong Credentials!",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 0);
                            toast.show();

                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                        Log.i("onFailure", "Failed Login!: Call: " + call + " .T: " + t);

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "A connection or network error has occurred !",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();


                    }
                });

            }


            }
        });
    }
}
