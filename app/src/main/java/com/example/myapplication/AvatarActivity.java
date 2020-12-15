package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AvatarActivity extends HeaderActivity {

    private ImageView imageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        basicReadWrite();
        final RadioGroup gr = (RadioGroup) findViewById(R.id.radioGroup);
        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
        group.check(R.id.radiobtn);


        /*gr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                if (arg0 == gr) {
                    switch (arg1) {
                        //ラジオボタン1が押されたとき
                        case R.id.radiobtn:
                            // (() findViewById(R.id.icon)).setImageResource(R.drawable.gabon);
                            break;
                        //ラジオボタン2が押されたとき
                        case R.id.radiobtn2:
                            ((ImageView) findViewById(R.id.add_button)).setImageResource(R.drawable.soraziro);
                            break;
                        //ラジオボタン3が押されたとき
                        case R.id.radiobtn3:
                            ((ImageView) findViewById(R.id.add_button)).setImageResource(R.drawable.tanaka);
                            break;
                        //ラジオボタン4が押されたとき
                        case R.id.radiobtn4:
                            ((ImageView) findViewById(R.id.add_button)).setImageResource(R.drawable.totoro);
                            break;
                        //ラジオボタン5が押されたとき
                        case R.id.radiobtn5:
                            ((ImageView) findViewById(R.id.add_button)).setImageResource(R.drawable.doeryuga);
                            break;

                    }

                }
            }
        });*/
    }


    public void basicReadWrite() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("avatar");
        myRef.setValue("doeryuga");

        ImageView image = findViewById(R.id.imageView);
        ImageView image2 = findViewById(R.id.imageView2);
        ImageView image3 = findViewById(R.id.imageView3);
        ImageView image4 = findViewById(R.id.imageView4);
        ImageView image5 = findViewById(R.id.imageView5);
        //findviewid


        myRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //画像を表示する

                imageView = (ImageView) findViewById(R.id.imageView);
                String fileName = "slide";
                int picId = getResources().getIdentifier(fileName, "drawable", getPackageName());
                imageView.setImageResource(picId);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(AvatarActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });
    }
}



