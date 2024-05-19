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

import java.util.ArrayList;
import java.util.List;

public class CertificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private CertificationAdapter adapter;
    private List<CertificationItem> certificationList;

    public CertificationFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_certification, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 샘플 데이터 추가
        certificationList = new ArrayList<>();
        certificationList.add(new CertificationItem("자격증 1", "설명 1"));
        certificationList.add(new CertificationItem("자격증 2", "설명 2"));
        certificationList.add(new CertificationItem("자격증 3", "설명 3"));

        adapter = new CertificationAdapter(certificationList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
