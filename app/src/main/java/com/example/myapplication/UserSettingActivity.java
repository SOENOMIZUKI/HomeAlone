package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSettingActivity extends AppCompatActivity {
    private SQLiteDatabase sqlDB;
    DBManager dbm;
    boolean repeat = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
    }
    @Override
    protected void onResume() {
        super.onResume();

        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();

        Button button = (Button)findViewById(R.id.user_insert_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.user_name) ;
                EditText mail = (EditText) findViewById(R.id.mailaddress);
                EditText address = (EditText) findViewById(R.id.street_address) ;
                String user_name = name.getText().toString();
                String user_mailaddress = mail.getText().toString();
                String user_street_address = address.getText().toString();
                if(user_name.equals("")){
                    name.setError("入力してください");
                    repeat = false;
                }
                if(user_mailaddress.equals("")){
                    mail.setError("入力してください");
                    repeat = false;
                }
                if(!emailValidator(user_mailaddress)){
                    mail.setError("正しいメールアドレスではありません");
                    repeat = false;
                }
                if(user_street_address.equals("")){
                    address.setError("入力してください");
                    repeat = false;
                }
                if(repeat){

                    //dbm.signUp(sqlDB, user_name, user_mailaddress, user_street_address);
                    //Intent intent = new Intent(Signup.this, CalendarActivity.class);
                    //Intent intent = new Intent(Signup.this, AccountDispActivity.class);
                    //startActivity(intent);

                }
            }
        });
    }

    //メールアドレス判定
    public static boolean emailValidator(final String mailAddress) {

        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mailAddress);
        return matcher.matches();

    }

}