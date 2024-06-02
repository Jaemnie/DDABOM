package com.example.team;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.WeakHashMap;

public class CertificationDialog extends Dialog {
    private String title;
    private String fee;
    private String qualification;
    private Button cancelBtn;
    private String host;
    private String url = "https://janet.co.kr/jnLics/licenseInfo?ld_id=";

    // 자격증 상세내용 출력
    // 넣는거에 추가하고 여기에도 추가
    public CertificationDialog(@NonNull Context context, String title, String fee, String qualification, String host) {
        super(context);
        this.title=title;
        this.fee=fee;
        this.qualification=qualification;
        this.host=host;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_show_detailcertification);

        TextView cert_title = findViewById(R.id.certification_title);
        TextView cert_fee = findViewById(R.id.certification_fee);
        TextView cert_quali = findViewById(R.id.certification_qualifications);
        TextView cert_host = findViewById(R.id.certification_host);
        WebView wv = findViewById(R.id.webview);

        // 여기에 해당 내용 출력
        cert_title.setText(title);
        cert_fee.setText("응시비:");
        cert_quali.setText("자격요건:");
        cert_host.setText("시행기관:"+host);
        // 자격증 상세정보 출력을 위한 웹뷰
        wv.loadUrl(url+"88");

        cancelBtn=(Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
