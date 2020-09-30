package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    TextView tv = findViewById(R.id.Email_Enter);
    EditText et = findViewById(R.id.Email_Enter2);
    TextView tv2 = findViewById(R.id.Password_Enter);
    EditText et2 = findViewById(R.id.Password_Enter2);
}