package com.example.androidlabs;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;


public class ChatRoomActivity extends AppCompatActivity {


    MyAdapter adapter;
    ArrayList<Message> myList = new ArrayList<>();
    MyDatabaseOpener dbHelper;
    SQLiteDatabase db;
    ContentValues cv;
    String Message;
    Boolean Send = false;
    int isSentColumnIndex;
    int messageColIndex;
    int idColIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ListView myListView = findViewById(R.id.LV);
        EditText etText = findViewById(R.id.inputET);
        Button addButton1 = findViewById(R.id.sendBtn);
        Button addButton2 = findViewById(R.id.receiveBtn);

        MyDatabaseOpener dbOpener = new MyDatabaseOpener(this);
        db = dbOpener.getWritableDatabase();


        String [] columns = {MyDatabaseOpener.ID, MyDatabaseOpener.MESSAGE, MyDatabaseOpener.ISSENT};
        Cursor results = db.query(false, MyDatabaseOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        isSentColumnIndex = results.getColumnIndex(MyDatabaseOpener.ISSENT);
        messageColIndex = results.getColumnIndex(MyDatabaseOpener.MESSAGE);
        idColIndex = results.getColumnIndex(MyDatabaseOpener.ID);

        while(results.moveToNext())
        {
            String message = results.getString(messageColIndex);
            Boolean isSent = Boolean.valueOf(results.getString(isSentColumnIndex));
            long id = results.getLong(idColIndex);

            myList.add(new Message(id, message, isSent));


        }
        printCursor(results, 1);
        adapter = new MyAdapter();
        myListView.setAdapter(adapter);

        myListView.setOnItemLongClickListener(( p,  b,  pos,  id) -> {
            Message M = myList.get(pos);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?");
            alertDialogBuilder.setMessage("The selected row is: " + (pos+1)+ " " +
                    "The database id is:" + id);
            alertDialogBuilder.setPositiveButton("Yes", (click, arg) -> {
                myList.remove(M);
                db.delete(MyDatabaseOpener.TABLE_NAME, "_id=?", new String[]{ Long.toString(M.getId()) });
                adapter.notifyDataSetChanged();
            });
            alertDialogBuilder.setNegativeButton("No", (click, arg) -> { });

            alertDialogBuilder.create().show();
            return true;
        });



        addButton1.setOnClickListener( click -> {
            String message = etText.getText().toString();
            cv = new ContentValues();
            cv.put(MyDatabaseOpener.MESSAGE, Message);
            cv.put(MyDatabaseOpener.ISSENT, Send.toString());
            long id = db.insert(MyDatabaseOpener.TABLE_NAME, null, cv);
            Message message1 = new Message(id, message, true);
            myList.add(message1);
            adapter.notifyDataSetChanged();
            etText.setText("");
        });
        addButton2.setOnClickListener( click -> {
            String message = etText.getText().toString();
            cv = new ContentValues();
            cv.put(MyDatabaseOpener.MESSAGE, Message);
            cv.put(MyDatabaseOpener.ISSENT, Send.toString());
            long id = db.insert(MyDatabaseOpener.TABLE_NAME, null, cv);
            Message message2 = new Message(id, message, false);
            myList.add(message2);
            adapter.notifyDataSetChanged();
            etText.setText("");
        });

    }

    public void printCursor( Cursor c, int version){
        //int IsSentColIndex = c.getColumnIndex(MyDatabaseOpener.ISSENT);
        //int MessageColIndex = c.getColumnIndex(MyDatabaseOpener.MESSAGE);
        //int IDColIndex = c.getColumnIndex(MyDatabaseOpener.ID);
        c.moveToFirst();
        Log.i("ChatRoomActivity", "The version number is " + db.getVersion());
        Log.i("ChatRoomActivity", "The number of items in the cursor is " + c.getColumnCount());
        Log.i("ChatRoomActivity", "The name of the columns are " + Arrays.toString(c.getColumnNames()));
        Log.i("ChatRoomActivity", "the number of rows is " + c.getCount());

        while (c.moveToNext()){
            String Message = c.getString(messageColIndex);
            long ID = c.getLong(idColIndex);
            boolean IsSent = Boolean.parseBoolean(c.getString(isSentColumnIndex));
            Log.i("ChatRoomActivity", "the column ID is " + ID + ", The message is " + Message + ", It is " + IsSent);


        }
    }

    private class MyAdapter extends BaseAdapter {

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

            return getItem(i).getId();
        }

        @Override
        public View getView(int i, View old, ViewGroup parent) {

            Message mess1 = getItem(i);
            LayoutInflater myInflater = getLayoutInflater();
            View newView = old;
            if (!mess1.getIsSent()) {
                if (newView == null) {
                    newView = myInflater.inflate(R.layout.left_chat, parent, false);
                    ImageView imv1 = newView.findViewById(R.id.img_left);
                    TextView tView = newView.findViewById(R.id.tv_left);
                    tView.setText(getItem(i).getMessage());
                }
            }
            if (mess1.getIsSent()) {
                if (newView == null) {
                    newView = myInflater.inflate(R.layout.right_chat, parent, false);

                    TextView tView = newView.findViewById(R.id.tv_right);
                    ImageView imv1 = newView.findViewById(R.id.img_right);
                    tView.setText(getItem(i).getMessage());

                }
            }
            return newView;
        }
    }

}
