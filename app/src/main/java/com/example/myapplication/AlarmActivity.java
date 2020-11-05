package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AlarmActivity extends AppCompatActivity {

    private SQLiteDatabase sqlDB;
    DBManager dbm;

    private static final String[] countries = {
            "America",
            "Japan",
            "China",
            "Korea",
            "British",
            "German"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        ListView listView = (ListView) findViewById(R.id.lstvAlarm);
        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), R.layout.list_alarm, countries);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (view.getId()) {
                    case R.id.switch2:
                        Toast.makeText(AlarmActivity.this, countries[position] + "の編集ボタンが押されました", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sqlDB.close();
    }
}