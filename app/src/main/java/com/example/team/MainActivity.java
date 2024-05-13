package com.example.team;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ImageButton info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        info = findViewById(R.id.info_list);
        // info_list 버튼 리스너
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingsFragment();

            }
        });
        //SettingsFragment 리스너 생성
        private void showSettingsFragment() {
            // SettingsFragment 생성
            SettingsFragment settingsFragment = new SettingsFragment();

            // FragmentTransaction을 사용하여 프래그먼트 추가
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_layout, settingsFragment)
                    .commit();
        }

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("홈");
        tab1.setIcon(R.drawable.ic_home);  // 아이콘 리소스 설정
        tabLayout.addTab(tab1);

        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setText("자격증");
        tab2.setIcon(R.drawable.ic_launcher_foreground);
        tabLayout.addTab(tab2);

        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setText("일정");
        tab3.setIcon(R.drawable.ic_launcher_background);
        tabLayout.addTab(tab3);
    }
}