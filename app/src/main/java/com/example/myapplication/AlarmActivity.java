package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AlarmActivity extends AppCompatActivity {

    private SQLiteDatabase sqlDB;
    DBManager dbm;
    List<Alarm> AlarmList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();

        AlarmList = dbm.selectAlarmList(sqlDB);

        ListView listView = (ListView) findViewById(R.id.lstvAlarm);
        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), R.layout.list_alarm, AlarmList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (view.getId()) {
                    case R.id.repeat:
                        Toast.makeText(AlarmActivity.this,"スイッチボタンが押されました", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    //アラーム追加ボタン押下時、画面遷移する
        public void onClickSendInsertAlarm(View v) {
            Intent intent = new Intent(getApplication(),AlarmSettingActivity.class);
            startActivity(intent);
        }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sqlDB.close();
    }
}