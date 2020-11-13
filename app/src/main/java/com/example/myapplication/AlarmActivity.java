package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
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
                        }else {
                            dbm.offAlarm(sqlDB, AlarmList.get(position).getTime());
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
                                    dbm.offRepeat(sqlDB, date);
                                    Toast.makeText(AlarmActivity.this, "変更しました", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplication(),AlarmActivity.class);
                                    startActivity(intent);
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
                                    Toast.makeText(AlarmActivity.this, "変更しました", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplication(),AlarmActivity.class);
                                    startActivity(intent);
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