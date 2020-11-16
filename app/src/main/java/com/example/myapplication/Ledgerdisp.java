package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;


public class Ledgerdisp extends AppCompatActivity {
    ListView lv;
    SimpleAdapter sAdapter;
    ArrayList<HashMap<String, String>> listData;

    // 選択肢
    private String[] spinnerItems = {"2020年10月 ▼", "2020年11月 ▼"};
    private TextView textView;

    float rainfall[] = {98.8f, 123.8f, 34.6f, 43.9f, 69.4f, 12.5f, 52.8f};
    String monthNames[] = {"家賃", "食費", "趣味", "水道代", "光熱費", "通信費", "その他"};


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ledgerdisp);

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
        setContentView(R.layout.activity_ledgerdisp);

        // ListViewのインスタンスを生成
        ListView listView = findViewById(R.id.listView);

        // BaseAdapter を継承したadapterのインスタンスを生成
        // レイアウトファイル list_items.xml を
        // activity_main.xml に inflate するためにKadapterに引数として渡す
        BaseAdapter Kadapter = new kakeiboAdapter(this.getApplicationContext(),
                R.layout.kakeibo_list, names, price, photos);

        // ListViewにadapterをセット
        listView.setAdapter(Kadapter);



    //プルダウンメニュー
    setupPieChart();

    textView =

    findViewById(R.id.text_view);

    Spinner spinner = findViewById(R.id.spinner);

    // ArrayAdapter
    ArrayAdapter<String> adapter
            = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, spinnerItems);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    // spinner に adapter をセット
        spinner.setAdapter(adapter);

    // リスナーを登録
        spinner.setOnItemSelectedListener(new

    OnItemSelectedListener() {
        //　アイテムが選択された時
        @Override
        public void onItemSelected (AdapterView < ? > parent,
                View view,int position, long id){
            Spinner spinner = (Spinner) parent;
            String item = (String) spinner.getSelectedItem();
            textView.setText(item);
        }

        //　アイテムが選択されなかった
        public void onNothingSelected (AdapterView < ? > parent){
        }
    });
}



    //円グラフ
    private void setupPieChart() {
        //PieEntriesのリストを作成する:
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < rainfall.length; i++) {
            pieEntries.add(new PieEntry(rainfall[i], monthNames[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Rainfall for Vancouver");
        //dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setColors(
                Color.rgb(0,0,0),
                Color.rgb(255,0,0),
                Color.rgb(0,255,0),
                Color.rgb(0,0,255),
                Color.rgb(0,255,0),
                Color.rgb(0,255,0),
                Color.rgb(0,255,0)
                );
        PieData data = new PieData(dataSet);

        //PieChartを取得する:
        PieChart piechart = (PieChart) findViewById(R.id.chart);
        piechart.setData(data);
        piechart.invalidate();
    }
}


