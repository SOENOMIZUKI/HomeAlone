package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< Updated upstream
import android.os.Bundle;
=======
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
>>>>>>> Stashed changes

import java.net.MalformedURLException;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

<<<<<<< Updated upstream
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
=======
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
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

        try {
            new GetAddress(this)
                    .execute(
<<<<<<< Updated upstream
<<<<<<< Updated upstream
                            new URL("http://api.openweathermap.org/data/2.5/forecast?q=London,uk&APPID=f4ccf014306d1a86f61016ee7bb4a0d2")
                    );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
=======
                            new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+ address +"components=country:JP&key=AIzaSyCe8iC-25jYIh9H6XijaTysl974CcNe8bU")
                    );
        } catch (MalformedURLException e) {
            e.printStackTrace();
       }
>>>>>>> Stashed changes
=======
                            new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+ address +"components=country:JP&key=AIzaSyCe8iC-25jYIh9H6XijaTysl974CcNe8bU")
                    );
        } catch (MalformedURLException e) {
            e.printStackTrace();
       }
>>>>>>> Stashed changes
    }
}
