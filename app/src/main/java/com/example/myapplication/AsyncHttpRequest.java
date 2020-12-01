package com.example.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncHttpRequest extends AsyncTask<URL, Void, String> {
    private Activity WeatherActivity;

    public AsyncHttpRequest(Activity activity) {
        this.WeatherActivity = activity;
    }
<<<<<<< HEAD

=======
>>>>>>> main

    @Override
    protected String doInBackground(URL... urls) {
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
            if(null == encoding){
                encoding = "UTF-8";
            }
                 final InputStreamReader inReader = new InputStreamReader(in, encoding);
            final BufferedReader bufReader = new BufferedReader(inReader);
            StringBuilder response = new StringBuilder();
            String line = null;
            while((line = bufReader.readLine()) != null) {
                response.append(line);
            }
            bufReader.close();
            inReader.close();
            in.close();
            JSONObject jsonObject = new JSONObject(response.toString());
            String weather = jsonObject.getJSONArray("list").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("icon");
            return weather;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
<<<<<<< HEAD
    protected void onPostExecute(ArrayList<String> weatherList) {
        super.onPostExecute(weatherList);
        Intent intent = new Intent(WeatherActivity,CalendarActivity.class);
        intent.putExtra("param1",weatherList);
        WeatherActivity.startActivity(intent);



=======
    protected void onPostExecute(String weather) {
        TextView tv = WeatherActivity.findViewById(R.id.tv1);
        tv.setText(weather);
>>>>>>> main
    }
}