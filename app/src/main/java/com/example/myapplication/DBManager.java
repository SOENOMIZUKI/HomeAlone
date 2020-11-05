package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.IntegerRes;

public class DBManager extends SQLiteOpenHelper {

    String inputdata;
    int inputswitch;
    int inputrepeat;

    public DBManager(Context context){
        super(context,"Alarm.sqlite3",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS" +
                " Alarm(alarm_id INTEGER PRIMARY KEY AUTOINCREMENT,data TEXT,switch INTEGER,repeat INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i,int i1){
        sqLiteDatabase.execSQL("DROP TABLE Alarm");
        onCreate(sqLiteDatabase);
    }
    public void insertAlarm(SQLiteDatabase sqLiteDatabase, String data, Integer switch1){
        String sql = "INSERT INTO Alarm(data,switch1,repeat) VALUES(?,?,?)";
        sqLiteDatabase.execSQL(sql,new String[]{inputdata});
        sqLiteDatabase.execSQL(sql,new Integer[]{inputswitch});
    }
    public void deleteAlarm(SQLiteDatabase sqLiteDatabase, String data){
        String sql = "DELETE from Alarm where data = ?";
        sqLiteDatabase.execSQL(sql,new String[]{inputdata});
    }
    public void changeAlarm(SQLiteDatabase sqLiteDatabase, String data, Integer switch1,Integer repeat){
        String sql = "UPDATE Alarm set data=?,switch=?,repeat=?";
        sqLiteDatabase.execSQL(sql,new String[]{inputdata});
        sqLiteDatabase.execSQL(sql,new Integer[]{inputswitch});
        sqLiteDatabase.execSQL(sql,new Integer[]{inputrepeat});
    }
}
