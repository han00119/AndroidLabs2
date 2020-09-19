package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Object obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView TV = findViewById(R.id.MyText);
        Button B = findViewById(R.id.MyButton);
        B.setOnClickListener( btn -> {
            Toast.makeText(this,"Here is more information", Toast.LENGTH_LONG).show();
        });
        CheckBox CB = findViewById(R.id.MyCheckBox);
        ImageButton IB = findViewById(R.id.MyImageButton);
        Switch S = findViewById(R.id.MySwitch);
        EditText ET = findViewById(R.id.MyEditText);

        //Toast.makeText(this,"Here is more information", Toast.LENGTH_LONG).show();
        //Snackbar.make(this, "this String is", Snackbar.LENGTH_LONG).show();
        //Snackbar.setAction("UNDO", click -> cb.setChecked(!b));
    }
}