package com.example.mynails.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import com.example.mynails.R;

public class MasterSchedule extends AppCompatActivity {

    int serviceId = 0;

    CalendarView calendarSchedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_schedule);


        calendarSchedule = (CalendarView) findViewById(R.id.calendarSchedule);


        Intent intent = getIntent();
        serviceId = intent.getIntExtra("id", 0);


        calendarSchedule.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@androidx.annotation.NonNull CalendarView view, int year, int month, int dayOfMonth) {



            }
        });
    }
}
