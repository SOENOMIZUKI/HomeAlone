package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CalendarComfirmActivity extends HeaderActivity {
    private SQLiteDatabase sqlDB;
    DBManager dbm;
    String setDate;
    String rowNum;
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
        planList = dbm.getPlan(sqlDB,date);

        ListView listView = (ListView) findViewById(R.id.PlanList);
        PlanLisViewAdapter adapter = new PlanLisViewAdapter(getApplicationContext(), R.layout.row_item, planList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (view.getId()) {
                    case R.id.delete:
                        Plan num1 = planList.get(position);
                        rowNum = num1.getCalnedar_id();
                        dbm.delPlan(sqlDB,rowNum);
                        Intent kousin = new Intent(getApplication(),CalendarComfirmActivity.class);
                        kousin.putExtra("Date",setDate);
                        startActivity(kousin);
                        break;
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


        ImageButton puls = (ImageButton)findViewById(R.id.pulsBtn);
        puls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = 1;
                if (size == planList.size()) {
                    Toast.makeText(getApplicationContext(), "登録できる予定の数は一つです。", Toast.LENGTH_SHORT).show();

                } else{
                    Intent intent2 = new Intent(CalendarComfirmActivity.this,CalendarAddActivity.class);
                    intent2.putExtra("Date",setDate);
                    startActivity(intent2);
                }

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