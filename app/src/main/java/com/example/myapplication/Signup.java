package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Signup extends AppCompatActivity {

    private SQLiteDatabase sqlDB;
    DBManager dbm;
    boolean repeat = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
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
                EditText pw1 = (EditText) findViewById(R.id.password1);
                EditText pw2 = (EditText) findViewById(R.id.password2);
                EditText address = (EditText) findViewById(R.id.street_address) ;
                String user_name = name.getText().toString();
                String user_mailaddress = mail.getText().toString();
                String user_password1 = pw1.getText().toString();
                String user_password2 = pw2.getText().toString();
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
                if(!checkLogic(user_password1)){
                    pw1.setError("半角英数字で入力してください");
                    repeat = false;
                }
                if(user_password1.length() < 8){
                    pw1.setError("8文字以上で入力してください");
                    repeat = false;
                }
                if(!user_password2.equals(user_password1)){
                    pw2.setError("上のパスワードと一致しません");
                    repeat = false;
                }
                if(user_stree_address.equals("")){
                    address.setError("入力してください");
                    repeat = false;
                }
                if(repeat){
                     dbm.signUp(sqlDB, user_name, user_mailaddress, user_password1, user_street_address);
                     Toast.makeText(CalendarActivity.this, "登録しました", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signup.class, CalendarActivity.class);
                    startActivity(intent);
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

    //半角英数字判定
    public static boolean checkLogic(String target) {
        boolean result = true;
        String regex_AlphaNum = "^[A-Za-z0-9]+$" ; // 半角英数字のみ
        if( target == null || target.isEmpty() ) return false ;
        // 3. 引数に指定した正規表現regexがtargetにマッチするか確認する
        Pattern p1 = Pattern.compile(regex_AlphaNum); // 正規表現パターンの読み込み
        Matcher m1 = p1.matcher(target); // パターンと検査対象文字列の照合
        result = m1.matches(); // 照合結果をtrueかfalseで取得
        return result;
    }
}