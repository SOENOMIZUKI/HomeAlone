package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    //戻るボタン押下時の処理
    public void onClickSendAlarm(View v) {
        Intent intent = new Intent(getApplication(),AlarmActivity.class);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();

        Button buttonInsert = (Button) findViewById(R.id.buttonInsert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int repeat;
                Date datedata = null;

                //平日繰り返しのスイッチボタンがONであればrepeatを１に
                if (AlarmSettingActivity.this.repeat =true){
                    repeat = 1;
                }else{
                    repeat = 2;
                }

                Button time = findViewById(R.id.time);

                //アラーム時刻をString型で取得
                String data = time.getText().toString();

                //アラーム時刻をString型からDate型に変換
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM時dd分");
                try {
                    datedata = dateFormat.parse(data);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //アラーム時刻をDate型からCalendar型に変換
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(datedata);

                //アラームをSQLiteに登録
                if (data != null) dbm.insertAlarm(sqlDB, data,repeat);

                //アラームセット
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                Context ct = getApplication();
                PendingIntent pendingintent = PendingIntent.getService(ct, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager Alarmmanager;
                Alarmmanager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Alarmmanager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingintent);

                //アラーム登録完了のトースト表示
                Toast.makeText(AlarmSettingActivity.this, "アラームを登録しました", Toast.LENGTH_SHORT).show();

                //アラーム一覧画面へ遷移
                Intent intent1 = new Intent(getApplication(),AlarmActivity.class);
                startActivity(intent1);
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