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
        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), R.layout.list_alarm, AlarmList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //アラームの ON OFF が押された時
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (view.getId()) {

                    //押されたボタンがswitchボタンの時の処理
                    case R.id.repeat:
                        Toast.makeText(AlarmActivity.this, "スイッチボタンが押されました", Toast.LENGTH_SHORT).show();
                        break;

                    //日付が長押しされた時の処理
                    case R.id.txtArea1:
                        //押されたlistの日付を取得する
                        final String date = ((TextView)view).getText().toString();

                        AlertDialog.Builder alert = new AlertDialog.Builder(AlarmActivity.this);
                        alert.setMessage("本当に削除しますか？");
                        alert.setPositiveButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Noボタンが押された時の処理
                                Toast.makeText(AlarmActivity.this, "削除をキャンセルしました", Toast.LENGTH_LONG).show();
                            }
                        });
                        alert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Yesボタンが押された時の処理
                                    dbm.deleteAlarm(sqlDB, date);
                                    Toast.makeText(AlarmActivity.this, "削除しました", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplication(),AlarmActivity.class);
                                    startActivity(intent);
                            }
                        });
                        alert.show();
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