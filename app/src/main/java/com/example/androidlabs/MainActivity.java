package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Object obj;
    String onoff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView TV = findViewById(R.id.MyText);
        Button B = findViewById(R.id.MyButton);
        B.setOnClickListener( btn -> {
            Toast.makeText(this,getResources().getString(R.string.ToastText), Toast.LENGTH_LONG).show();
        });
        CheckBox CB = findViewById(R.id.MyCheckBox);
        ImageButton IB = findViewById(R.id.MyImageButton);
        Switch S = findViewById(R.id.MySwitch);
        S.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){onoff="on";}
                if (isChecked==false){onoff="off";}
                Snackbar SB = Snackbar.make(S, getResources().getString(R.string.SnackbarText) +" " + onoff, Snackbar.LENGTH_LONG);
                SB.setAction(getResources().getString(R.string.UndoText), click -> {
                    S.setChecked(!isChecked);
                });
                SB.show();
            }
        });

    }}