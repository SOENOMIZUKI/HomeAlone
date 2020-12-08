package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AvatarActivity extends HeaderActivity {

    private ImageView imageView;

    public static class Avatar {
        public String image;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        basicReadWrite();
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

        Button button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(AvatarActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}



