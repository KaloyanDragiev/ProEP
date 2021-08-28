package com.proep.cliffin.dialoges;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.proep.cliffin.R;
import com.proep.cliffin.activites.LoginActivity;
import com.proep.cliffin.fragments.JumpingSiteLogsFragment;
import com.proep.cliffin.models.JumpingSite;
import com.proep.cliffin.models.JumpingSiteLog;
import com.proep.cliffin.models.User;
import com.proep.cliffin.repositories.JumpingSiteRepository;

import java.time.LocalDateTime;
import java.util.Date;

public class JumpingSiteAddLogDialog extends DialogFragment {

    //Tag fragment for Dialog
    public static final String TAG = JumpingSiteAddLogDialog.class.getSimpleName();
    public EditText _rating;
    public EditText _description;
    Button _btnPostLog;

    User currentUser;
    JumpingSite chosenJumpingsite;
    JumpingSiteRepository _repository;





    public JumpingSiteAddLogDialog(JumpingSite site) {

        currentUser= LoginActivity.currentUser;  // put this back
        //currentUser = new User(90,"David", "Gerges" , "d@gmail.com"); // and delete this
        chosenJumpingsite=site;
    }


//    public static JumpingSiteAddLogDialog newInstance(JumpingSite jumpingSite) {
//        JumpingSiteAddLogDialog dialog = new JumpingSiteAddLogDialog();
//        return dialog;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.jumpingsite_details_dialog, container, false);
        _repository = new JumpingSiteRepository();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _rating = view.findViewById(R.id.xml_DialogRatingValue);
        _description = view.findViewById(R.id.xml_DialogDescriptionValue);
        _btnPostLog = view.findViewById(R.id.xml_buttonPostLog);


        _btnPostLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // int user_idUser = currentUser.getIdUser(); - will be obtained from the session
                String logDescription = _description.getText().toString();
                Integer rating = Integer.parseInt(_rating.getText().toString());
                //Date dateOfLog = LocalDateTime.now();
                int jumpingSite_idJumpingSite = chosenJumpingsite.getIdJumpingSite();

                JumpingSiteLog toBePosted = new JumpingSiteLog(logDescription,  rating,
                  jumpingSite_idJumpingSite);

                // Add the post log stuff here
                _repository.postJumpingSiteLog(toBePosted);

                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


                dismiss();
            }
        });
//        String nameArg = getArguments().getString("name", "Name");
//        name.setText(nameArg);


//        img = view.findViewById(R.id.xml_jumpingSiteDetailImage);
//        String imgArg = getArguments().getString("img", "");

//        try {
//            //Pass some params to server so img is already nicely sized and cropped
//            String imgPath = imgArg + "?width=158&height=158&mode=crop&anchor=top";
//            Glide.with(img)
//                    .load(GlideHeader.getUrlWithHeaders(imgPath))
//                    .into(img);
//        } catch (Exception e) {
//            System.out.println(e);
//        }

    }
}
