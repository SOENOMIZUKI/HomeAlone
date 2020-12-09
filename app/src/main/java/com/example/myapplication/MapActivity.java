package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MapActivity extends HeaderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Button buttonS = (Button) findViewById(R.id.btnsuperr);
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.google.co.jp/maps/search/%E3%82%B9%E3%83%BC%E3%83%91%E3%83%BC");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Button buttonK = (Button) findViewById(R.id.btnkon);
        buttonK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.google.co.jp/maps/search/%E3%82%B3%E3%83%B3%E3%83%93%E3%83%8B");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Button buttonB = (Button) findViewById(R.id.btnbyou);
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.google.co.jp/maps/search/%E7%97%85%E9%99%A2");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Button buttonW = (Button) findViewById(R.id.btnken);
        buttonW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText search1 = (EditText)findViewById(R.id.search);
                String search = search1.getText().toString();
                Uri uri = Uri.parse("https://www.google.co.jp/maps/search/"+search);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });




    }
}