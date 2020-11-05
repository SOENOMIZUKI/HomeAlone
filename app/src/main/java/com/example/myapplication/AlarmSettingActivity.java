package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmSettingActivity extends AppCompatActivity {
    private SQLiteDatabase sqlDB;
    DBManager dbm;
    boolean switch1 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);
        Switch s = (Switch) findViewById(R.id.switch2);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch1 = true;
                } else {
                    switch1 = false;
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();

        Button buttonInsert = (Button) findViewById(R.id.buttonInsert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button data = findViewById(R.id.time);
                if (data != null) dbm.insertAlarm(sqlDB, data,switch1);
                Toast.makeText(AlarmSettingActivity.this, "アラームを登録しました", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void nya(View v){

        final Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Button data = findViewById(R.id.time);
                        data.setText(hourOfDay+"時"+minute+"分");

                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }
}