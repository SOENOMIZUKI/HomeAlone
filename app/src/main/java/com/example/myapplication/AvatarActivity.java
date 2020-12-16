package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
    private SQLiteDatabase sqlDB;
    DBManager dbm;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();
        basicReadWrite();

        final RadioGroup gr = (RadioGroup) findViewById(R.id.radioGroup);
        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);



        group.check(R.id.radiobtn);



    }



    public void basicReadWrite() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("avatar");


        ImageView image =  findViewById(R.id.imageView);
        ImageView image2 = findViewById(R.id.imageView2);
        ImageView image3 = findViewById(R.id.imageView3);
        ImageView image4 = findViewById(R.id.imageView4);
        ImageView image5 = findViewById(R.id.imageView5);
        //findviewid


        myRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Avatar avatar = dataSnapshot.child("gabon").getValue(Avatar.class);
                Avatar avatar2 = dataSnapshot.child("soraziro").getValue(Avatar.class);
                Avatar avatar3 = dataSnapshot.child("tanaka").getValue(Avatar.class);
                Avatar avatar4 = dataSnapshot.child("totoro").getValue(Avatar.class);
                Avatar avatar5 = dataSnapshot.child("doeryuga").getValue(Avatar.class);
                //画像を表示する


                imageView = (ImageView) findViewById(R.id.imageView);
                String fileName = avatar.getImage();
                int picId = getResources().getIdentifier(fileName, "drawable", getPackageName());
                imageView.setImageResource(picId);

                imageView = (ImageView) findViewById(R.id.imageView2);
                String fileName2 = avatar2.getImage();
                int picId2 = getResources().getIdentifier(fileName2, "drawable", getPackageName());
                imageView.setImageResource(picId2);

                imageView = (ImageView) findViewById(R.id.imageView3);
                String fileName3 = avatar3.getImage();
                int picId3 = getResources().getIdentifier(fileName3, "drawable", getPackageName());
                imageView.setImageResource(picId3);

                imageView = (ImageView) findViewById(R.id.imageView4);
                String fileName4 = avatar4.getImage();
                int picId4 = getResources().getIdentifier(fileName4, "drawable", getPackageName());
                imageView.setImageResource(picId4);

                imageView = (ImageView) findViewById(R.id.imageView5);
                String fileName5 = avatar5.getImage();
                int picId5 = getResources().getIdentifier(fileName5, "drawable", getPackageName());
                imageView.setImageResource(picId5);




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
                RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
                int id = rg.getCheckedRadioButtonId();
                dbm.setAvatarId(sqlDB, id);
                Intent intent = new Intent(AvatarActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });
    }
}



