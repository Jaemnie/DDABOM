package com.example.team;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CertificationAdapter extends RecyclerView.Adapter<CertificationAdapter.CertificationViewHolder> {

    private List<CertificationItem> certificationList;

    public CertificationAdapter(List<CertificationItem> certificationList) {
        this.certificationList = certificationList;
    }

    @NonNull
    @Override
    public CertificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_certification, parent, false);
        return new CertificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CertificationViewHolder holder, int position) {
        CertificationItem certificationItem = certificationList.get(position);
        holder.title.setText(certificationItem.getTitle());
        holder.description.setText(certificationItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return certificationList.size();
    }

    static class CertificationViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;

        CertificationViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.certification_title);
            description = itemView.findViewById(R.id.certification_description);
        }
    }
}
