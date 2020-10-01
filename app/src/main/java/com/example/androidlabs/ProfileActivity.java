package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        EditText et1 = findViewById(R.id.et1);
        TextView tv3 = findViewById(R.id.tv3);
        EditText et2 = findViewById(R.id.et2);
        TextView tv4 = findViewById(R.id.tv4);
        ImageButton im = findViewById(R.id.ib);
        TextView tv5 = findViewById(R.id.tv5);
    }
}