package com.example.team;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.team.database.ScheduleDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class ScheduleFragment extends Fragment {

    private MaterialCalendarView calendarView;  // 달력 뷰
    private FloatingActionButton addButton;  // 일정 추가 버튼
    private HashMap<String, String> scheduleMap;  // 일정 데이터를 저장할 맵
    private String selectedDate;  // 선택된 날짜
    private TextView showScheduleDate;  // 선택된 날짜를 보여주는 텍스트뷰
    private TextView showScheduler;  // 일정을 보여주는 텍스트뷰

    private ScheduleToday scheduleToday = new ScheduleToday();  // 오늘 날짜를 표시하는 데코레이터
    private ScheduleDAO scheduleDAO;  // 데이터베이스 접근 객체

    public ScheduleFragment() {
        // 기본 생성자
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        calendarView = view.findViewById(R.id.calendar_view);  // 달력 뷰 초기화
        addButton = view.findViewById(R.id.fab_add);  // 추가 버튼 초기화
        showScheduleDate = view.findViewById(R.id.showScheduleDay);  // 날짜 텍스트뷰 초기화
        showScheduler = view.findViewById(R.id.showSchedule);  // 일정 텍스트뷰 초기화
        showScheduler.setGravity(Gravity.CENTER);  // 텍스트를 가운데 정렬로 설정

        scheduleDAO = new ScheduleDAO(getContext());  // 데이터베이스 접근 객체 초기화
        scheduleMap = scheduleDAO.getAllEvents();  // 데이터베이스에서 모든 일정 불러오기

        // 달력 꾸미기
        calendarView.addDecorators(
                new ScheduleSundayDecorator(),  // 일요일 색상 설정
                new ScheduleSaturdayDecorator(),  // 토요일 색상 설정
                scheduleToday  // 오늘 날짜 설정
        );

        // 데이터베이스에서 불러온 일정 날짜들에 대해 데코레이터 설정
        addEventDecorators();

        // 날짜 선택 리스너 설정
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month = date.getMonth();
                int day = date.getDay();

                // 선택된 날짜 포맷 설정
                selectedDate = String.format("%04d-%02d-%02d", year, month + 1, day);
                displayScheduleForDate(selectedDate);  // 선택된 날짜의 일정 표시
            }
        });

        calendarView.setSelectedDate(CalendarDay.today());  // 오늘 날짜를 기본 선택으로 설정

        // 추가 버튼 클릭 리스너 설정
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEventDialog();  // 일정 추가 다이얼로그 표시
            }
        });

        return view;
    }

    // 일정 추가 다이얼로그 표시
    private void showAddEventDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("일정 추가");

        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_add_event, null);
        builder.setView(customLayout);

        builder.setPositiveButton("추가", (dialog, which) -> {
            EditText eventInput = customLayout.findViewById(R.id.edit_text_event);
            String event = eventInput.getText().toString();
            if (!event.isEmpty()) {
                addEventToSchedule(selectedDate, event);  // 일정 추가
                displayScheduleForDate(selectedDate);  // 일정 추가 후 일정 표시
            }
        });

        builder.setNegativeButton("취소", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 일정 추가 또는 수정
    private void addEventToSchedule(String date, String event) {
        scheduleDAO.addOrUpdateEvent(date, event);  // 데이터베이스에 일정 추가 또는 수정
        scheduleMap.put(date, event);  // 메모리 내 일정 맵 업데이트
        displayScheduleForDate(date);  // 일정 추가 후 일정 표시
    }

    // 날짜에 해당하는 일정 표시
    private void displayScheduleForDate(String date) {
        showScheduleDate.setText(date + "\n시험일정");
        String event = scheduleMap.get(date);
        if (event != null) {
            showScheduler.setText(event);

            // 이벤트가 있는 날짜를 달력에 표시
            calendarView.addDecorator(new ScheduleEventDecorator(Color.RED, Collections.singleton(getEventDate(date))));
        } else {
            showScheduler.setText("예정된 일정이 없습니다!");
        }
    }

    // 선택된 날짜를 CalendarDay 객체로 변환
    public CalendarDay getEventDate(String selectedDate) {
        String[] dateParts = selectedDate.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]) - 1;  // CalendarView에서 월은 0부터 시작
        int day = Integer.parseInt(dateParts[2]);
        return CalendarDay.from(year, month, day);
    }

    // 일정 날짜들에 대해 데코레이터 설정
    private void addEventDecorators() {
        HashSet<CalendarDay> dates = new HashSet<>();
        for (String date : scheduleMap.keySet()) {
            dates.add(getEventDate(date));
        }
        calendarView.addDecorator(new ScheduleEventDecorator(Color.RED, dates));
    }
}
