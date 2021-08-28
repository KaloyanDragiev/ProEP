package com.proep.cliffin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.proep.cliffin.R;
import com.proep.cliffin.api.GlideHeader;
import com.proep.cliffin.models.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{

    public ArrayList<Event> events;
    private static ClickListener clickListener;


    //Constructor
    public EventAdapter(ArrayList<Event> events)
    { this.events = events; }





    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        EventAdapter.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_layout, parent, false);
        return new EventAdapter.EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm", Locale.getDefault());

        holder._eventName.setText(events.get(position).getName());
        holder._eventDate.setText(dateFormat.format(events.get(position).getDate()));

        try {
            //Pass some params to server so img is already nicely sized and cropped

            String imgPath = events.get(position).getImage() ;
            Glide.with(holder._eventImage)
                    .load(GlideHeader.getUrlWithHeaders(imgPath))
                    .placeholder(R.drawable.cliffing_logo)
                    .into(holder._eventImage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    //Custom JumpingSite ViewHolder
    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView _eventName;
        private TextView _eventDate;
        private ImageView _eventImage;

        public EventViewHolder(View view) {
            super(view);
            _eventName = view.findViewById(R.id.xml_Event_name);
            _eventDate = view.findViewById(R.id.xml_Event_date);
            _eventImage = view.findViewById(R.id.xml_Event_Image);


            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
