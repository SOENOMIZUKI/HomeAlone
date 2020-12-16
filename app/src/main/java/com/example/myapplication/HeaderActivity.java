package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

public class HeaderActivity extends AppCompatActivity {
    private SQLiteDatabase sqlDB;
    DBManager dbm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);
        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();

    }

    // アクションバーを表示するメソッド
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        int avatar = dbm.getSelectAvatarId(sqlDB);
        MenuItem item = menu.findItem(R.id.add_button);

        switch (avatar){
            case R.id.radiobtn:
                item.setIcon(R.drawable.gabon);

                return true;
            case R.id.radiobtn2:
                item.setIcon(R.drawable.soraziro);

                return true;
            case R.id.radiobtn3:
                item.setIcon(R.drawable.tanaka);

                return true;
            case R.id.radiobtn4:
                item.setIcon(R.drawable.totoro);
                return true;
            case R.id.radiobtn5:
                item.setIcon(R.drawable.doeryuga);

                return true;

        }
        return true;


    }

    // オプションメニューのアイテムが選択されたときに呼び出されるメソッド

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent1 = new Intent(getApplication(), Ledgerdisp.class);
                startActivity(intent1);
                return true;

            case R.id.item2:
                Intent intent2 = new Intent(getApplication(), AlarmActivity.class);
                startActivity(intent2);

                return true;
            case R.id.item3:
                Intent intent3 = new Intent(getApplication(), MapActivity.class);
                startActivity(intent3);

                return true;
            case R.id.item4:
                Intent intent4 = new Intent(getApplication(), AvatarActivity.class);
                startActivity(intent4);

                return true;
            case R.id.item5:
                Intent intent5 = new Intent(getApplication(), WeatherActivity.class);
                startActivity(intent5);

                return true;

            case R.id.add_button:
                int id = item.getItemId();
                if (id == R.id.add_button) {
                    Intent intent = new Intent(getApplication(), AccountDispActivity.class);
                    startActivity(intent);

                }
        }
                return super.onOptionsItemSelected(item);
        }
    }