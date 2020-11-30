package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;


public class Ledgerinput extends AppCompatActivity {

    int post = 0;
    private SQLiteDatabase sqlDB;
    DBManager dbm;
    boolean repeat = true;
    Date month;
    int ans;
        private static final String[] names = {
                "家賃",
                "食費",
                "趣味",
                "水道代",
                "光熱費",
                "通信費",
                "その他",
        };
        private static final String[] price = {
                "00",
                "00",
                "00",
                "00",
                "00",
                "00",
                "00",
        };

            // drawableに画像を入れる、R.id.xxx はint型
            private static final int[] photos = {
                    R.drawable.yatinn,
                    R.drawable.sixyokuhi,
                    R.drawable.sixyumi,
                    R.drawable.suidoudai,
                    R.drawable.kounetuhi,
                    R.drawable.tuusinhi,
                    R.drawable.sonota,
            };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ledgerinput);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        month = (Date) intent.getSerializableExtra("Month");

        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();

        //押された時間のアラーム情報をSQLiteから取得
        Kakeibo kakeibo = dbm.selectMoneybook(sqlDB, month.getMonth());
        //平日繰り返しがONの時の処理
        price[0] = kakeibo.getRent().toString();
        price[1] = kakeibo.getFood_expenses().toString();
        price[2] = kakeibo.getWater_costs().toString();
        price[3] = kakeibo.getUtility_costs().toString();
        price[4] = kakeibo.getCommunication_costs().toString();
        price[5] = kakeibo.getHobby().toString();
        price[6] = kakeibo.getOther().toString();

        Log.i("1111111",price.toString());
        ListView listView = findViewById(R.id.listView);


        // BaseAdapter を継承したadapterのインスタンスを生成
        // レイアウトファイル list_items.xml を
        // activity_main.xml に inflate するためにKadapterに引数として渡す
        BaseAdapter Kadapter = new kakeiboAdapter(this.getApplicationContext(),
                R.layout.kakeibo_list, names, price, photos);

        // ListViewにadapterをセット
        listView.setAdapter(Kadapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                post = position;
            }
        });
        //入力チェック
        final EditText editText = findViewById(R.id.editTextTextPersonName3);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean flag) {
                if(!flag){
                    String str = editText.getText().toString().trim();
                    if(str.matches("")){
                        Toast toast = Toast.makeText(Ledgerinput.this,"入力されていません",Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }

        });


        //戻るボタン遷移
        Button backButton = findViewById(R.id.back_bottom);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //変更ボタン遷移
        Button inputfinish = findViewById(R.id.inputfinish);
        inputfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText price = (EditText) findViewById(R.id.editTextTextPersonName3);
                //増減値の取得
                String input_price = price.getText().toString();
                //リスト に紐づいているアダプターを取得
                ListView listView = findViewById(R.id.listView);
                int pint = Integer.parseInt(input_price);
                kakeiboAdapter adapter = (kakeiboAdapter) listView.getAdapter();
                //アダプターから選択されているpriceを取得 getpriceを使う

                int pri = adapter.getprice(post);
                //priceを増減させる（input_priceを数値に変換する必要がある）
                ans = pint + pri;

                Log.i("aaaaaaaaaaa","pos="+post+" ans="+  ans);
                //変換ごの値でdbを更新する
                if(post==0) {
                    dbm.onMoneybookRent(sqlDB,month.getMonth(),ans);
                }else if(post==1) {
                    dbm.onMoneybookFood(sqlDB,month.getMonth(),ans);
                }else if(post==2) {
                    dbm.onMoneybookWater(sqlDB,month.getMonth(),ans);
                }else if(post==3) {
                    dbm.onMoneybookUtility(sqlDB,month.getMonth(),ans);
                }else if(post==4) {
                    dbm.onMoneybookCommunication(sqlDB,month.getMonth(),ans);
                }else if(post==5) {
                    dbm.onMoneybookHobby(sqlDB,month.getMonth(),ans);
                }else if(post==6) {
                    dbm.onMoneybookOther(sqlDB,month.getMonth(),ans);
                }
                //アダプターのsetpriceを使ってアダプターのpriceListの更新をする
                String vans = String.valueOf(ans);
                adapter.setprice(post, vans);
                Intent intent = new Intent(getApplication(), Ledgerdisp.class);
                startActivity(intent);
            }
        });
    }
}
