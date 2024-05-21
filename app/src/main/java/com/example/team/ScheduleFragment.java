package com.example.team;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ScheduleFragment extends Fragment {

    private CalendarView calendarView;
    private FloatingActionButton addButton;
    private HashMap<String, ArrayList<String>> scheduleMap;
    private String selectedDate;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        calendarView = view.findViewById(R.id.calendar_view);
        addButton = view.findViewById(R.id.fab_add);
        scheduleMap = new HashMap<>();

        // 초기 선택 날짜 설정
        selectedDate = getCurrentDate();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                displayScheduleForDate(selectedDate);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEventDialog();
            }
        });

        return view;
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date(calendarView.getDate()));
    }

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
        ArrayList<String> events = scheduleMap.get(date);
        if (events != null) {
            StringBuilder eventsDisplay = new StringBuilder();
            for (String event : events) {
                eventsDisplay.append(event).append("\n");
            }
            Toast.makeText(getContext(), "일정 (" + date + "):\n" + eventsDisplay.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "일정 없음 (" + date + ")", Toast.LENGTH_SHORT).show();
        }
    }
}
