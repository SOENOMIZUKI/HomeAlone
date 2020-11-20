package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context){
        super(context,"Alarm.sqlite3",null,3);
    }
    // テーブル作成
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS" +
                " Alarm(alarm_id INTEGER PRIMARY KEY AUTOINCREMENT,data TEXT,repeat INTEGER,switch INTEGER)");
<<<<<<< HEAD

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS" +
                " Moneybook(month INTEGER PRIMARY KEY AUTOINCREMENT,rent INTEGER,food_expenses INTEGER,water_costs INTEGER,utility_costs INTEGER,communication_costs INTEGER,hobby INTEGER,other INTEGER)");
=======
        //アバターの外部キーを書いてない
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS" +  
                " User(user_id INTEGER PRIMARY KEY AUTOINCREMENT,user_name TEXT,mailaddress TEXT,password TEXT,street_address TEXT,avatar_id INTEGER)");
>>>>>>> main
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i,int i1){
        sqLiteDatabase.execSQL("DROP TABLE Alarm");
<<<<<<< HEAD
        sqLiteDatabase.execSQL("DROP TABLE Moneybook");
=======
        sqLiteDatabase.execSQL("DROP TABLE User");
>>>>>>> main
        onCreate(sqLiteDatabase);
    }
    public void insertAlarm(SQLiteDatabase sqLiteDatabase, String data, Integer repeat){
        String sql = "INSERT INTO Alarm(data,repeat,switch) VALUES(?,?,1)";
        sqLiteDatabase.execSQL(sql,new Object[]{data,repeat});
    }
    public void deleteAlarm(SQLiteDatabase sqLiteDatabase, String data){
        String sql = "DELETE from Alarm where data = ?";
        sqLiteDatabase.execSQL(sql,new String[]{data});
    }
    public void onAlarm(SQLiteDatabase sqLiteDatabase, String data){
        String sql = "UPDATE Alarm set switch=1 WHERE data = ?";
        sqLiteDatabase.execSQL(sql,new Object[]{data});
    }
    public void offAlarm(SQLiteDatabase sqLiteDatabase, String data){
        String sql = "UPDATE Alarm set switch=2 WHERE data = ?";
        sqLiteDatabase.execSQL(sql,new Object[]{data});
    }
    public void onRepeat(SQLiteDatabase sqLiteDatabase, String data){
        String sql = "UPDATE Alarm set repeat=1 WHERE data = ?";
        sqLiteDatabase.execSQL(sql,new Object[]{data});
    }
    public void offRepeat(SQLiteDatabase sqLiteDatabase, String data){
        String sql = "UPDATE Alarm set repeat=2 WHERE data = ?";
        sqLiteDatabase.execSQL(sql,new Object[]{data});
    }
    public List<Alarm> selectAlarmList(SQLiteDatabase sqLiteDatabase){
        List<Alarm> AlarmList = new ArrayList<>();
        String selectSql = "SELECT * FROM Alarm ORDER BY alarm_id";
        SQLiteCursor cursor = (SQLiteCursor)sqLiteDatabase.rawQuery(selectSql,null);
        while (cursor.moveToNext()){
            Alarm alarm = new Alarm();
            alarm.setAlarm_id(cursor.getInt(cursor.getColumnIndex("alarm_id")));
            alarm.setTime(cursor.getString(cursor.getColumnIndex("data")));
            alarm.setRepeat(cursor.getInt(cursor.getColumnIndex("repeat")));
            alarm.setSwitch1(cursor.getInt(cursor.getColumnIndex("switch")));
            AlarmList.add(alarm);
        }
        return AlarmList;
    }
    public Alarm selectAlarm(SQLiteDatabase sqLiteDatabase,String data) {
        String selectSql = "SELECT * FROM Alarm WHERE data = ?";
        SQLiteCursor cursor = (SQLiteCursor)sqLiteDatabase.rawQuery(selectSql,new String[]{data},null);
        cursor.moveToNext();
        Alarm alarm = new Alarm();
        alarm.setAlarm_id(cursor.getInt(cursor.getColumnIndex("alarm_id")));
        alarm.setTime(cursor.getString(cursor.getColumnIndex("data")));
        alarm.setRepeat(cursor.getInt(cursor.getColumnIndex("repeat")));
        alarm.setSwitch1(cursor.getInt(cursor.getColumnIndex("switch")));
        return alarm;
    }
    //ユーザー登録
    public void signUp(SQLiteDatabase sqLiteDatabase, String user_name, String mailaddress, String password,String street_address){
        String sql = "INSERT INTO User(user_name,mailaddress,password,street_address,avatar_id) VALUES(?,?,?,?,1)";
        sqLiteDatabase.execSQL(sql,new String[]{user_name});
        sqLiteDatabase.execSQL(sql,new String[]{mailaddress});
        sqLiteDatabase.execSQL(sql,new String[]{password});
        sqLiteDatabase.execSQL(sql,new String[]{street_address});
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
