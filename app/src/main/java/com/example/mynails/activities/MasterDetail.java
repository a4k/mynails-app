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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mynails.R;
import com.example.mynails.adapters.MasterViewAdapter;
import com.example.mynails.adapters.ServiceViewAdapter;
import com.example.mynails.model.Config;
import com.example.mynails.model.Master;
import com.example.mynails.model.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MasterDetail extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_INTERNET = 101;

    private JsonObjectRequest request;
    private RequestQueue requestQueue;
    private RequestOptions req_options;

    private List<Service> listServices;
    private RecyclerView viewService;

    private Context mContext;

    TextView title;
    TextView description;
    ImageView item_image;

    int masterId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        this.mContext = this;

        Intent intent = getIntent();
        masterId = intent.getIntExtra("id", 0);

        // Элементы страницы
        title = (TextView) findViewById(R.id.item_detail_title);
        description = (TextView) findViewById(R.id.item_detail_text);
        item_image = (ImageView) findViewById(R.id.item_detail_image);

        listServices = new ArrayList<>();
        viewService = findViewById(R.id.serviceList);


        // Описание запросов для Glide
        req_options = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

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

        return api_url + api_method + "&master_id=" + masterId;

    }

    // адрес хоста
    public String getApiHost() {

        Config config = new Config();
        return config.getApi_host();

    }

    // отправка запроса
    private void jsonRequest(String url) {

        final String api_host = getApiHost();

        request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    Master master = new Master();
                    master.setId(response.getInt("id"));
                    master.setName(response.getString("name"));
                    master.setPhone(response.getString("phone"));
                    master.setDescription(response.getString("description"));
                    master.setImage(response.getString("image"));


                    JSONObject jsonObject = null;
                    JSONArray services = response.getJSONArray("services");

                    for (int i = 0; i < services.length(); i++) {

                        try {
                            jsonObject = services.getJSONObject(i);
                            Service service = new Service();
                            service.setId(jsonObject.getInt("id"));
                            service.setName(jsonObject.getString("name"));
                            service.setPrice(jsonObject.getInt("price"));
                            service.setTime(jsonObject.getInt("time"));

                            service.setMaster_id(jsonObject.getInt("master_id"));
                            service.setService_id(jsonObject.getInt("service_id"));

                            listServices.add(service);

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    setUpRecyclerView(listServices);

                    title.setText(master.getName());
                    description.setText(master.getDescription());

                    // Подгрузка изображения
                    Glide.with(mContext)
                            .load(api_host + master.getImage()).apply(req_options).into(item_image);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });


        requestQueue = Volley.newRequestQueue(MasterDetail.this);
        requestQueue.add(request);

    }


    private void setUpRecyclerView(List<Service> list) {

        ServiceViewAdapter.OnItemClickListener listener = new MasterDetail.BindListener();

        ServiceViewAdapter myAdapter = new ServiceViewAdapter(this, list, listener);

        viewService.setLayoutManager(new LinearLayoutManager(this));

        viewService.setAdapter(myAdapter);

    }


    public class BindListener implements ServiceViewAdapter.OnItemClickListener {

        @Override
        public void onItemClick(Service item) {

            int id = item.getId();
            int master_id = item.getMaster_id();
            int service_id = item.getService_id();

            // Переход на страницу информации о мастере
            Intent masterSchedule = new Intent(MasterDetail.this, MasterSchedule.class);
            masterSchedule.putExtra("id", id);
            masterSchedule.putExtra("master_id", master_id);
            masterSchedule.putExtra("service_id", service_id);

            startActivity(masterSchedule);

        }
    }

}
