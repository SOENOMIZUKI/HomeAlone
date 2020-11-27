package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarComfirmActivity extends AppCompatActivity {
    private SQLiteDatabase sqlDB;
    DBManager dbm;
    String setDate;
    List<Plan> planList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_comfirm);

        Intent intent = getIntent();
        setDate = intent.getStringExtra("Date");
        TextView text = (TextView)findViewById(R.id.dateView);
        text.setText(setDate + "の予定");

        dbm = new DBManager(this);
        String date = setDate;
        sqlDB = dbm.getWritableDatabase();
        planList = dbm.getplan(sqlDB,date);
    }

    @Override
    protected void onResume() {
        super.onResume();


        ImageButton puls = (ImageButton)findViewById(R.id.pulsBtn);
        puls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(CalendarComfirmActivity.this,CalendarAddActivity.class);
                intent2.putExtra("Date",setDate);
                startActivity(intent2);
            }
        });

        ImageButton back = (ImageButton)findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(CalendarComfirmActivity.this, WeatherActivity.class);
                startActivity(intent3);
            }
        });
    }
}