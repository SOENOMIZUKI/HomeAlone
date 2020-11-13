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

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS" +
                " Alarm(alarm_id INTEGER PRIMARY KEY AUTOINCREMENT,data TEXT,repeat INTEGER,switch INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i,int i1){
        sqLiteDatabase.execSQL("DROP TABLE Alarm");
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
    public void changeAlarm(SQLiteDatabase sqLiteDatabase,String data1, String data2, Integer repeat, Integer switch1){
        String sql = "UPDATE Alarm set data=?,repeat=?,switch=? WHERE data = ?";
        sqLiteDatabase.execSQL(sql,new Object[]{data2,repeat,switch1,data1});
    }
    public void onoffAlarm(SQLiteDatabase sqLiteDatabase, String data,Integer switch1){
        String sql = "UPDATE Alarm set switch=? WHERE data = ?";
        sqLiteDatabase.execSQL(sql,new Object[]{switch1,data});
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
}
