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

import static android.nfc.NfcAdapter.EXTRA_DATA;

public class Ledgerchange extends AppCompatActivity {


    private SQLiteDatabase sqlDB;
    DBManager dbm;
    private long selectedID = -1;
    private int lastPosiotion = -1;
    private int count = 0;
    private String month ;
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
            "30000",
            "10000",
            "4000",
            "2000",
            "1000",
            "3000",
            "10000",
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
        Intent intent = getIntent();
        month = intent.getStringExtra("Month");
        setContentView(R.layout.activity_ledgerchange);

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
                //if(selectedID != -1){
                //   Log.i("listView","lastposition:"+lastPosiotion+"parent.SIZE:"+parent.getCount());
                //   parent.getChildAt(lastPosiotion).setBackgroundColor(0);
//
                //              }
                count = position;

                Log.i("listView", "parent.getFirstVisiblePosition():" + parent.getFirstVisiblePosition());
                for (int i = 0; i < 5; i++) {
                    Log.i("listView", "i:" + i);
                    //parent.getItemAtPosition(i).setBackgroundColor(0);
                    //((View)parent.getItemAtPosition(i)).setBackgroundColor(0);
                }
                //view.setBackgroundColor(getResources().getColor(R.color.BLACK));
                //lastPosiotion = position;
                //selectedID = id;
            }
        });


        //エラーチェック
        //入力チェック
        final EditText editText = findViewById(R.id.editTextTextPersonName3);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean flag) {
                if (!flag) {
                    String str = editText.getText().toString().trim();
                    if (str.matches("")) {
                        Toast toast = Toast.makeText(Ledgerchange.this, "入力されていません", Toast.LENGTH_LONG);
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
        Button changefinish = findViewById(R.id.changefinish);
        changefinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        EditText money = (EditText) findViewById(R.id.editTextTextPersonName3);
        String updatemoney = money.getText().toString();
        int price = Integer.parseInt(updatemoney);
        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();
//        if (count == 0) {
//            dbm.onMoneybookRent(sqlDB, month, price);
//        }
//        else if(count == 1) {
//            dbm.onMoneybookFood(sqlDB, month, price);
//        }
//        else if(count == 2) {
//            dbm.onMoneybookWater(sqlDB, month, price);
//        }
//        else if(count == 3) {
//            dbm.onMoneybookUtility(sqlDB, month, price);
//        }
//        else if(count == 4) {
//            dbm.onMoneybookCommunication(sqlDB, month, price);
//        }
//        else if(count == 5) {
//            dbm.onMoneybookHobby(sqlDB, month, price);
//        }
//        else if(count == 6) {
//            dbm.onMoneybookOther(sqlDB, month, price);
//        }
    }
}