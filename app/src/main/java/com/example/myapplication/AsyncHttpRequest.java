package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AsyncHttpRequest extends AsyncTask<URL, Void, ArrayList<String>> {
    private WeatherActivity WeatherActivity;
    private CaldroidSampleCustomFragment caldroidFragment;
    public AsyncHttpRequest(WeatherActivity activity) {
        this.WeatherActivity = activity;
    }
<<<<<<< Updated upstream

=======
    final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
>>>>>>> Stashed changes

    @Override
    protected ArrayList<String> doInBackground(URL... urls) {
        final URL url = urls[0];

        HttpURLConnection con = null;
        try {
            //指定のURLにGETで接続する設定
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            // リダイレクトを自動で許可しない設定
            con.setInstanceFollowRedirects(false);
            //WebAPIに接続する
            con.connect();
            final int statusCode = con.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                System.err.println("正常に接続できていません。statusCode:" + statusCode);
                return null;
            }
            final InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            if (null == encoding) {
                encoding = "UTF-8";
            }
            final InputStreamReader inReader = new InputStreamReader(in, encoding);
            final BufferedReader bufReader = new BufferedReader(inReader);
            StringBuilder response = new StringBuilder();
            String line = null;
            while ((line = bufReader.readLine()) != null) {
                response.append(line);
            }
            bufReader.close();
            inReader.close();
            in.close();
            JSONObject jsonObject = new JSONObject(response.toString());
            ArrayList<String> weatherList = new ArrayList<>();
            weatherList.add(jsonObject.getJSONArray("list").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("icon"));
            weatherList.add(jsonObject.getJSONArray("list").getJSONObject(12).getJSONArray("weather").getJSONObject(0).getString("icon"));
            weatherList.add(jsonObject.getJSONArray("list").getJSONObject(20).getJSONArray("weather").getJSONObject(0).getString("icon"));
            weatherList.add(jsonObject.getJSONArray("list").getJSONObject(28).getJSONArray("weather").getJSONObject(0).getString("icon"));
            weatherList.add(jsonObject.getJSONArray("list").getJSONObject(36).getJSONArray("weather").getJSONObject(0).getString("icon"));
            return weatherList;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<String> weatherList) {
        super.onPostExecute(weatherList);
        Intent intent = new Intent(WeatherActivity,CalendarActivity.class);
        intent.putExtra("param1",weatherList);
        WeatherActivity.startActivity(intent);
<<<<<<< Updated upstream
=======
        /*
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

        caldroidFragment = new CaldroidSampleCustomFragment();

        caldroidFragment.setArguments(args);

        FragmentTransaction t = WeatherActivity.getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        final CaldroidListener listener = new CaldroidListener() {

            //日付をタップした時
            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(WeatherActivity, formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }
            //月を変更したとき
            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(WeatherActivity, text,
                        Toast.LENGTH_SHORT).show();
            }
            //日付を長くタップした時
            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(WeatherActivity,
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }
            //画面を左にスライドしたとき
            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    Toast.makeText(WeatherActivity,
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        };
        caldroidFragment.setCaldroidListener(listener);
*/

>>>>>>> Stashed changes
    }
}
