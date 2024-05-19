package com.example.team;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ImageButton info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // 사이드바 설정을 위한 id 설정
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.nav_view);

        info = findViewById(R.id.info_list);
        // info_list 버튼 리스너
         info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //일단 info_list를 누르면 카테고리가 나오게 만들어봄
                //showSettingsFragment();
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

         nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                 int id = menuItem.getItemId();

                 if (id == R.id.category1){
                    // 프래그먼트로? 인텐트로?
                    showDetailFragment("Category1");
                 } else if (id == R.id.category2) {
                     showDetailFragment("Category2");
                 } else if (id == R.id.category3) {
                     showDetailFragment("Category3");
                 }
                 drawerLayout.closeDrawer(GravityCompat.START);
                 return true;
             }
         });
         /**
        //SettingsFragment 리스너 생성
         private void showSettingsFragment () {
            // SettingsFragment 생성
            SettingsFragment settingsFragment = new SettingsFragment();

            // FragmentTransaction을 사용하여 프래그먼트 추가
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_layout, settingsFragment)
                    .commit();
        }
          **/

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("홈");
        tab1.setIcon(R.drawable.ic_home);  // 아이콘 리소스 설정
        tabLayout.addTab(tab1);

        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setText("자격증");
        tab2.setIcon(R.drawable.ic_cert);
        tabLayout.addTab(tab2);

        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setText("일정");
        tab3.setIcon(R.drawable.ic_sche);
        tabLayout.addTab(tab3);

        //tabLayout 동작

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position =tab.getPosition(); //탭 위치로 구분
                switch (position) {
                    case 0 :
                        //showFragment(new HomeFragment());
                        break;

                    case 1:
                        //showFragment(new CertificationFragment());
                        break;

                    case 2:
                        showFragment(new CalendarFragment());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    
    //카테고리 눌렀을 때 상세보기 나오게
    private void showDetailFragment (String category) {
        detailFragment fragment = detailFragment.newInstance(category, "0");
        // FragmentTransaction을 사용하여 프래그먼트 추가
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.drawer_layout, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout, fragment)
                .commit();
    }
}