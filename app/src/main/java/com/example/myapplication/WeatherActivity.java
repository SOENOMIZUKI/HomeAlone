package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
=======
>>>>>>> main
import android.os.Bundle;

import java.net.MalformedURLException;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

<<<<<<< HEAD
    private CaldroidSampleCustomFragment caldroidFragment;
    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy MMM dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //sql

        String lat = "33.437821";
        String lon = "129.972552";
=======
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
>>>>>>> main

        try {
            new AsyncHttpRequest(this)
                    .execute(
                            new URL("http://api.openweathermap.org/data/2.5/forecast?q=London,uk&APPID=f4ccf014306d1a86f61016ee7bb4a0d2")
                    );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
<<<<<<< HEAD

    public void dispCalender(ArrayList<String> weatherList) {

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

        caldroidFragment = new CaldroidSampleCustomFragment(weatherList);


        caldroidFragment.setArguments(args);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        final CaldroidListener listener = new CaldroidListener() {

            //日付をタップした時
            @Override
            public void onSelectDate(Date date, View view) {
                Intent intent = new Intent(WeatherActivity.this, CalendarComfirmActivity.class);
                startActivity(intent);
            }
            //月を変更したとき
            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }
            //日付を長くタップした時
            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }
            //画面を左にスライドしたとき
            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    Toast.makeText(getApplicationContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        };
        caldroidFragment.setCaldroidListener(listener);


    }
=======
>>>>>>> main
}
