package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;


public class Ledgerdisp extends AppCompatActivity {

    // 選択肢
    private String[] spinnerItems = {"2020年10月 ▼", "2020年11月 ▼"};
    private TextView textView;

    float rainfall[] = {98.8f, 123.8f, 34.6f, 43.9f, 69.4f, 12.5f, 52.8f};
    String monthNames[] = {"家賃", "食費", "趣味", "水道代", "光熱費", "通信費", "その他"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledgerdisp);

        //リスト
//        class MainActivity extends ListActivity {
//
//            // itemのアイコンと名前を保持するクラス
//            class CellData {
//                String imageComment;
//                int imageDrawableId;
//
//                CellData(String imageComment, int imageDrawableId) {
//                    this.imageComment = imageComment;
//                    this.imageDrawableId = imageDrawableId;
//                }
//            }
//
//            // Android が持っているシステムアイコン
//            private Integer[] imageDrawables = {
//                    android.R.drawable.ic_menu_close_clear_cancel,
//                    android.R.drawable.ic_menu_compass,
//                    android.R.drawable.ic_menu_crop,
//                    android.R.drawable.ic_menu_delete,
//                    android.R.drawable.ic_menu_directions,
//                    android.R.drawable.ic_menu_gallery,
//                    android.R.drawable.ic_menu_edit,
//            };
//
//            private String[] imageComments = {
//                    "家賃", "食費", "趣味", "水道代", "光熱費",
//                    "通信費", "その他"
//            };
//
//            @Override
//            protected void onCreate(Bundle savedInstanceState) {
//                super.onCreate(savedInstanceState);
//                setContentView(R.layout.activity_main);
//
//                List<CellData> list = new ArrayList<>();
//
//                for (int i = 0; i < imageDrawables.length ; i++){
//                    CellData data = new CellData(imageComments[i], imageDrawables[i]);
//                    list.add(data);
//                }
//
//                setListAdapter(new ListViewAdapter(this, R.layout.list, list));
//            }
//
//            class ViewHolder {
//                TextView textView;
//                ImageView imageView;
//            }
//
//            // ArrayAdapterを継承したカスタムのアダプタークラス
//          class ListViewAdapter extends ArrayAdapter<CellData> {
//                private LayoutInflater inflater;
//                private int itemLayout;
//                CellData data;
//
//                ListViewAdapter(Context context, int itemLayout, List<CellData> list) {
//                    super(context, 0, list);
//                    this.inflater =
//                            (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    this.itemLayout = itemLayout;
//                }
//
//                @Override
//                public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
//                    ViewHolder holder;
//                    if (convertView == null) {
//                        convertView = inflater.inflate(itemLayout, parent, false);
//                        holder = new ViewHolder();
//                        holder.textView = convertView.findViewById(R.id.textView);
//                        holder.imageView = convertView.findViewById(R.id.imageView);
//                        convertView.setTag(holder);
//                    } else {
//                        holder = (ViewHolder) convertView.getTag();
//                    }
//
//                    data = getItem(position);
//                    if(data != null){
//                        holder.textView.setText(data.imageComment);
//                        holder.imageView.setImageResource(data.imageDrawableId);
//                    }
//                    return convertView;
//                }
//            }
//        }
        // ListViewに表示するリスト項目をArrayListで準備する
       ArrayList data = new ArrayList<>();
        data.add("家賃");
        data.add("食費");
        data.add("趣味");
        data.add("水道代");
        data.add("光熱費");
        data.add("通信費");
        data.add("その他");

        // リスト項目とListViewを対応付けるArrayAdapterを用意する
        ArrayAdapter Aadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

        // ListViewにArrayAdapterを設定する
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(Aadapter);

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
                Spinner spinner = (Spinner)parent;
                String item = (String)spinner.getSelectedItem();
                textView.setText(item);
            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
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
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);

        //PieChartを取得する:
        PieChart piechart = (PieChart) findViewById(R.id.chart);
        piechart.setData(data);
        piechart.invalidate();
    }
}