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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.RequestOptions;
import com.example.mynails.R;
import com.example.mynails.adapters.MasterViewAdapter;
import com.example.mynails.adapters.ScheduleTimeViewAdapter;
import com.example.mynails.model.Config;
import com.example.mynails.model.Master;
import com.example.mynails.model.Schedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleTime extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_INTERNET = 101;

    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private Context mContext;

    private List<Schedule> listSchedule;
    private RecyclerView viewSchedule;


    int id = 0;
    int masterId = 0;
    int serviceId = 0;
    int reqDay = 0;
    int reqMonth = 0;
    int reqYear = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_time);

        this.mContext = this;


        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        serviceId = intent.getIntExtra("service_id", 0);
        masterId = intent.getIntExtra("master_id", 0);
        reqYear = intent.getIntExtra("year", 0);
        reqMonth = intent.getIntExtra("month", 0);
        reqDay = intent.getIntExtra("day", 0);


        listSchedule = new ArrayList<>();
        viewSchedule = findViewById(R.id.scheduleList);


        int permissionStatus = ContextCompat.checkSelfPermission(mContext, Manifest.permission.INTERNET);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED && permissionStatus != 0) {
            // Получение списка
            jsonRequest(getApiUrl());
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET},
                    REQUEST_CODE_PERMISSION_INTERNET);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_INTERNET:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted

                    // Получение списка
                    jsonRequest(getApiUrl());
                }

        }
    }

    // адрес апи
    public String getApiUrl() {

        Config config = new Config();
        String api_url = config.getApi_url();

        String api_method = "schedule.filter";

        String strFilter = "";
        StringBuilder filter = new StringBuilder(strFilter);

        HashMap<String, Integer> arFilter = new HashMap<>();

        if(masterId > 0) {
            arFilter.put("master_id", masterId);
        }
        if(serviceId > 0) {
            arFilter.put("service_id", serviceId);
        }
        if(reqDay > 0) {
            arFilter.put("day", reqDay);
        }
        if(reqMonth > 0) {
            arFilter.put("month", reqMonth);
        }
        if(reqYear > 0) {
            arFilter.put("year", reqYear);
        }

        for(Map.Entry<String, Integer> entry : arFilter.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            if(filter.length() > 1) {
                filter.append("&" + key + "=" + value);
            } else {
                filter.append(key + "=" + value);
            }

        }

        strFilter = filter.toString();


        return api_url + api_method + "&" + strFilter;

    }


    // отправка запроса
    private void jsonRequest(String url) {

        request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);

                        Schedule schedule = new Schedule();
                        schedule.setId(jsonObject.getInt("id"));
                        schedule.setUser_id(jsonObject.getInt("user_id"));
                        schedule.setMaster_id(jsonObject.getInt("master_id"));
                        schedule.setService_id(jsonObject.getInt("service_id"));
                        schedule.setYear(jsonObject.getInt("year"));
                        schedule.setMaster_id(jsonObject.getInt("month"));
                        schedule.setDay(jsonObject.getInt("day"));
                        schedule.setHour(jsonObject.getInt("hour"));
                        schedule.setMinute(jsonObject.getInt("minute"));
                        schedule.setType(jsonObject.getInt("type"));

                        listSchedule.add(schedule);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                setUpRecyclerView(listSchedule);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });


        requestQueue = Volley.newRequestQueue(ScheduleTime.this);
        requestQueue.add(request);

    }

    private void setUpRecyclerView(List<Schedule> listSchedule) {

        ScheduleTimeViewAdapter.OnItemClickListener listener = new ScheduleTime.BindListener();

        ScheduleTimeViewAdapter myAdapter = new ScheduleTimeViewAdapter(this, listSchedule, listener);

        viewSchedule.setLayoutManager(new LinearLayoutManager(this));

        viewSchedule.setAdapter(myAdapter);

    }


    public class BindListener implements ScheduleTimeViewAdapter.OnItemClickListener {

        @Override
        public void onItemClick(Schedule item) {

            int id = item.getId();

            // Переход на страницу информации о мастере
            Intent masterDetailPage = new Intent(ScheduleTime.this, MasterDetail.class);
            masterDetailPage.putExtra("id", id);


            startActivity(masterDetailPage);


        }
    }



}
