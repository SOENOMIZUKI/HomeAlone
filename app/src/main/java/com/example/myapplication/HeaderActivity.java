package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class HeaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);
    }
    // アクションバーを表示するメソッド
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    // オプションメニューのアイテムが選択されたときに呼び出されるメソッド
    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        switch (item.getItemId()) {
            case R.id.item1:

                
            case R.id.item2:
                Intent intent2 = new Intent(getApplication(), AlarmActivity.class);
                startActivity(intent2);

                return true;
            case R.id.item3:
                Intent intent3 = new Intent(getApplication(), Signup.class);
                startActivity(intent3);

                return true;
            case R.id.item4:
                Intent intent4 = new Intent(getApplication(), AlarmSettingActivity.class);
                startActivity(intent4);

                return true;
            case R.id.item5:
                Intent intent5 = new Intent(getApplication(), CalendarActivity.class);
                startActivity(intent5);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}