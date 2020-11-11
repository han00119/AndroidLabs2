package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        Intent i = getIntent();
        String Message = i.getStringExtra("Message");
        long id = i.getLongExtra("id", 0);
        boolean isSend = i.getBooleanExtra("isSend", false);

        Bundle dataToPass = new Bundle();
        dataToPass.putString("Message", Message);
        dataToPass.putLong("id", id);
        dataToPass.putBoolean("isSend", isSend);

        DetailsFragment dFragment = new DetailsFragment();
        dFragment.setArguments(dataToPass);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragDetail, dFragment)
                .commit();

    }
}