package com.example.androidlabs;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static com.example.androidlabs.MessageListAdapter.ITEM_VIEW_TYPE_RECEIVE;
import static com.example.androidlabs.MessageListAdapter.ITEM_VIEW_TYPE_SEND;


public class ChatRoomActivity extends AppCompatActivity {

    private MessageListAdapter adapter;
    private ArrayList<Message> dataList = new ArrayList<>();
    private ListView mListView;
    private EditText inputText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        mListView = findViewById(R.id.lv_chat);
        inputText = findViewById(R.id.et_input);

        adapter = new MessageListAdapter(this, dataList);
        mListView.setAdapter(adapter);




        findViewById(R.id.btn_send).setOnClickListener(click -> {
            updateList(ITEM_VIEW_TYPE_SEND, inputText.getText().toString());
        });

        findViewById(R.id.btn_receive).setOnClickListener(click -> {
            updateList(ITEM_VIEW_TYPE_RECEIVE, inputText.getText().toString());
        });
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            findViewById(R.id.lv_chat).setOnLongClickListener(click -> {
            alertDialogBuilder.setMessage("Do you want to delete this?");
            alertDialogBuilder.setPositiveButton("Yes", (click2, arg) -> {

            });
            alertDialogBuilder.setNegativeButton("No", (click2, arg) -> {

            });
            return true;
            });
    }

    private void updateList(int type, String message) {
        Message msg = new Message(type, message);
        dataList.add(msg);
        adapter.notifyDataSetChanged();
        inputText.setText("");
    }


}
