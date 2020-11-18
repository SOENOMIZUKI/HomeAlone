package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.IntegerRes;

public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context){
        super(context,"Alarm.sqlite3",null,3);
    }
    // テーブル作成
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        sqLiteDatabase.execSQL("CREATE TABLE " +
                " Alarm(alarm_id INTEGER PRIMARY KEY AUTOINCREMENT,data TEXT,repeat INTEGER,switch INTEGER)");

        //アバターの外部キーを書いてない
        sqLiteDatabase.execSQL("CREATE TABLE  User(user_id INTEGER PRIMARY KEY AUTOINCREMENT,user_name TEXT,mailaddress TEXT,password TEXT,street_address TEXT,avatar_id INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i,int i1){
        sqLiteDatabase.execSQL("DROP TABLE Alarm");
        sqLiteDatabase.execSQL("DROP TABLE User");
        onCreate(sqLiteDatabase);
    }
    public void insertAlarm(SQLiteDatabase sqLiteDatabase, String data, Integer repeat){
        String sql = "INSERT INTO Alarm(data,repeat,switch) VALUES(?,?,1)";
        sqLiteDatabase.execSQL(sql,new String[]{data});
        sqLiteDatabase.execSQL(sql,new Integer[]{repeat});
    }
    public void deleteAlarm(SQLiteDatabase sqLiteDatabase, String data){
        String sql = "DELETE from Alarm where data = ?";
        sqLiteDatabase.execSQL(sql,new String[]{data});
    }
    public void changeAlarm(SQLiteDatabase sqLiteDatabase, String data, Integer repeat, Integer switch1){
        String sql = "UPDATE Alarm set data=?,repeat=?,switch=?";
        sqLiteDatabase.execSQL(sql,new String[]{data});
        sqLiteDatabase.execSQL(sql,new Integer[]{repeat});
        sqLiteDatabase.execSQL(sql,new Integer[]{switch1});
    }
    //ユーザー登録
    public void signUp(SQLiteDatabase sqLiteDatabase, String user_name, String mailaddress, String password,String street_address){
        String sql = "INSERT INTO User(user_name,mailaddress,password,street_address,avatar_id) VALUES(?,?,?,?,1)";
        sqLiteDatabase.execSQL(sql,new String[]{user_name});
        sqLiteDatabase.execSQL(sql,new String[]{mailaddress});
        sqLiteDatabase.execSQL(sql,new String[]{password});
        sqLiteDatabase.execSQL(sql,new String[]{street_address});
    }
}
