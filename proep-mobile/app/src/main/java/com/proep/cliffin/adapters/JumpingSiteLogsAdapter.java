package com.proep.cliffin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proep.cliffin.R;
import com.proep.cliffin.models.JumpingSite;
import com.proep.cliffin.models.JumpingSiteLog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class JumpingSiteLogsAdapter extends RecyclerView.Adapter<JumpingSiteLogsAdapter.JumpingSiteLogsViewHolder> {

    public ArrayList<JumpingSiteLog> jumpingSiteLogs;



    //Constructor
    public JumpingSiteLogsAdapter(ArrayList<JumpingSiteLog> jumpingSites)
    { this.jumpingSiteLogs = jumpingSites; }



    @Override
    public int getItemCount() {
        return jumpingSiteLogs.size();
    }

    @NonNull
    @Override
    public JumpingSiteLogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jumpingsitelog_item_layout, parent, false);
        return new JumpingSiteLogsAdapter.JumpingSiteLogsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JumpingSiteLogsViewHolder holder, int position) {

        holder._User_idUser.setText(Integer.toString(jumpingSiteLogs.get(position).getUser_idUser()) );

        Date date = jumpingSiteLogs.get(position).getDateOfLog();

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

        //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        //System.out.println("Converted String: " + strDate);

        holder._dateOfLog.setText(strDate);
        holder._logDescription.setText(jumpingSiteLogs.get(position).getLogDescription());
        holder._rating.setText(Integer.toString(jumpingSiteLogs.get(position).getRating()));

    }



    //Custom JumpingSiteLogsViewHolder ViewHolder
    public class JumpingSiteLogsViewHolder extends RecyclerView.ViewHolder {

        private TextView _User_idUser;
        private TextView _dateOfLog;
        private TextView _logDescription;
        private TextView _rating;

        public JumpingSiteLogsViewHolder(View view) {
            super(view);
            _User_idUser = view.findViewById(R.id.xml_User_idUserValue);
               _dateOfLog = view.findViewById(R.id.xml_dateOfLogValue);
           _logDescription = view.findViewById(R.id.xml_logDescriptionValue);
            _rating = view.findViewById(R.id.xml_ratingValue);

        }


    }
}
