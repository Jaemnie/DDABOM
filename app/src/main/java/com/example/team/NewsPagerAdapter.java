package com.example.team;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsPagerAdapter extends RecyclerView.Adapter<NewsPagerAdapter.NewsViewHolder> {

    private List<NewsItem> newsList;

    public NewsPagerAdapter(List<NewsItem> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem newsItem = newsList.get(position);
        holder.title.setText(newsItem.getTitle());
        holder.description.setText(newsItem.getDescription());

        // Glide를 사용하여 이미지 로드
        Glide.with(holder.itemView.getContext())
                .load(newsItem.getImageResId())
                .fitCenter() // centerCrop에서 fitCenter로 변경
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        ImageView image;

        NewsViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            description = itemView.findViewById(R.id.news_description);
            image = itemView.findViewById(R.id.news_image);
        }
    }
}
