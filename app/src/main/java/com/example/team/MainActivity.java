package com.example.team;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        setupTabs(tabLayout);

        // 기본으로 첫 번째 탭 프래그먼트 표시
        replaceFragment(new HomeFragment());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selectedFragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        selectedFragment = new HomeFragment();
                        break;
                    case 1:
                        // 자격증 프래그먼트를 추가하세요
                        selectedFragment = new CertificationFragment();
                        break;
                    case 2:
                        // 일정 프래그먼트를 추가하세요
                        selectedFragment = new ScheduleFragment();
                        break;
                }
                if (selectedFragment != null) {
                    replaceFragment(selectedFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupTabs(TabLayout tabLayout) {
        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("홈");
        tab1.setIcon(R.drawable.ic_home);
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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, fragment);
        fragmentTransaction.commit();
    }
}
