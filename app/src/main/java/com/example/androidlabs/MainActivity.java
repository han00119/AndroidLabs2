package com.example.androidlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs=null;
    EditText et;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("EmailList", Context.MODE_PRIVATE);
        TextView tv = findViewById(R.id.Email_Enter);
        et = findViewById(R.id.Email_Enter2);
        TextView tv2 = findViewById(R.id.Password_Enter);
        EditText et2 = findViewById(R.id.Password_Enter2);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("key", et.getText().toString());
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}