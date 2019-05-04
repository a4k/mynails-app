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
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynails.R;
import com.example.mynails.model.Config;
import com.example.mynails.model.Schedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_INTERNET = 101;

    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private Context mContext;

    int scheduleId = 0;

    Button actionOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mContext = this;


        Intent intent = getIntent();
        scheduleId = intent.getIntExtra("id", 0);

        actionOrderBtn = findViewById(R.id.action_create_order);


        actionOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeRequest();

            }
        });

    }

    private void makeRequest() {
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

        String api_method = "schedule.choose";

        return api_url + api_method + "&schedule_id=" + scheduleId;

    }


    // отправка запроса
    private void jsonRequest(String url) {

        request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                // запрос отправлен

                // todo перенаправить на успешную страницу

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });


        requestQueue = Volley.newRequestQueue(OrderActivity.this);
        requestQueue.add(request);

    }


}
