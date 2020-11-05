package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    ProgressBar pb;
    TextView UVRating;
    TextView MaxTemperature;
    TextView MinTemperature;
    TextView CurrentTemperature;
    ImageView ivWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        ivWeather = findViewById(R.id.ivWeather);
        CurrentTemperature = findViewById(R.id.CurrentTemperature);
        MinTemperature = findViewById(R.id.MinTemperature);
        MaxTemperature = findViewById(R.id.MaxTemperature);
        UVRating = findViewById(R.id.UVRating);
        pb = findViewById(R.id.pb);

        pb.setVisibility(View.VISIBLE);

        ForecastQuery onlineQuery = new ForecastQuery();
        onlineQuery.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric", "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");

    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String UV;
        String min;
        String max;
        String Current;
        Bitmap image;
        String icon;


        @Override
        protected String doInBackground(String... strings) {
            try {

                HttpURLConnection con1 = (HttpURLConnection) new URL(strings[0]).openConnection();

                InputStream stream = con1.getInputStream();

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




                URL url2 = new URL(strings[1]);
                    HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con2.getInputStream(), "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    String result = sb.toString();

                    JSONObject jObject = new JSONObject(result);
                        float value = (float)jObject.getDouble("value");
                        UV = String.valueOf(value);
                     //   publishProgress(100);


                if (fileExistance(icon + ".png")) {
                    image = getPicture(icon + ".png");
                    Log.i("image", "it is found locally");
                    publishProgress(100);
                } else {
                    Log.i("image","the image has to be downloaded");
                    //URL url0 = new URL(urlString);
                    HttpURLConnection con0 = (HttpURLConnection) new URL("http://openweathermap.org/img/w/" + icon + ".png").openConnection();
                    //connection = (HttpURLConnection) url.openConnection();
                    con0.connect();
                    int responseCode = con0.getResponseCode();
                    if (responseCode == 200) {
                        image = BitmapFactory.decodeStream(con0.getInputStream());
                        FileOutputStream outputStream = openFileOutput(icon + ".png", Context.MODE_PRIVATE);
                        image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                        outputStream.flush();
                        outputStream.close();
                        image = getPicture(icon + ".png");
                        publishProgress(100);
                    }
                }


            } catch (XmlPullParserException | IOException | JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }
        public Bitmap getPicture (String imagefile){
            FileInputStream fis = null;
            try {
                fis = openFileInput(imagefile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bm = BitmapFactory.decodeStream(fis);
            return bm;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            CurrentTemperature.setText(getString(R.string.CurTemp) + Current + "\u00b0");
            MinTemperature.setText(getString(R.string.MinTemp) + min + "\u00b0");
            MaxTemperature.setText(getString(R.string.MaxTemp)+ max + "\u00b0");
            UVRating.setText(getString(R.string.UVrating) + UV);
            ivWeather.setImageBitmap(image);
            pb.setVisibility(View.INVISIBLE);

        }


    }
}