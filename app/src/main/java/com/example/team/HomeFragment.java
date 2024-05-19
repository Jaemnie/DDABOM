package com.example.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager;
    private NewsPagerAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        setupViewPager();
        return view;
    }

    private void setupViewPager() {
        List<NewsItem> newsList = new ArrayList<>();
        newsList.add(new NewsItem("자격증 소식 1", "자세한 설명 1", R.drawable.ele));
        newsList.add(new NewsItem("자격증 소식 2", "자세한 설명 2", R.drawable.ja));
        newsList.add(new NewsItem("자격증 소식 3", "자세한 설명 3", R.drawable.ja2));

        adapter = new NewsPagerAdapter(newsList);
        viewPager.setAdapter(adapter);
    }
}
