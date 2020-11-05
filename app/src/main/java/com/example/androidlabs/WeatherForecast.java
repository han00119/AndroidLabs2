package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        ImageView ivWeather = findViewById(R.id.ivWeather);
        TextView CurrentTemperature = findViewById(R.id.CurrentTemperature);
        TextView MinTemperature = findViewById(R.id.MinTemperature);
        TextView MaxTemperature = findViewById(R.id.MaxTemperature);
        TextView UVRating = findViewById(R.id.UVRating);
        ProgressBar pb = findViewById(R.id.pb);

        pb.setVisibility(View.VISIBLE);

        ForecastQuery onlineQuery = new ForecastQuery();
        onlineQuery.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric",
                "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");

    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String UV;
        String min;
        String max;
        String Current;
        Bitmap picture;
        String icon;


        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(strings[0]).openConnection();

                InputStream stream = con.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(stream, "UTF-8");

                while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                    if (xpp.getEventType() == XmlPullParser.START_TAG) {
                        String name = xpp.getName();
                        if (name.equals("temperature")) {
                            Current = xpp.getAttributeValue(null, "value");
                            publishProgress(25);
                            min = xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            max = xpp.getAttributeValue(null, "max");
                            publishProgress(75);
                            Log.i("Weather", Current);
                            Log.i("Weather", min);
                            Log.i("Weather", max);
                        }
                        if(name.equals("weather")){
                            icon = xpp.getAttributeValue(null, "icon");
                        }
                    }
                    xpp.next();
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }


    }
}