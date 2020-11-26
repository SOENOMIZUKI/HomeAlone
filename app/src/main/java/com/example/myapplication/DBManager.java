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

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS" +
                " Moneybook(month INTEGER PRIMARY KEY AUTOINCREMENT,rent INTEGER,food_expenses INTEGER,water_costs INTEGER,utility_costs INTEGER,communication_costs INTEGER,hobby INTEGER,other INTEGER)");

        //アバターの外部キーを書いてない
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS" +  
                " User(user_id INTEGER PRIMARY KEY AUTOINCREMENT,user_name TEXT,mailaddress TEXT,password TEXT,street_address TEXT,avatar_id INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i,int i1){
        sqLiteDatabase.execSQL("DROP TABLE Alarm");

        sqLiteDatabase.execSQL("DROP TABLE Moneybook");

        sqLiteDatabase.execSQL("DROP TABLE User");

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
    public void onMoneybookRent(SQLiteDatabase sqLiteDatabase, Integer month, Integer price  ) {
        String sql = "UPDATE Moneybook set rent = ?   WHERE month = ?";
        sqLiteDatabase.execSQL(sql, new Object[]{price,month});
    }
    public void onMoneybookFood(SQLiteDatabase sqLiteDatabase, Integer month, Integer price  ) {
        String sql = "UPDATE Moneybook set food_expenses = ?   WHERE month = ?";
        sqLiteDatabase.execSQL(sql, new Object[]{price,month});
    }
    public void onMoneybookWater(SQLiteDatabase sqLiteDatabase, Integer month, Integer price  ) {
        String sql = "UPDATE Moneybook set water_costs = ?   WHERE month = ?";
        sqLiteDatabase.execSQL(sql, new Object[]{price,month});
    }
    public void onMoneybookUtility(SQLiteDatabase sqLiteDatabase, Integer month, Integer price  ) {
        String sql = "UPDATE Moneybook set utility_costs = ?   WHERE month = ?";
        sqLiteDatabase.execSQL(sql, new Object[]{price,month});
    }
    public void onMoneybookCommunication(SQLiteDatabase sqLiteDatabase, Integer month, Integer price  ) {
        String sql = "UPDATE Moneybook set communication_costs = ?   WHERE month = ?";
        sqLiteDatabase.execSQL(sql, new Object[]{price,month});
    }
    public void onMoneybookHobby(SQLiteDatabase sqLiteDatabase, Integer month, Integer price  ) {
        String sql = "UPDATE Moneybook set hobby = ?   WHERE month = ?";
        sqLiteDatabase.execSQL(sql, new Object[]{price,month});
    }
    public void onMoneybookOther(SQLiteDatabase sqLiteDatabase, Integer month, Integer price  ) {
        String sql = "UPDATE Moneybook set other = ?   WHERE month = ?";
        sqLiteDatabase.execSQL(sql, new Object[]{price,month});
    }
    public Kakeibo selectMoneybook(SQLiteDatabase sqLiteDatabase,Integer month) {
        String selectSql = "SELECT * FROM Moneybook WHERE month = ?";
        SQLiteCursor cursor = (SQLiteCursor)sqLiteDatabase.rawQuery(selectSql,new String[]{month.toString()},null);
        Kakeibo kakeibo = new Kakeibo();
        if(cursor.moveToNext()) {
            kakeibo.setMonth(cursor.getInt(cursor.getColumnIndex("month")));
            kakeibo.setRent(cursor.getInt(cursor.getColumnIndex("rent")));
            kakeibo.setFood_expenses(cursor.getInt(cursor.getColumnIndex("food_expenses")));
            kakeibo.setWater_costs(cursor.getInt(cursor.getColumnIndex("water_costs")));
            kakeibo.setUtility_costs(cursor.getInt(cursor.getColumnIndex("utility_costs")));
            kakeibo.setCommunication_costs(cursor.getInt(cursor.getColumnIndex("communication_costs")));
            kakeibo.setHobby(cursor.getInt(cursor.getColumnIndex("hobby")));
            kakeibo.setOther(cursor.getInt(cursor.getColumnIndex("other")));
        }
            return kakeibo;
    }
}
