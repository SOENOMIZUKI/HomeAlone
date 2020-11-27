package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AccountDispActivity extends AppCompatActivity {

    private SQLiteDatabase sqlDB;
    DBManager dbm;

    private static final String[] names = {
            "ユーザー名",
            "メールアドレス",
            "パスワード",
            "住所",
            "アバター",
    };
    String[] date = {
            "",
            "",
            "",
            "",
            "",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_disp);

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
}