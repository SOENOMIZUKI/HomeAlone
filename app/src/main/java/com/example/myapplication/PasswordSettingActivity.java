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

public class PasswordSettingActivity extends HeaderActivity {
    private SQLiteDatabase sqlDB;
    DBManager dbm;
    boolean repeat = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_setting);
    }
    @Override
    protected void onResume() {
        super.onResume();

        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();

        Button button = (Button)findViewById(R.id.password_update_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText bpw = (EditText) findViewById(R.id.before_password);
                EditText pw1 = (EditText) findViewById(R.id.password1);
                EditText pw2 = (EditText) findViewById(R.id.password2);

                String before_password = bpw.getText().toString();
                String user_password1 = pw1.getText().toString();
                String user_password2 = pw2.getText().toString();

                User user = dbm.getUserSetting(sqlDB);

                if(before_password.equals("")){
                    bpw.setError("入力してください");
                    repeat = false;
                }
                if(!before_password.equals(user.getPassword())){
                    bpw.setError("パスワードが正しくありません");
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

                if(repeat){
                    dbm.setUserPassword(sqlDB, user_password1);
                    Intent intent = new Intent(PasswordSettingActivity.this, AccountDispActivity.class);
                    startActivity(intent);
                }
            }
        });
        Button return_button3 = (Button)findViewById(R.id.return_button3);
        return_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordSettingActivity.this, AccountDispActivity.class);
                startActivity(intent);
            }
        });
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