package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class AccountDispActivity extends HeaderActivity {

    private SQLiteDatabase sqlDB;
    DBManager dbm;

    private static final String[] names = {
            "ユーザー名",
            "メールアドレス",
            "パスワード",
            "住所",
    };
    String[] date = {
            "",
            "",
            "",
            "",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_disp);

        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();
        User user = dbm.getUserSetting(sqlDB);

        date[0] = user.getUser_name();
        date[1] = user.getMailAddress();
        date[2] = "●●●●●●●●";
        date[3] = user.getStreet_address();

        ArrayList list = new ArrayList<>();

        // リスト項目とListViewを対応付けるArrayAdapterを用意する
        ArrayAdapter Aadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        // ListViewにArrayAdapterを設定する
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(Aadapter);
        // ListViewのインスタンスを生成
        listView = findViewById(R.id.listView);

        // BaseAdapter を継承したadapterのインスタンスを生成
        // レイアウトファイル list_items.xml を
        // activity_main.xml に inflate するためにKadapterに引数として渡す
        BaseAdapter Kadapter = new UserDispAdapter(this.getApplicationContext(),
                R.layout.user_disp_list, names, date);

        // ListViewにadapterをセット
        listView.setAdapter(Kadapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button account_update = (Button)findViewById(R.id.account_update);
        account_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountDispActivity.this, UserSettingActivity.class);
                startActivity(intent);
            }
        });
        Button password_update = (Button)findViewById(R.id.password_update);
        password_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountDispActivity.this, PasswordSettingActivity.class);
                startActivity(intent);
            }
        });
        Button return_button = (Button)findViewById(R.id.return_button);
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountDispActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });
    }
}