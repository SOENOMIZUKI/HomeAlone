package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WeatherActivity extends AppCompatActivity {

    private CaldroidSampleCustomFragment caldroidFragment;
    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy MMM dd");
    String address;
    private SQLiteDatabase sqlDB;
    DBManager dbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();
        address = dbm.getAddress(sqlDB);
        Log.i("tag","住所は"+ address);

        try {
            new GetAddress(this)
                    .execute(
                            new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+ address +"&components=country:JP&key=APIKey")
                    );
        } catch (MalformedURLException e) {
            e.printStackTrace();
       }
    }
}
