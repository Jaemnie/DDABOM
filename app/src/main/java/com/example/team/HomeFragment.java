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

import com.example.team.perser.DataCallback;
import com.example.team.perser.JanetParser;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager;
    private NewsPagerAdapter pagerAdapter;
    private RecyclerView newsRecyclerView;
    private NewsFeedAdapter newsFeedAdapter;
    private List<NewsFeedItem> newsFeedList;
    private List<NewsItem> newsList;

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

        // JanetParser를 통해 매거진 데이터 가져오기
        fetchMagazineData();

        return view;
    }

    private void setupViewPager() {
        newsList = new ArrayList<>();
        pagerAdapter = new NewsPagerAdapter(newsList);
        viewPager.setAdapter(pagerAdapter);
    }

    private void setupNewsFeed() {
        newsFeedList = new ArrayList<>();
        newsFeedAdapter = new NewsFeedAdapter(newsFeedList);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRecyclerView.setAdapter(newsFeedAdapter);
    }

    private void fetchMagazineData() {
        JanetParser parser = new JanetParser(getContext());
        parser.Janet_Magazine("https://janet.co.kr/bbs/board.php?bo_table=bMagazine&wr_id=217&page=1", new DataCallback() {
            @Override
            public void onDataReceived(List<Object[]> data) {
                for (Object[] item : data) {
                    String link = (String) item[0];
                    String imgSrc = (String) item[1];
                    String title = (String) item[2];
                    String description = (String) item[3];
                    newsFeedList.add(new NewsFeedItem(title, description));
                    newsList.add(new NewsItem(imgSrc));
                }
                // UI를 업데이트하기 위해 메인 스레드에서 어댑터에 데이터 변경 알림
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        newsFeedAdapter.notifyDataSetChanged();
                        pagerAdapter.notifyDataSetChanged();
                    });
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
