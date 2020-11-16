package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.net.MalformedURLException;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            new AsyncHttpRequest(this)
                    .execute(
                            new URL("http://api.openweathermap.org/data/2.5/forecast?q=London,uk&APPID=f4ccf014306d1a86f61016ee7bb4a0d2")
                    );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
