package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context){
        super(context,"Alarm.sqlite3",null,7);
    }
    // テーブル作成
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS" +
                " Alarm(alarm_id INTEGER PRIMARY KEY AUTOINCREMENT,data TEXT,repeat INTEGER,switch INTEGER)");
        //アバターの外部キーを書いてない
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS" +  
                " User(user_id INTEGER PRIMARY KEY AUTOINCREMENT,user_name TEXT,mailaddress TEXT,password TEXT,street_address TEXT,avatar_id INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS" +
                " Plans(calendar_id INTEGER PRIMARY KEY AUTOINCREMENT,date TEXT,plans TEXT,starttime TEXT,finishtime TEXT,notification TEXT,color INTEGER,note TEXT)");
        Log.i("aaaa","onCreate(SQLiteDatabase sqLiteDatabase){");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i,int i1){
        sqLiteDatabase.execSQL("DROP TABLE Alarm");
       // sqLiteDatabase.execSQL("DROP TABLE User");
        sqLiteDatabase.execSQL("DROP TABLE Plans");
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
    //予定登録
    public void plans(SQLiteDatabase sqLiteDatabase,String date,String plans,String starttime,String finishtime,String notification,String color,String note){
        String sql = "INSERT INTO Plans(calendar_id,date,plans,starttime,finishtime,notification,color,note) VALUES(null,?,?,?,?,?,?,?)";
        Log.i("aaaa","plans!!!!!!!!!!");
        sqLiteDatabase.execSQL(sql,new String[]{date,plans,starttime,finishtime,notification,color,note});
    }
    public List<Plan> getplan(SQLiteDatabase sqLiteDatabase,String date){
        String sql = "SELECT * FROM Plans WHERE date = ?";
        List<Plan> planList = new ArrayList<>();
        SQLiteCursor cursor = (SQLiteCursor)sqLiteDatabase.rawQuery(sql,new String[]{date},null);
        while (cursor.moveToNext()) {
            Plan plan = new Plan();
            plan.setCalendar_id(cursor.getString(cursor.getColumnIndex("calnedar_id")));
            plan.setDate(cursor.getString(cursor.getColumnIndex("date")));
            plan.setPlans(cursor.getString(cursor.getColumnIndex("plans")));
            plan.setNote(cursor.getString(cursor.getColumnIndex("note")));
            planList.add(plan);
        }
            return planList;
    }
}
