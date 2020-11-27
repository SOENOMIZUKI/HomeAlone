package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
    AlarmManager Alarmmanager;
    PendingIntent pendingintent;



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

                // 検索文字（時）より前の文字列取り出し
                // 時間の部分を取得
                int index1 = data.indexOf("時");
                String stringhour = (data.substring(0,index1));
                int hour = Integer.parseInt(stringhour);

                // 指定した文字（時）より後ろの文字取り出し
                // 分の部分を取得
                int index2 = data.indexOf("時");
                String stringminute1 = (data.substring(index2+1));
                //　例:16分の場合,分を取り除き16だけにしてstringminute2に保存する処理
                String stringminute2 = "";
                if (stringminute1.contains("分")) {
                    stringminute2 = (stringminute1.substring(0,2));
                }
                //　例:6分など1桁の場合,分が取り除けていないので取り除く処理
                if (stringminute2.length()==2 && stringminute2.contains("分")){
                    stringminute2 = (stringminute1.substring(0,1));
                }
                int minute = Integer.parseInt(stringminute2);

                //アラーム時刻をDate型からCalendar型に変換
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                if(calendar.getTimeInMillis() < System.currentTimeMillis()){
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                }
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                //アラームをSQLiteに登録
                if (data != null) dbm.insertAlarm(sqlDB, data,repeat);

                //アラームセット
                calendar.add(Calendar.SECOND,10);
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                pendingintent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
                intent.setAction(Intent.ACTION_SEND);
                Context ct = getApplication();
                Alarmmanager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Alarmmanager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingintent);

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