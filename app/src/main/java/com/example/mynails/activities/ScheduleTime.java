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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.RequestOptions;
import com.example.mynails.R;
import com.example.mynails.model.Config;
import com.example.mynails.model.Master;
import com.example.mynails.model.Schedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ScheduleTime extends AppCompatActivity {


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



    }


    public void makeRequest() {
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

        String api_method = "master.get";

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
                        Master master = new Master();
                        master.setId(jsonObject.getInt("id"));
                        master.setName(jsonObject.getString("name"));
                        master.setPhone(jsonObject.getString("phone"));
                        master.setDescription(jsonObject.getString("description"));
                        master.setImage(jsonObject.getString("image"));

                        listMasters.add(master);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                setUpRecyclerView(listMasters);

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


}
