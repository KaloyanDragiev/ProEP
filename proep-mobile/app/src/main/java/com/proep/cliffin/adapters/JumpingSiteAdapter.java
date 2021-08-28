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
import com.proep.cliffin.models.JumpingSite;

import java.util.ArrayList;
import com.proep.cliffin.api.GlideHeader;

public class JumpingSiteAdapter extends RecyclerView.Adapter<JumpingSiteAdapter.JumpingSiteViewHolder>{


    public ArrayList<JumpingSite> jumpingSites;
    private static ClickListener clickListener;


    //Constructor
    public JumpingSiteAdapter(ArrayList<JumpingSite> jumpingSites)
    { this.jumpingSites = jumpingSites; }





    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        JumpingSiteAdapter.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return jumpingSites.size();
    }

    @NonNull
    @Override
    public JumpingSiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jumpingsite_item_layout, parent, false);
        return new JumpingSiteAdapter.JumpingSiteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JumpingSiteViewHolder holder, int position) {

        holder._jumpingSiteName.setText(jumpingSites.get(position).getName());
        holder._jumpingSiteDifficulty.setText("Difficulty: " + jumpingSites.get(position).getDifficultyLevel());

        try {
            //Pass some params to server so img is already nicely sized and cropped
            //String imgPath = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png";
            String imgPath = jumpingSites.get(position).getImage() ;
            Glide.with(holder._jumpingSiteImage)
                    .load(GlideHeader.getUrlWithHeaders(imgPath))
                    .placeholder(R.drawable.cliffing_logo)
                    .into(holder._jumpingSiteImage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    //Custom JumpingSite ViewHolder
    public class JumpingSiteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView _jumpingSiteName;
        private TextView _jumpingSiteDifficulty;
        private ImageView _jumpingSiteImage;

        public JumpingSiteViewHolder(View view) {
            super(view);
            _jumpingSiteName = view.findViewById(R.id.xml_JumpingSite_name);
            _jumpingSiteDifficulty = view.findViewById(R.id.xml_JumpingSite_Difficulty_Level);
            _jumpingSiteImage = view.findViewById(R.id.xml_jmpingsite_Image);


            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

}
