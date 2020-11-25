package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

class CustomAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int resourcedId;
    private List<Alarm> items;
    private AlarmActivity alarmActivity;

    static class ViewHolder {
        Switch switch1;
        TextView textView1;
        TextView textView2;
    }

    CustomAdapter(Context context, int resourcedId, List<Alarm> items,AlarmActivity alarmActivity) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resourcedId = resourcedId;
        this.items = items;
        this.alarmActivity = alarmActivity;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resourcedId, parent, false);

            holder = new ViewHolder();
            holder.textView1 = convertView.findViewById(R.id.txtArea1);
            holder.switch1 = convertView.findViewById(R.id.repeat);
            holder.textView2 = convertView.findViewById(R.id.txtArea2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Alarm alarm = items.get(position);
        holder.textView1.setText(alarm.getTime());
        if (alarm.getSwitch1()==1){
            holder.switch1.toggle();
        }
        if (alarm.getRepeat()==1){
            holder.textView2.setText("ON");
        }else {
            holder.textView2.setText("OFF");
        }

        holder.switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.repeat);
            }
        });
        holder.textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.txtArea1);
            }
        });

        holder.textView1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final String date = ((TextView)view).getText().toString();

                AlertDialog.Builder alert = new AlertDialog.Builder(alarmActivity);
                alert.setMessage("本当に削除しますか？");
                alert.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Noボタンが押された時の処理
                        Toast.makeText(alarmActivity, "削除をキャンセルしました", Toast.LENGTH_LONG).show();
                    }
                });
                alert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Yesボタンが押された時の処理
                        DBManager dbm = new DBManager(alarmActivity);
                        SQLiteDatabase sqlDB = dbm.getWritableDatabase();
                        dbm.deleteAlarm(sqlDB, date);
                        Toast.makeText(alarmActivity, "削除しました", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(alarmActivity.getApplication(),AlarmActivity.class);
                        alarmActivity.startActivity(intent);
                    }
                });
                alert.show();
                return true;
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
