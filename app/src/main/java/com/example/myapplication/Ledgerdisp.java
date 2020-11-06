package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class Ledgerdisp extends AppCompatActivity {

    float rainfall[] = {98.8f, 123.8f, 34.6f, 43.9f, 69.4f, 12.5f, 52.8f};
    String monthNames[] = {"家賃", "食費", "趣味", "水道代", "光熱費", "通信費", "その他"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledgerdisp);

        setupPieChart();
    }


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