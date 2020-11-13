package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;

public class Signup extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //ヒントのアニメーションをoffにする処理

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.name_box);
        final CharSequence hint1 = til1.getHint();
        til1.setHint(null);
        til1.getEditText().setHint(hint1);
        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.address_box);
        final CharSequence hint2 = til2.getHint();
        til2.setHint(null);
        til2.getEditText().setHint(hint2);

    }
}