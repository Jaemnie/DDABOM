package com.example.team;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder> {

    private List<NewsFeedItem> newsFeedList;

    public NewsFeedAdapter(List<NewsFeedItem> newsFeedList) {
        this.newsFeedList = newsFeedList;
    }

    @NonNull
    @Override
    public NewsFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_feed, parent, false);
        return new NewsFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedViewHolder holder, int position) {
        NewsFeedItem newsFeedItem = newsFeedList.get(position);
        holder.title.setText(newsFeedItem.getTitle());
        holder.description.setText(newsFeedItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return newsFeedList.size();
    }

    static class NewsFeedViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;

        public NewsFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            description = itemView.findViewById(R.id.news_description);
        }
    }
}
