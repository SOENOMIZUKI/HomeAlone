package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
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
    boolean repeat = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);

        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();

        Switch s = (Switch) findViewById(R.id.repeat);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    repeat = true;
                } else {
                    repeat = false;
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();

        Button buttonInsert = (Button) findViewById(R.id.buttonInsert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int repeat;

                //平日繰り返しのスイッチボタンがONであればrepeatを１に
                if (AlarmSettingActivity.this.repeat =true){
                    repeat = 1;
                }else{
                    repeat = 2;
                }

                Button time = findViewById(R.id.time);
                String data = time.getText().toString();
                if (data != null) dbm.insertAlarm(sqlDB, data,repeat);
                Toast.makeText(AlarmSettingActivity.this, "アラームを登録しました", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(),AlarmActivity.class);
                startActivity(intent);
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