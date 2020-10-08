package com.example.androidlabs;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;



public class ChatRoomActivity extends AppCompatActivity {


    private MyAdapter adapter;
    private ArrayList<Message> myList = new ArrayList<>();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView myListView = findViewById(R.id.LV);
        EditText etText = findViewById(R.id.inputET);
        Button addButton1 = findViewById(R.id.sendBtn);
        addButton1.setOnClickListener( click -> {
            listInput (1,etText.getText().toString());
            adapter.notifyDataSetChanged();
        });
        Button addButton2 = findViewById(R.id.receiveBtn);
        addButton2.setOnClickListener( click -> {
            listInput (2,etText.getText().toString());
            adapter.notifyDataSetChanged();
        });
        adapter = new MyAdapter();
        myListView.setAdapter(adapter);




           myListView.setOnLongClickListener( (p, b, pos, id) -> {
               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

               alertDialogBuilder.setTitle("Do you want to delete this?");
               alertDialogBuilder.setMessage("The selected row is: " + pos +
                                               "The database id is:" + id);
               alertDialogBuilder.setPositiveButton("Yes", (click, arg) -> {
                   myList.remove(pos);
                   adapter.notifyDataSetChanged();
               });
               alertDialogBuilder.setNegativeButton("No", (click, arg) -> { });

               alertDialogBuilder.create().show();
                return true;
            });
    }

    private void listInput(int messagetype, String message) {
        Message message1 = new Message(messagetype, message);
        myList.add(message1);
        adapter.notifyDataSetChanged();

    }
    private class MyAdapter extends BaseAdapter {




        private LayoutInflater myInflater;



        @Override
        public int getCount() {
            return myList == null ? 0 : myList.size();
        }

        @Override
        public Message getItem(int i) {
            return myList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return (long)i;
        }

        @Override
        public View getView(int i, View old, ViewGroup parent) {


            Message mess1=getItem(i);
            LayoutInflater inflater = getLayoutInflater();
            View newView=old;
            if(getItem(i).getMessageType()==1) {
                if (newView == null) {
                    newView = inflater.inflate(R.layout.left_chat, parent, false);
                    ImageView imv1 = newView.findViewById(R.id.img_left);
                    TextView tView = newView.findViewById(R.id.tv_left);
                    tView.setText(getItem(i).getMessage().toString());
                }
            }
            if(getItem(i).getMessageType()==2) {
                if (newView == null) {
                    newView = inflater.inflate(R.layout.right_chat, parent, false);

                    TextView tView = newView.findViewById(R.id.tv_right);
                    tView.setText(getItem(i).getMessage().toString());
                    ImageView imv1 = newView.findViewById(R.id.img_right);
                }
            }





    }


}
