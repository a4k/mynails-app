package com.example.mynails.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mynails.R;

public class MainActivity extends AppCompatActivity {

    Button chooseMasterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chooseMasterBtn = (Button) findViewById(R.id.chooseMasterBtn);


        chooseMasterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на страницу всех мастеров
                Intent masterAllPage = new Intent(MainActivity.this, MasterAll.class);

                startActivity(masterAllPage);

            }
        });
    }

}
