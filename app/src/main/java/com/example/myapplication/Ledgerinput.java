package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Ledgerinput extends AppCompatActivity {

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
        setContentView(R.layout.activity_ledgerinput);

        ListView listView = findViewById(R.id.listView);

        // BaseAdapter を継承したadapterのインスタンスを生成
        // レイアウトファイル kakeibo_list.xml を
        // activity_legerinput.xml に inflate するためにKadapterに引数として渡す
        BaseAdapter Kadapter = new kakeiboAdapter(this.getApplicationContext(),
                R.layout.kakeibo_list, names, price, photos);

        // ListViewにadapterをセット
        listView.setAdapter(Kadapter);

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
                finish();
            }
        });
    }
}