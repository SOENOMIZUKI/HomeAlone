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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AlarmActivity extends HeaderActivity {

    private SQLiteDatabase sqlDB;
    DBManager dbm;
    List<Alarm> AlarmList = new ArrayList<>();
    AlarmManager Alarmmanager;
    PendingIntent pendingintent;
    Date datedata = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //アダプターのリストビューの処理
        setContentView(R.layout.activity_alarm);
        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();

        AlarmList = dbm.selectAlarmList(sqlDB);

        ListView listView = (ListView) findViewById(R.id.lstvAlarm);
        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), R.layout.list_alarm, AlarmList,this);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //アラームの ON OFF が押された時
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (view.getId()) {

                    //switchボタンが押された時の処理
                    case R.id.repeat:
                        Toast.makeText(AlarmActivity.this, "スイッチボタンが押されました", Toast.LENGTH_SHORT).show();
                        Switch sw = (Switch)view;

                        if (String.valueOf(sw.isChecked()) == "true"){
                            dbm.onAlarm(sqlDB, AlarmList.get(position).getTime());

                            String data = AlarmList.get(position).getTime();

                            //アラーム時刻をString型からDate型に変換
                            SimpleDateFormat dateFormat = new SimpleDateFormat("HH時mm分");
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

                            //例:現在12:00、アラームの時間11:00の場合アラームが過去のものになり、アラームがすぐに鳴るので日付を+1する
                            Date date1 = new Date();
                            String data2 = new SimpleDateFormat("HH時mm分").format(date1);
                            SimpleDateFormat sdFormat = new SimpleDateFormat("HH時mm分");
                            Date date3 = null;
                            try {
                                date3 = sdFormat.parse(data2);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (datedata.before(date3)==true){
                                calendar.add(Calendar.DAY_OF_MONTH,1);
                            }

                            //アラームセット
                            Log.e("12345678", calendar.toString());
                            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                            intent.setType(data);
                            pendingintent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            intent.setAction(Intent.ACTION_SEND);
                            Alarmmanager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                            Alarmmanager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingintent);

                        }else {
                            //アラームのOFFが押されたとき
                            String data = AlarmList.get(position).getTime();
                            dbm.offAlarm(sqlDB, AlarmList.get(position).getTime());
                            Alarmmanager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                            intent.setType(data);
                            pendingintent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            pendingintent.cancel();
                            Alarmmanager.cancel(pendingintent);

                        }

                        break;

                    //日付が押された時の処理
                    case R.id.txtArea1:

                        //押されたlistの時間を取得する
                        final String date = ((TextView)view).getText().toString();
                        //押された時間のアラーム情報をSQLiteから取得
                        Alarm alarm = dbm.selectAlarm(sqlDB, date);
                        //平日繰り返しがONの時の処理
                        if (alarm.getRepeat()==1){
                            AlertDialog.Builder alert = new AlertDialog.Builder(AlarmActivity.this);
                            alert.setMessage("平日繰り返しをOFFにしますか？");
                            alert.setPositiveButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //Noボタンが押された時の処理
                                    Toast.makeText(AlarmActivity.this, "キャンセルしました", Toast.LENGTH_LONG).show();
                                }
                            });
                            alert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //Yesボタンが押された時の処理

                                    //DBの平日繰り返しをOFFにする処理
                                    dbm.offRepeat(sqlDB, date);
                                    //登録しているアラームを一度キャンセルする
                                    Alarmmanager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                    Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                                    intent.setType(date);
                                    pendingintent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    pendingintent.cancel();
                                    Alarmmanager.cancel(pendingintent);

                                    //アラームを平日繰り返しなしで登録しなおす
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH時mm分");
                                    try {
                                        datedata = dateFormat.parse(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    // 検索文字（時）より前の文字列取り出し
                                    // 時間の部分を取得
                                    int index1 = date.indexOf("時");
                                    String stringhour = (date.substring(0,index1));
                                    int hour = Integer.parseInt(stringhour);

                                    // 指定した文字（時）より後ろの文字取り出し
                                    // 分の部分を取得
                                    int index2 = date.indexOf("時");
                                    String stringminute1 = (date.substring(index2+1));
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

                                    Log.e("12345678", calendar.toString());

                                    intent.setAction(Intent.ACTION_SEND);
                                    Alarmmanager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                                    Alarmmanager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingintent);

                                    Toast.makeText(AlarmActivity.this, "変更しました", Toast.LENGTH_SHORT).show();

                                    Intent intent1 = new Intent(getApplication(),AlarmActivity.class);
                                    startActivity(intent1);
                                }
                            });
                            alert.show();

                            //平日繰り返しがOFFの時の処理
                        }else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(AlarmActivity.this);
                            alert.setMessage("平日繰り返しをONにしますか？");
                            alert.setPositiveButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //Noボタンが押された時の処理
                                    Toast.makeText(AlarmActivity.this, "キャンセルしました", Toast.LENGTH_LONG).show();
                                }
                            });
                            alert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //Yesボタンが押された時の処理
                                    dbm.onRepeat(sqlDB, date);
                                    //今登録しているアラームを一度キャンセルする
                                    Alarmmanager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                    Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                                    intent.setType(date);
                                    pendingintent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    pendingintent.cancel();
                                    Alarmmanager.cancel(pendingintent);


                                    //アラーム時刻をString型からDate型に変換
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH時mm分");
                                    try {
                                        datedata = dateFormat.parse(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    // 検索文字（時）より前の文字列取り出し
                                    // 時間の部分を取得
                                    int index1 = date.indexOf("時");
                                    String stringhour = (date.substring(0,index1));
                                    int hour = Integer.parseInt(stringhour);

                                    // 指定した文字（時）より後ろの文字取り出し
                                    // 分の部分を取得
                                    int index2 = date.indexOf("時");
                                    String stringminute1 = (date.substring(index2+1));
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

                                    pendingintent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    intent.setAction(Intent.ACTION_SEND);


                                    Alarmmanager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                    //DAY_OF_WEEK, 1で月曜日のアラーム登録
                                    calendar.set(Calendar.DAY_OF_WEEK, 1);
                                    Alarmmanager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingintent);
                                    //DAY_OF_WEEK, 1で火曜日のアラーム登録
                                    calendar.set(Calendar.DAY_OF_WEEK, 2);
                                    Alarmmanager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingintent);
                                    //DAY_OF_WEEK, 1で水曜日のアラーム登録
                                    calendar.set(Calendar.DAY_OF_WEEK, 3);
                                    Alarmmanager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingintent);
                                    //DAY_OF_WEEK, 1で木曜日のアラーム登録
                                    calendar.set(Calendar.DAY_OF_WEEK, 4);
                                    Alarmmanager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingintent);
                                    //DAY_OF_WEEK, 1で金曜日のアラーム登録
                                    calendar.set(Calendar.DAY_OF_WEEK, 5);
                                    Alarmmanager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingintent);

                                    Toast.makeText(AlarmActivity.this, "変更しました", Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(getApplication(),AlarmActivity.class);
                                    startActivity(intent1);
                                }
                            });
                            alert.show();
                        }
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