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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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
    private JsonObjectRequest request;
    private RequestQueue requestQueue;
    private Context mContext;

    // Данные пользователя
    int scheduleId = 0;
    String formNameValue = "";
    String formPhoneValue = "";

    // Элементы страницы
    EditText nameInput;
    EditText phoneInput;
    Button actionOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mContext = this;


        Intent intent = getIntent();
        scheduleId = intent.getIntExtra("id", 0);

        nameInput = findViewById(R.id.form_name);
        phoneInput = findViewById(R.id.form_phone);
        actionOrderBtn = findViewById(R.id.action_create_order);


        actionOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                formNameValue = nameInput.getText().toString();
                formPhoneValue = phoneInput.getText().toString();

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


        String strFilter = "";
        StringBuilder filter = new StringBuilder(strFilter);

        HashMap<String, String> arFilter = new HashMap<>();

        if(scheduleId > 0) {
            arFilter.put("schedule_id", scheduleId + "");
        }
        if(formNameValue.length() > 0) {
            arFilter.put("name", formNameValue);
        }
        if(formPhoneValue.length() > 0) {
            arFilter.put("phone", formPhoneValue);
        }

        for(Map.Entry<String, String> entry : arFilter.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

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

        request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // запрос отправлен

                try {

                    Boolean result = response.getBoolean("result");

                    if(result) {

                        openSuccessPage();

                    } else {

                        showErrorMessage();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    showErrorMessage();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                showErrorMessage();

            }
        });


        requestQueue = Volley.newRequestQueue(OrderActivity.this);
        requestQueue.add(request);

    }

    private void showErrorMessage() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Возникла ошибка",
                Toast.LENGTH_SHORT);
        toast.show();
    }

    private void openSuccessPage() {

        // Переход на страницу успеха
        Intent masterDetailPage = new Intent(OrderActivity.this, OrderSuccess.class);

        startActivity(masterDetailPage);

    }


}
