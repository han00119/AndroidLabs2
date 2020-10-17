package com.example.androidlabs;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.androidlabs.MyDatabaseOpener.IsSent;


public class ChatRoomActivity extends AppCompatActivity {


    private MyAdapter adapter;
    private ArrayList<Message> myList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        MyDatabaseOpener dbHelper = new MyDatabaseOpener(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String [] columns = {MyDatabaseOpener.ID, MyDatabaseOpener.Message, MyDatabaseOpener.IsSent};
        Cursor result = db.query(false, MyDatabaseOpener.DATABASE_NAME, columns, null, null, null, null, null, null);
        int ColIndex = result.getColumnIndex(MyDatabaseOpener.IsSent);
        int MessageColIndex = result.getColumnIndex(MyDatabaseOpener.Message);
        int IDColIndex = result.getColumnIndex(MyDatabaseOpener.ID);


        ListView myListView = findViewById(R.id.LV);
        EditText etText = findViewById(R.id.inputET);
        Button addButton1 = findViewById(R.id.sendBtn);
        addButton1.setOnClickListener( click -> {
            listInput (1,etText.getText().toString());
            cv.put(dbHelper.Message, MyDatabaseOpener.Message);
            cv.put(IsSent, MyDatabaseOpener.IsSent);
            long id = db.insert(MyDatabaseOpener.DATABASE_NAME, null, cv);
            adapter.notifyDataSetChanged();
            etText.setText("");
        });
        Button addButton2 = findViewById(R.id.receiveBtn);
        addButton2.setOnClickListener( click -> {
            listInput (2,etText.getText().toString());
            cv.put(dbHelper.Message, MyDatabaseOpener.Message);
            cv.put(IsSent, MyDatabaseOpener.IsSent);
            long id = db.insert(MyDatabaseOpener.DATABASE_NAME, null, cv);
            adapter.notifyDataSetChanged();
            etText.setText("");
        });
        adapter = new MyAdapter();
        myListView.setAdapter(adapter);

        while(result.moveToNext()) {
            String Message = result.getString(MessageColIndex);
            Boolean Send = Boolean.valueOf(result.getString(ColIndex));
            long ID = result.getLong(IDColIndex);

            myList.add(new Message(Message, Send, ID));

            adapter.notifyDataSetChanged();
            myListView.setAdapter(adapter);
            //printCursor(result);
        }


           myListView.setOnItemLongClickListener( ( p, b, pos, id) -> {
               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
               alertDialogBuilder.setTitle("Do you want to delete this?");
               alertDialogBuilder.setMessage("The selected row is: " + (pos+1)+ " " +
                                               "The database id is:" + id);
               alertDialogBuilder.setPositiveButton("Yes", (click, arg) -> {
                   //listDelete(p.getItemAtPosition(pos).toString());
//                   MyList.remove(adapter.getItem(pos));
//                   adapter.notifyDataSetChanged();
                   Message yyyy=myList.get(pos);
                   myList.remove(yyyy);
                   adapter.notifyDataSetChanged();
                   //etText.setText("");
                   myListView.setAdapter(new MyAdapter());
               });
               alertDialogBuilder.setNegativeButton("No", (click, arg) -> { });

               db.delete(dbHelper.DATABASE_NAME, "_id=?", new String[]{ Long.toString(id) });

               alertDialogBuilder.create().show();
                return true;
            });
        }


    private void listInput(int messagetype, String message) {
        Message message1 = new Message(messagetype, message);
        myList.add(message1);
        adapter.notifyDataSetChanged();

    }
    private void listDelete(String message) {
        Message message1 = new Message(message);
        myList.remove(message1);
        adapter.notifyDataSetChanged();

    }
    public void printCursor( Cursor c, int version){
        c.moveToFirst();
        Log.i("ChatRoomActivity", "The version number is " + db.getVersion());
        Log.i("ChatRoomActivity", "The number of items in the cursor is " + c.getColumnCount());
        Log.i("ChatRoomActivity", "The name of the columns are " + Arrays.toString(c.getColumnName()));
        Log.i("ChatRoomActivity", "the number of rows is " + c.getCount());
        int IsSentColIndex = c.getColumnIndex(MyDatabaseOpener.IsSent);
        int MessageColIndex = c.getColumnIndex(MyDatabaseOpener.Message);
        int IDColIndex = c.getColumnIndex(MyDatabaseOpener.ID);

        while (c.moveToNext()){
            String Message = c.getString(MessageColIndex);
            Long ID = c.getLong(IDColIndex);
            Boolean IsSent = Boolean.valueOf(c.getString(IsSentColIndex));
            Log.i("ChatRoomActivity", "the column ID is " + ID ", The message is " + Message + ", It is " + IsSent);


        }
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
            return i;
        }

        @Override
        public View getView(int i, View old, ViewGroup parent) {


            Message mess1=getItem(i);
            LayoutInflater inflater = getLayoutInflater();
            View newView = old;
            if(getItem(i).getMessageType()==2) {
                if (newView == null) {
                    newView = inflater.inflate(R.layout.left_chat, parent, false);
                    ImageView imv1 = newView.findViewById(R.id.img_left);
                    TextView tView = newView.findViewById(R.id.tv_left);
                    tView.setText(getItem(i).getMessage().toString());
                }
            }
            if(getItem(i).getMessageType()==1) {
                if (newView == null) {
                    newView = inflater.inflate(R.layout.right_chat, parent, false);

                    TextView tView = newView.findViewById(R.id.tv_right);
                    tView.setText(getItem(i).getMessage().toString());
                    ImageView imv1 = newView.findViewById(R.id.img_right);
                }
            }




            return newView;
       }

    }

}