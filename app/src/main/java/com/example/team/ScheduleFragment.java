package com.example.team;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ScheduleFragment extends Fragment {

    private MaterialCalendarView calendarView;  // 달력 뷰
    private FloatingActionButton addButton;  // 일정 추가 버튼
    private HashMap<String, ArrayList<String>> scheduleMap;  // 일정 데이터를 저장할 맵
    private String selectedDate;  // 선택된 날짜
    private TextView showScheduleDate;  // 선택된 날짜를 보여주는 텍스트뷰
    private TextView showScheduler;  // 일정을 보여주는 텍스트뷰

    private ScheduleToday scheduleToday = new ScheduleToday();  // 오늘 날짜를 표시하는 데코레이터

    public ScheduleFragment() {
        // 기본 생성자
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        calendarView = view.findViewById(R.id.calendar_view);  // 달력 뷰 초기화
        addButton = view.findViewById(R.id.fab_add);  // 추가 버튼 초기화
        showScheduleDate = (TextView) view.findViewById(R.id.showScheduleDay);  // 날짜 텍스트뷰 초기화
        showScheduler = (TextView) view.findViewById(R.id.showSchedule);  // 일정 텍스트뷰 초기화
        scheduleMap = new HashMap<>();  // 일정 맵 초기화

        // 달력 꾸미기
        calendarView.addDecorators(
                new ScheduleSundayDecorator(),  // 일요일 색상 설정
                new ScheduleSaturdayDecorator(),  // 토요일 색상 설정
                scheduleToday  // 오늘 날짜 설정
        );

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
                Toast.makeText(getContext(), "일정 추가: " + event, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "일정을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("취소", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 일정 추가
    private void addEventToSchedule(String date, String event) {
        ArrayList<String> events = scheduleMap.get(date);
        if (events == null) {
            events = new ArrayList<>();
            scheduleMap.put(date, events);
        }
        events.add(event);
        displayScheduleForDate(date);  // 일정 추가 후 일정 표시
    }

    // 날짜에 해당하는 일정 표시
    private void displayScheduleForDate(String date) {
        showScheduleDate.setText(date + "\n시험일정");
        ArrayList<String> events = scheduleMap.get(date);
        if (events != null) {
            StringBuilder eventsDisplay = new StringBuilder();
            for (String event : events) {
                eventsDisplay.append(event).append("\n");
            }
            Toast.makeText(getContext(), "일정 (" + date + "):\n" + eventsDisplay.toString(), Toast.LENGTH_LONG).show();
            showScheduler.setText(eventsDisplay.toString());
            // 해당 날짜 표시
            showScheduler.setText(getEventDate(selectedDate).toString());
            Toast.makeText(getContext(), "현재시간 (" + CalendarDay.today() + "):\n", Toast.LENGTH_LONG).show();

            // 이벤트가 있는 날짜를 달력에 표시
            calendarView.addDecorator(new ScheduleEventDecorator(Color.RED, Collections.singleton(getEventDate(selectedDate))));
        } else {
            Toast.makeText(getContext(), "일정 없음 (" + date + ")", Toast.LENGTH_SHORT).show();
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
}
