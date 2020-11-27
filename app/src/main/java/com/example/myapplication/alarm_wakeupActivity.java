package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class alarm_wakeupActivity extends AppCompatActivity {

    private SQLiteDatabase sqlDB;
    DBManager dbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();
        setContentView(R.layout.activity_alarm_wakeup);

        startService(new Intent(this, SoundService.class));
        AlertDialog.Builder alert = new AlertDialog.Builder(alarm_wakeupActivity.this);
        alert.setCancelable(false);
        alert.setMessage("アラーム停止");
        alert.setPositiveButton("アラーム停止", new DialogInterface.OnClickListener() {

            Date dateObj = new Date();
            SimpleDateFormat format = new SimpleDateFormat( "HH時mm分" );
            String date = format.format( dateObj );

            public void onClick(DialogInterface dialog, int which) {
                //アラーム停止が押された時の処理
                AlarmManager Alarmmanager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                PendingIntent pendingintent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                if (Alarmmanager != null) {

                    //アラームを止める処理
                    Alarmmanager.cancel(pendingintent);
                    pendingintent.cancel();

                    //アラーム音を止める処理
                    stopService(new Intent(getApplicationContext(), SoundService.class));
                    Toast.makeText(alarm_wakeupActivity.this, "アラームを停止しました", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(getApplication(), AlarmActivity.class);
                    startActivity(intent1);

                    dbm.offAlarm(sqlDB, date);
                }
            }
        });
        alert.show();
    }
}