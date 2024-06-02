package com.example.team;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.team.database.DatabaseHelper;
import com.example.team.perser.JanetParser;
import com.example.team.perser.DataCallback;

import java.util.ArrayList;
import java.util.List;

public class CertificationFragment extends Fragment {

    // ListView와 어댑터 선언
    private ListView listView;
    private CertificationAdapter adapter;
    // 자격증 목록을 저장할 리스트
    private List<CertificationItem> certificationList;
    private List<CertificationItem> filteredCertificationList; // 필터링된 리스트
    // 데이터베이스 헬퍼 객체 선언
    private DatabaseHelper dbHelper;
    private EditText searchEditText; // 검색어 입력 창
    // 상세정보 표시 다이얼로그
    private Dialog showDetailCert;
    private int selectItem;

    // 필수적으로 빈 생성자 선언
    public CertificationFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 프래그먼트 레이아웃을 인플레이트
        View view = inflater.inflate(R.layout.fragment_certification, container, false);
        listView = view.findViewById(R.id.list_view);
        searchEditText = view.findViewById(R.id.search_edit_text); // 검색어 입력 창 초기화

        // DatabaseHelper 초기화
        dbHelper = new DatabaseHelper(getContext());

        // 데이터베이스에서 데이터를 불러옵니다.
        loadDataFromDatabase();

        // 검색 기능 설정
        setupSearchFunctionality();

        // API에서 데이터를 가져와서 데이터베이스에 저장하고, UI를 업데이트합니다.
        fetchCertificationsFromAPI();

        return view;
    }

    // 데이터베이스에서 데이터를 불러오는 메서드
    private void loadDataFromDatabase() {
        certificationList = dbHelper.getAllLicenses();
        filteredCertificationList = new ArrayList<>(certificationList); // 초기 필터링된 리스트 설정
        adapter = new CertificationAdapter(getContext(), filteredCertificationList);
        listView.setAdapter(adapter);
        //자격증 아이템 클릭시
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem=position;
                /*
                Toast.makeText(getContext(),certificationList.get(selectItem).getTitle() ,Toast.LENGTH_SHORT).show();
                */
                CertificationItem selectedItem = certificationList.get(selectItem);
                String Host=selectedItem.getDescription(); // 주관기관 임시로
                CertificationDialog dialog = new CertificationDialog(getContext(), selectedItem.getTitle(), "","",Host);
                dialog.show();
            }
        });
    }

    // 검색 기능 설정 메서드
    private void setupSearchFunctionality() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    // 필터링 메서드
    private void filter(String text) {
        filteredCertificationList.clear();
        if (text.isEmpty()) {
            filteredCertificationList.addAll(certificationList);
        } else {
            for (CertificationItem item : certificationList) {
                if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filteredCertificationList.add(item);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    // API에서 데이터를 가져와서 데이터베이스에 저장하고 UI를 업데이트하는 메서드
    private void fetchCertificationsFromAPI() {
        // 데이터베이스 초기화 (기존 데이터 삭제)
        dbHelper.deleteAllLicenses();

        JanetParser parser = new JanetParser(getContext());
        parser.Janet_list(new DataCallback() {
            @Override
            public void onDataReceived(List<Object[]> data) {
                certificationList.clear(); // 기존 리스트 초기화
                for (Object[] item : data) {
                    String title = (String) item[1];
                    String description = (String) item[2];
                    certificationList.add(new CertificationItem(title, description));
                }
                // 필터링된 리스트 업데이트
                filter(searchEditText.getText().toString());
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
