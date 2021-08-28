package com.proep.cliffin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proep.cliffin.R;
import com.proep.cliffin.models.Article;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    public ArrayList<Article> news;
    private static ClickListener clickListener;


    //Constructor
    public ArticleAdapter(ArrayList<Article> news) {
        this.news = news;
    }


    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ArticleAdapter.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent, false);
        return new ArticleAdapter.ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ArticleViewHolder holder, int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm", Locale.getDefault());

        holder._articleTitle.setText(news.get(position).getTitle());
        holder._articleDate.setText(dateFormat.format(news.get(position).getTimePublished()));
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    //Custom JumpingSite ViewHolder
    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView _articleTitle;
        private TextView _articleDate;


        public ArticleViewHolder(View view) {
            super(view);
            _articleTitle = view.findViewById(R.id.xml_Article_name);
            _articleDate = view.findViewById(R.id.xml_Article_date);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}

