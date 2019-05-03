package com.example.mynails.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mynails.R;

public class MasterDetail extends AppCompatActivity {

    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        title = (TextView) findViewById(R.id.item_detail_title);

        title.setText(id);


    }
}
