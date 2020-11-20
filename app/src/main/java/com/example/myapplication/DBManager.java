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

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS" +
                " Alarm(alarm_id INTEGER PRIMARY KEY AUTOINCREMENT,data TEXT,repeat INTEGER,switch INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS" +
                " Moneybook(month INTEGER PRIMARY KEY AUTOINCREMENT,rent INTEGER,food_expenses INTEGER,water_costs INTEGER,utility_costs INTEGER,communication_costs INTEGER,hobby INTEGER,other INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i,int i1){
        sqLiteDatabase.execSQL("DROP TABLE Alarm");
        sqLiteDatabase.execSQL("DROP TABLE Moneybook");
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
    public void insertMoneybook(SQLiteDatabase sqLiteDatabase,Integer rent,Integer food_expenses,Integer water_costs,Integer utility_costs,Integer communication_costs,Integer hobby,Integer other){
        String sql = "UPDATE Moneybook(rent,food_expenses,water_costs,communication_costs,hobby,other) VALUES(?,?,?,?,?,?,?)";
        sqLiteDatabase.execSQL(sql,new Integer[]{rent,food_expenses,water_costs,utility_costs,communication_costs,hobby,other});
    }
    public void changeMoneybook(SQLiteDatabase sqLiteDatabase,Integer rent,Integer food_expenses,Integer water_costs,Integer utility_costs,Integer communication_costs,Integer hobby,Integer other){
        String sql = "UPDATE Moneybook set rent=?,food_expenses=?,water_costs=?,communication_costs=?,hobby=?,other=?";
        sqLiteDatabase.execSQL(sql,new Integer[]{rent,food_expenses,water_costs,utility_costs,communication_costs,hobby,other});
    }

}
