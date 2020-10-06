package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ListView lv = (ListView) findViewById(R.id.LV);
        Button b = findViewById(R.id.B);
        EditText et = findViewById(R.id.ET);
        Button b2 = findViewById(R.id.B2);
        b.setOnClickListener(click -> );
        int getCount(){

        }
        Object getItem(int position){

        }
        View getView( ){

        }
        long getItemId(int i){

        }

    }
}