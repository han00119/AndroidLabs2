package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView TV = findViewById(R.id.MyText);
        Button B = findViewById(R.id.MyButton);
        CheckBox CB = findViewById(R.id.MyCheckBox);
        ImageButton IB = findViewById(R.id.MyImageButton);
        Switch S = findViewById(R.id.MySwitch);
        EditText ET = findViewById(R.id.MyEditText);
    }
}