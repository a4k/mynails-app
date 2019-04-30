package com.example.mynails.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynails.R;
import com.example.mynails.adapters.RecyclerViewAdapter;
import com.example.mynails.model.Config;
import com.example.mynails.model.Master;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MasterAll extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_INTERNET = 101;

    private Config config;
    private String api_url;
    private String api_method = "master.all";

    private JsonArrayRequest request;
    private RequestQueue requestQueue;

    private List<Master> listMasters;
    private RecyclerView viewMaster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_all);

        config = new Config();

        api_url = config.getApi_url();
        String url = api_url + api_method;

        listMasters = new ArrayList<>();
        viewMaster = findViewById(R.id.mastersList);

        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED && permissionStatus != 0) {
            // Получение списка
            jsonRequest(url);
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

                    api_url = config.getApi_url();
                    String url = api_url + api_method;

                    // Получение списка
                    jsonRequest(url);
                }

        }
    }

    private void jsonRequest(String url) {

        String test = "test";

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


        requestQueue = Volley.newRequestQueue(MasterAll.this);
        requestQueue.add(request);

    }

    private void setUpRecyclerView(List<Master> listMasters) {

        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, listMasters);

        viewMaster.setLayoutManager(new LinearLayoutManager(this));

        viewMaster.setAdapter(myAdapter);

    }
}
