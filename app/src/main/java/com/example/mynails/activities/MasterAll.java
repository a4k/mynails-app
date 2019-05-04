package com.example.mynails.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynails.R;
import com.example.mynails.adapters.MasterViewAdapter;
import com.example.mynails.model.Config;
import com.example.mynails.model.Master;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MasterAll extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_INTERNET = 101;

    private JsonArrayRequest request;
    private RequestQueue requestQueue;

    private List<Master> listMasters;
    private RecyclerView viewMaster;

    LinearLayout notFindLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_all);


        listMasters = new ArrayList<>();
        viewMaster = findViewById(R.id.mastersList);

        notFindLayout = findViewById(R.id.not_find);

        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED && permissionStatus != 0) {
            // Получение списка
            jsonRequest(getApiUrl());
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET},
                    REQUEST_CODE_PERMISSION_INTERNET);
        }


    }

    public String getApiUrl() {

        Config config = new Config();
        String api_url = config.getApi_url();
        String api_method = "master.all";

        return api_url + api_method;

    }

    private void showNotFind() {
        notFindLayout.setVisibility(View.VISIBLE);
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
                        showNotFind();

                    }

                }

                setUpRecyclerView(listMasters);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                showNotFind();

            }
        });


        requestQueue = Volley.newRequestQueue(MasterAll.this);
        requestQueue.add(request);

    }

    private void setUpRecyclerView(List<Master> listMasters) {

        MasterViewAdapter.OnItemClickListener listener = new BindListener();

        MasterViewAdapter myAdapter = new MasterViewAdapter(this, listMasters, listener);

        viewMaster.setLayoutManager(new LinearLayoutManager(this));

        viewMaster.setAdapter(myAdapter);

    }

    public class BindListener implements MasterViewAdapter.OnItemClickListener {

        @Override
        public void onItemClick(Master item) {

            int id = item.getId();

            // Переход на страницу информации о мастере
            Intent masterDetailPage = new Intent(MasterAll.this, MasterDetail.class);
            masterDetailPage.putExtra("id", id);


            startActivity(masterDetailPage);


        }
    }
}
