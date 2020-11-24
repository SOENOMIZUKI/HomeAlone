package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        Log.e("11111111111111111", "おんれしーぶ");

        //アラーム音を鳴らす処理
        context.startService(new Intent(context, SoundService.class));

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("アラームを停止");
        alert.setPositiveButton("アラーム停止", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //アラーム停止が押された時の処理
                AlarmManager Alarmmanager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(context,AlarmReceiver.class);
                PendingIntent pendingintent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                if (Alarmmanager!= null) {

                    //アラームを止める処理
                    Alarmmanager.cancel(pendingintent);
                    pendingintent.cancel();

                    //アラーム音を止める処理
                    context.stopService(new Intent(context, SoundService.class));
                }
                Toast.makeText(context, "アラームを停止しました", Toast.LENGTH_LONG).show();
            }
        });
        alert.show();
    }
}