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

import com.example.team.database.DatabaseHelper;
import com.example.team.perser.JanetParser;
import com.example.team.perser.DataCallback;

import java.util.List;

public class CertificationFragment extends Fragment {

    // RecyclerView와 어댑터 선언
    private RecyclerView recyclerView;
    private CertificationAdapter adapter;
    // 자격증 목록을 저장할 리스트
    private List<CertificationItem> certificationList;
    // 데이터베이스 헬퍼 객체 선언
    private DatabaseHelper dbHelper;

    // 필수적으로 빈 생성자 선언
    public CertificationFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 프래그먼트 레이아웃을 인플레이트
        View view = inflater.inflate(R.layout.fragment_certification, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // DatabaseHelper 초기화
        dbHelper = new DatabaseHelper(getContext());

        // 데이터베이스에서 데이터를 불러옵니다.
        loadDataFromDatabase();

        // API에서 데이터를 가져와서 데이터베이스에 저장하고, UI를 업데이트합니다.
        fetchCertificationsFromAPI();

        return view;
    }

    // 데이터베이스에서 데이터를 불러오는 메서드
    private void loadDataFromDatabase() {
        certificationList = dbHelper.getAllLicenses();
        adapter = new CertificationAdapter(certificationList);
        recyclerView.setAdapter(adapter);
    }

    // API에서 데이터를 가져와서 데이터베이스에 저장하고 UI를 업데이트하는 메서드
    private void fetchCertificationsFromAPI() {
        JanetParser parser = new JanetParser(getContext());
        parser.Janet_list(new DataCallback() {
            @Override
            public void onDataReceived(List<Object[]> data) {
                for (Object[] item : data) {
                    String title = (String) item[1];
                    String description = (String) item[2];
                    certificationList.add(new CertificationItem(title, description));
                }
                // UI를 업데이트하기 위해 메인 스레드에서 어댑터에 데이터 변경 알림
                getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
