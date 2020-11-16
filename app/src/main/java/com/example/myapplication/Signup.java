package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Signup extends AppCompatActivity {
    boolean repeat = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Button button = (Button)findViewById(R.id.user_insert_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.name_box) ;
                EditText email = (EditText) findViewById(R.id.editTextTextEmailAddress);
                EditText password1 = (EditText) findViewById(R.id.editTextTextPassword);
                EditText password2 = (EditText) findViewById(R.id.editTextTextPassword2);
                EditText address = (EditText) findViewById(R.id.address_box) ;
                String user_name = name.getText().toString();
                String user_email = email.getText().toString();
                String user_password1 = password1.getText().toString();
                String user_password2 = password2.getText().toString();
                String user_address = address.getText().toString();
                if(user_name.equals("")){
                    name.setError("入力してください");
                    repeat = false;
                }
                if(user_email.equals("")){
                    email.setError("入力してください");
                    repeat = false;
                }
                if(!emailValidator(user_email)){
                    email.setError("正しいメールアドレスではありません");
                    repeat = false;
                }
                if(!checkLogic(user_password1)){
                    password1.setError("半角英数字で入力してください");
                    repeat = false;
                }
                if(user_password1.length() < 8){
                    password1.setError("8文字以上で入力してください");
                    repeat = false;
                }
                if(!user_password2.equals(user_password1)){
                    password2.setError("上のパスワードと一致しません");
                    repeat = false;
                }
                if(user_address.equals("")){
                    address.setError("入力してください");
                    repeat = false;
                }
                if(repeat){
                    
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