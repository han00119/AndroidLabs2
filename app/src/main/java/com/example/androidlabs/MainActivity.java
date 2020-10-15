package com.example.androidlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs;
    EditText et;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("EmailList", Context.MODE_PRIVATE);
        TextView tv = findViewById(R.id.Email_Enter);
        et = findViewById(R.id.Email_Enter2);
        TextView tv2 = findViewById(R.id.Password_Enter);
        EditText et2 = findViewById(R.id.Password_Enter2);
        Button b = (Button)findViewById(R.id.Login_Button);
        b.setOnClickListener( click -> {
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            goToProfile.putExtra("EMAIL",et.getText().toString() );
            startActivity(goToProfile);

        });
        String savedString = prefs.getString("key", "");///every time on create
        et.setText(savedString);
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
    protected void onPause() {////before leaving this Activity,when register
        super.onPause();
        SharedPreferences.Editor editor = prefs.edit();////prefs taking note
        editor.putString("key", et.getText().toString());
        editor.commit();

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