package com.example.team;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class ScheduleEventDecorator implements DayViewDecorator {
    // 이벤트 날짜 표시
    private final int color;
    private final HashSet<CalendarDay> dates;
    
    public ScheduleEventDecorator(int color, Collection<CalendarDay> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }
    
    // 설정 할 날짜
    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, color));
    }
}
