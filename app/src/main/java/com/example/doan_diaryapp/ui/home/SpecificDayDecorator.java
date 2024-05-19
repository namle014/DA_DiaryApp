package com.example.doan_diaryapp.ui.home;
import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.example.doan_diaryapp.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

public class SpecificDayDecorator implements DayViewDecorator {

    private final CalendarDay date;

    public SpecificDayDecorator(CalendarDay date) {
        this.date = date;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(10, Color.parseColor("#005138")));
    }
}
