package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAddress extends AsyncTask<URL, Void, latlag> {
    private WeatherActivity weatherActivity;
    latlag address;

    public GetAddress(WeatherActivity activity) {
        this.weatherActivity = activity;
    }


    @Override
    protected latlag doInBackground(URL... urls) {
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
            Log.i("tag", "ステータスコードは"+statusCode);
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
            Log.i("tag", "JosnObjectは"+jsonObject.toString());
            address = new latlag();

            address.lat = String.valueOf(jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("bounds").getJSONObject("southwest").getDouble("lat"));
            address.lag = String.valueOf(jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("bounds").getJSONObject("southwest").getDouble("lng"));
            Log.i("tag","lat="+ address.lat);
            Log.i("tag","lag=" + address.lag);
            return address;
        } catch (
                IOException e) {
            e.printStackTrace();
            return null;
        } catch (
                JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(latlag address) {
        super.onPostExecute(address);
        String lat = address.lat;
        String lon = address.lag;

        try {
            new AsyncHttpRequest(weatherActivity)
                    .execute(
                            new URL("http://api.openweathermap.org/data/2.5/forecast?lat="+lat+"&lon="+lon+"&APPID=f4ccf014306d1a86f61016ee7bb4a0d2")
                    );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
