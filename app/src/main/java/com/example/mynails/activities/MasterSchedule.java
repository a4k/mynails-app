package com.example.mynails.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.CalendarView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mynails.R;
import com.example.mynails.model.Config;
import com.example.mynails.model.Master;
import com.example.mynails.model.Schedule;
import com.example.mynails.model.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterSchedule extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_INTERNET = 101;

    private JsonObjectRequest request;
    private RequestQueue requestQueue;
    private RequestOptions req_options;

    private Context mContext;

    int reqDay = 0;
    int reqMonth = 0;
    int reqYear = 0;

    int id = 0;
    int masterId = 0;
    int serviceId = 0;

    CalendarView calendarSchedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_schedule);

        this.mContext = this;

        calendarSchedule = (CalendarView) findViewById(R.id.calendarSchedule);


        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        serviceId = intent.getIntExtra("service_id", 0);
        masterId = intent.getIntExtra("master_id", 0);


        calendarSchedule.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                reqDay = dayOfMonth;
                reqMonth = month;
                reqYear = year;


                // Переход на страницу информации о мастере
                Intent scheduleTime = new Intent(MasterSchedule.this, ScheduleTime.class);
                scheduleTime.putExtra("id", id);
                scheduleTime.putExtra("master_id", masterId);
                scheduleTime.putExtra("service_id", serviceId);
                scheduleTime.putExtra("year", year);
                scheduleTime.putExtra("month", month);
                scheduleTime.putExtra("day", dayOfMonth);

                startActivity(scheduleTime);


            }
        });
    }




}
