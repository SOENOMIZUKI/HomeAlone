package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class CalendarAddActivity extends HeaderActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
    private SQLiteDatabase sqlDB;
    DBManager dbm;
    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    TimePickerDialog timePickerDialog;
    int Year, Month, Day ;
    int hour, minute;
    String plans;
    String note;
    String setDate;
    String startdate;
    String enddate;
    String startTime;
    String endtime;
    String color;
    String notification;
    boolean Frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_add);

        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        dbm = new DBManager(this);
        sqlDB = dbm.getWritableDatabase();


        TextView startText = (TextView) findViewById(R.id.startText);
        startText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialog = DatePickerDialog.newInstance(CalendarAddActivity.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(Color.parseColor("#0072BA"));

                datePickerDialog.setTitle("Select Date From DatePickerDialog");

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

            }
        });
        TextView startTime = (TextView) findViewById(R.id.startTimeView);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = TimePickerDialog.newInstance(CalendarAddActivity.this, hour, minute, true);

                timePickerDialog.setThemeDark(false);

                timePickerDialog.setAccentColor(Color.parseColor("#0072BA"));

                timePickerDialog.show(getFragmentManager(), "TimePickerDialog");

                Frag = true;
            }
        });

        TextView endText = (TextView) findViewById(R.id.endText);
        endText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialog = DatePickerDialog.newInstance(CalendarAddActivity.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(Color.parseColor("#0072BA"));

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });

        TextView endTime = (TextView) findViewById(R.id.endTimeView);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = TimePickerDialog.newInstance(CalendarAddActivity.this, hour, minute, true);

                timePickerDialog.setThemeDark(false);

                timePickerDialog.setAccentColor(Color.parseColor("#0072BA"));

                timePickerDialog.show(getFragmentManager(), "TimePickerDialog");

                Frag = false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        setDate = intent.getStringExtra("Date");
        TextView text = (TextView)findViewById(R.id.dateText);
        text.setText(setDate + "の予定");

        String Yotei = "開始終了時間";
        TextView text1 = (TextView)findViewById(R.id.Text1);
                text1.setText(Yotei);

        ImageButton back = (ImageButton) findViewById(R.id.backBtn2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(CalendarAddActivity.this, CalendarComfirmActivity.class);
                startActivity(intent3); }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        int MonthPuls = Month + 1;
        startdate =Year + "年" + MonthPuls + "月" + Day + "日";
        TextView start = (TextView)findViewById(R.id.startText);
        start.setText(startdate);

        int monthEnd = monthOfYear + 1;
        enddate = year + "年" + monthEnd + "月" + dayOfMonth + "日";
        TextView end = (TextView)findViewById(R.id.endText);
        end.setText(enddate);

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        if(Frag == true){
            startTime = hourOfDay + "時" + minute + "分";
            TextView start = (TextView)findViewById(R.id.startTimeView);
            start.setText(startTime);
        }else{
            endtime = hourOfDay + "時" + minute + "分";
            TextView end = (TextView)findViewById(R.id.endTimeView);
            end.setText(endtime);
        }
    }
    public void onRegist(View view){
        Toast.makeText(getApplicationContext(), "登録しました。", Toast.LENGTH_SHORT).show();
        EditText editText1 = (EditText)findViewById(R.id.plans);
        EditText editText2 = (EditText)findViewById(R.id.note);
        String plans = editText1.getText().toString();
        String note = editText2.getText().toString();
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        int checkId = radioGroup.getCheckedRadioButtonId();
        if( checkId == R.id.aka ){
            color = "aka";
        }

        CompoundButton toggle = (CompoundButton) findViewById(R.id.notification);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   notification = "0";
                } else {
                    notification  = "1";
                }
            }
        });
        String starttime = startTime + startdate;
        String finishtime = endtime + enddate;
        String date = setDate;

        dbm.plans(sqlDB,date,plans,starttime,finishtime,notification,color,note);
        Intent intent = new Intent(CalendarAddActivity.this, CalendarComfirmActivity.class);
        startActivity(intent);

    }
}