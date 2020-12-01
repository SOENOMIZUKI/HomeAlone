package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.nfc.NfcAdapter.EXTRA_DATA;



public class Ledgerdisp extends AppCompatActivity {

    private SQLiteDatabase sqlDB;
    DBManager dbm;
    ListView lv;
    SimpleAdapter sAdapter;
    ArrayList<HashMap<String, String>> listData;
    int sum = 0;
    int[] ia = new int[price.length];
    int[] en = new int[ia.length];
    // 選択肢
    private String[] spinnerItems = {"2020/10", "2020/11", "2020/12", "2021/1", "2021/2", "2021/3", "2021/4", "2021/5", "2021/6",
            "2021/7", "2021/8", "2021/9"};
    private TextView textView;




//    float rainfall[] = {36f,43f,69f,76f,12f,45f,23f};
    Integer rainfall[] = {en[0],en[1], en[2], en[3],en[4],en[5],en[6]};
    String monthNames[] = {"家賃", "食費", "趣味", "水道代", "光熱費", "通信費", "その他"};


    //リスト
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
    protected void onResume() {
        super.onResume();
        try{
            String strDate = "2020/10";

            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM");
            Date date = sdFormat.parse(strDate);

            dbm = new DBManager(this);
            sqlDB = dbm.getWritableDatabase();


            //priceリストに金額を入れる
            Kakeibo kakeibo = dbm.selectMoneybook(sqlDB, date.getMonth());
            price[0] = kakeibo.getRent().toString();
            price[1] = kakeibo.getFood_expenses().toString();
            price[2] = kakeibo.getWater_costs().toString();
            price[3] = kakeibo.getUtility_costs().toString();
            price[4] = kakeibo.getCommunication_costs().toString();
            price[5] = kakeibo.getHobby().toString();
            price[6] = kakeibo.getOther().toString();


                //priceリストの中身をString型からInt型に変換
                for (int i = 0; i < price.length; i++) {
                    ia[i] = Integer.parseInt(price[i]);
                    rainfall[i] = ia[i];
                }
                // 合計金額の保存
                for(int n = 0; n<ia.length; n++){
                sum = sum + ia[n];
                }
                    TextView txt1 = (TextView) findViewById(R.id.sumtext);
                    // テキストビューのテキストを設定します
                    txt1.setText("" + sum);

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

            textView = findViewById(R.id.text_view);

            Spinner spinner = findViewById(R.id.spinner);

            // ArrayAdapter
            ArrayAdapter<String> adapter
                    = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spinnerItems);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // spinner に adapter をセット
            spinner.setAdapter(adapter);

            // リスナーを登録
            spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                //　アイテムが選択された時
                @Override
                public void onItemSelected(AdapterView<?> parent,
                                           View view, int position, long id) {
                    Spinner spinner = (Spinner) parent;
                    String item = (String) spinner.getSelectedItem();

                    try {
                        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM");
                        Date date = sdFormat.parse(item);

                        Kakeibo kakeibo = dbm.selectMoneybook(sqlDB, date.getMonth());

                    // ListViewのインスタンスを生成
                    ListView listView = findViewById(R.id.listView);

                    kakeiboAdapter adapter = (kakeiboAdapter) listView.getAdapter();
                        adapter.setprice(0,kakeibo.getRent().toString());
                        adapter.setprice(1,kakeibo.getFood_expenses().toString());
                        adapter.setprice(2,kakeibo.getWater_costs().toString());
                        adapter.setprice(3,kakeibo.getUtility_costs().toString());
                        adapter.setprice(4,kakeibo.getCommunication_costs().toString());
                        adapter.setprice(5,kakeibo.getHobby().toString());
                        adapter.setprice(6,kakeibo.getOther().toString());

                        adapter.notifyDataSetInvalidated();
                    }catch(ParseException e) {
                        e.printStackTrace();
                        }
                }

                //　アイテムが選択されなかった
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //家計簿変更画面遷移
            Button changeButton = findViewById(R.id.changebottom);
            changeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplication(), Ledgerchange.class);
                    try {
                        Spinner spinner = findViewById(R.id.spinner);
                        String item = (String) spinner.getSelectedItem();

                        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM");
                        Date date = sdFormat.parse(item);
                        intent.putExtra("Month", date);
                        }  catch (ParseException e) {
                        e.printStackTrace();
                            }
                    startActivity(intent);
                }
            });
            //家計簿追加画面遷移
            Button inputButton = findViewById(R.id.inputbottom);
            inputButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplication(), Ledgerinput.class);
                    try {
                        Spinner spinner = findViewById(R.id.spinner);
                        String item = (String) spinner.getSelectedItem();

                        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM");
                        Date date = sdFormat.parse(item);
                        intent.putExtra("Month", date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
            });

            // date.getMonth()
        } catch(
                ParseException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledgerdisp);

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
                Color.rgb(24,127,196),
                Color.rgb(0,169,95),
                Color.rgb(84,195,241),
                Color.rgb(210,131,0),
                Color.rgb(243,152,0),
                Color.rgb(238,135,180),
                Color.rgb(153,153,153)
                );
        PieData data = new PieData(dataSet);

        //PieChartを取得する:
        PieChart piechart = (PieChart) findViewById(R.id.chart);
        Description description = new Description();
        description.setText("");
        piechart.setDescription(description);
        piechart.getLegend().setEnabled(false);
        piechart.setData(data);
        piechart.invalidate();
    }
}



