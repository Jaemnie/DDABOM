package com.example.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager;
    private NewsPagerAdapter pagerAdapter;
    private RecyclerView newsRecyclerView;
    private NewsFeedAdapter newsFeedAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        newsRecyclerView = view.findViewById(R.id.newsRecyclerView);

        setupViewPager();
        setupNewsFeed();

        return view;
    }

    private void setupViewPager() {
        List<NewsItem> newsList = new ArrayList<>();
        newsList.add(new NewsItem(R.drawable.ele));
        newsList.add(new NewsItem(R.drawable.ja));
        newsList.add(new NewsItem(R.drawable.ja2));

        pagerAdapter = new NewsPagerAdapter(newsList);
        viewPager.setAdapter(pagerAdapter);
    }

    private void setupNewsFeed() {
        List<NewsFeedItem> newsFeedList = new ArrayList<>();
        // Add news feed items
        newsFeedList.add(new NewsFeedItem("News 1", "News Description 1"));
        newsFeedList.add(new NewsFeedItem("News 2", "News Description 2"));
        newsFeedList.add(new NewsFeedItem("News 3", "News Description 3"));

        newsFeedAdapter = new NewsFeedAdapter(newsFeedList);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRecyclerView.setAdapter(newsFeedAdapter);
    }
}
