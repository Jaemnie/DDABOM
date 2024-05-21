package com.example.team;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CertificationAdapter extends RecyclerView.Adapter<CertificationAdapter.CertificationViewHolder> {

    // 자격증 목록을 저장할 리스트
    private List<CertificationItem> certificationList;

    // 생성자: 자격증 목록을 전달받아 초기화
    public CertificationAdapter(List<CertificationItem> certificationList) {
        this.certificationList = certificationList;
    }

    @NonNull
    @Override
    public CertificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 아이템 뷰를 위한 레이아웃 인플레이터 설정
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_certification, parent, false);
        return new CertificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CertificationViewHolder holder, int position) {
        // 자격증 아이템 데이터를 뷰홀더에 바인딩
        CertificationItem certificationItem = certificationList.get(position);
        holder.title.setText(certificationItem.getTitle());
        holder.description.setText(certificationItem.getDescription());
    }

    @Override
    public int getItemCount() {
        // 자격증 목록의 아이템 수 반환
        return certificationList.size();
    }

    static class CertificationViewHolder extends RecyclerView.ViewHolder {

        // 자격증 제목과 설명을 표시하는 텍스트뷰
        TextView title;
        TextView description;

        // 뷰홀더 생성자: 뷰를 전달받아 텍스트뷰를 초기화
        CertificationViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.certification_title);
            description = itemView.findViewById(R.id.certification_description);
        }
    }
}
