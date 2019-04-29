package com.example.mynails.activities;

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

        jsonRequest(url);


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
                    }

                }

                setUpRecyclerView(listMasters);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
