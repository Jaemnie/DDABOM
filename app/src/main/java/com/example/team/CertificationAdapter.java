package com.example.team;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CertificationAdapter extends ArrayAdapter<CertificationItem> {

    // 생성자: 자격증 목록을 전달받아 초기화
    public CertificationAdapter(Context context, List<CertificationItem> certificationList) {
        super(context, 0, certificationList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 자격증 아이템 데이터를 가져옴
        CertificationItem certificationItem = getItem(position);

        // convertView가 null이면 새로운 뷰를 생성함
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_certification, parent, false);
        }

        // 자격증 제목과 설명을 표시하는 텍스트뷰를 초기화
        TextView title = convertView.findViewById(R.id.certification_title);
        TextView description = convertView.findViewById(R.id.certification_description);

        // 자격증 아이템 데이터를 텍스트뷰에 설정
        if (certificationItem != null) {
            title.setText(certificationItem.getTitle());
            description.setText(certificationItem.getDescription());
        }

        return convertView;
    }
}
