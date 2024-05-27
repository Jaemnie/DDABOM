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

    private MaterialCalendarView calendarView;
    private FloatingActionButton addButton;
    private HashMap<String, ArrayList<String>> scheduleMap;
    private String selectedDate;
    private TextView showScheduleDate;
    private TextView showScheduler;

    private ScheduleToday scheduleToday = new ScheduleToday();

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        calendarView = view.findViewById(R.id.calendar_view);
        addButton = view.findViewById(R.id.fab_add);
        showScheduleDate = (TextView) view.findViewById(R.id.showScheduleDay);
        showScheduler = (TextView) view.findViewById(R.id.showSchedule);
        scheduleMap = new HashMap<>();
        // 초기 선택 날짜 설정
        /*selectedDate = getCurrentDate();*/

        // 달력꾸미기
        calendarView.addDecorators( 

                // 토, 일요일 색깔
                new ScheduleSundayDecorator()
                ,new ScheduleSaturdayDecorator()
                // 오늘 날짜
                ,scheduleToday
        );

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month = date.getMonth();
                int day = date.getDay();

                selectedDate = String.format("%04d-%02d-%02d", year, month+1, day);
                displayScheduleForDate(selectedDate);
            }
        });

        calendarView.setSelectedDate(CalendarDay.today());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEventDialog();
            }
        });

        return view;
    }

    /*private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date(calendarView.getDate()));
    }*/

    private void showAddEventDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("일정 추가");

        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_add_event, null);
        builder.setView(customLayout);

        builder.setPositiveButton("추가", (dialog, which) -> {
            EditText eventInput = customLayout.findViewById(R.id.edit_text_event);
            String event = eventInput.getText().toString();
            if (!event.isEmpty()) {
                addEventToSchedule(selectedDate, event);
                Toast.makeText(getContext(), "일정 추가: " + event, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "일정을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("취소", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addEventToSchedule(String date, String event) {
        ArrayList<String> events = scheduleMap.get(date);
        if (events == null) {
            events = new ArrayList<>();
            scheduleMap.put(date, events);
        }
        events.add(event);
        displayScheduleForDate(date);


    }

    private void displayScheduleForDate(String date) {
        showScheduleDate.setText(date+"\n시험일정");
        ArrayList<String> events = scheduleMap.get(date);
        if (events != null) {
            StringBuilder eventsDisplay = new StringBuilder();
            for (String event : events) {
                eventsDisplay.append(event).append("\n");
            }
            Toast.makeText(getContext(), "일정 (" + date + "):\n" + eventsDisplay.toString(), Toast.LENGTH_LONG).show();
            showScheduler.setText(eventsDisplay.toString());
            // 해당날짜  표시
            showScheduler.setText(getEventDate(selectedDate).toString());
            Toast.makeText(getContext(), "현재시간 (" + CalendarDay.today() + "):\n" , Toast.LENGTH_LONG).show();

            calendarView.addDecorator(new ScheduleEventDecorator(Color.RED, Collections.singleton(getEventDate(selectedDate))));
        } else {
            Toast.makeText(getContext(), "일정 없음 (" + date + ")", Toast.LENGTH_SHORT).show();
            showScheduler.setText("예정된 일정이 없습니다!");
        }
    }
    // 이벤트 날짜를 CalendarDay로 바꾸기
    public CalendarDay getEventDate (String selectedDate) {
        String[] dateParts = selectedDate.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1])-1; // Custom Calendar는 자체적으로 월을 +1 안해도 자동적으로 올려주기 때문에 여기선 -1을 해준다
        int day = Integer.parseInt(dateParts[2]);
        CalendarDay selectedDay = CalendarDay.from(year, month, day);
        return selectedDay;
    }

}


