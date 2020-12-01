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
    }
}