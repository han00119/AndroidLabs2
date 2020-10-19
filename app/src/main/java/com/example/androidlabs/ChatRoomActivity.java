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

import static java.sql.Types.INTEGER;


public class ChatRoomActivity extends AppCompatActivity {


    private MyAdapter adapter;
    private ArrayList<Message> myList = new ArrayList<>();
    MyDatabaseOpener dbHelper;
    SQLiteDatabase db;
    ContentValues cv;
    String Message;
    Boolean Send;
    long ID;
    Cursor result;
    ListView myListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        cv = new ContentValues();
        myListView = findViewById(R.id.LV);
        EditText etText = findViewById(R.id.inputET);
        Button addButton1 = findViewById(R.id.sendBtn);
        Button addButton2 = findViewById(R.id.receiveBtn);
        getFromDB();


        addButton1.setOnClickListener( click -> {
//            Send = true;
            String message = etText.getText().toString();

            cv.put(MyDatabaseOpener.MESSAGE, Message);
            cv.put(MyDatabaseOpener.ISSENT, Send.toString());

            long id = db.insert(MyDatabaseOpener.DATABASE_NAME, null, cv);

            listInput (id, message, Send);

            etText.setText("");
        });
        addButton2.setOnClickListener( click -> {
//            Send = false;
            String message = etText.getText().toString();
            cv.put(MyDatabaseOpener.MESSAGE, Message);
            cv.put(MyDatabaseOpener.ISSENT, Send.toString());
            long id = db.insert(MyDatabaseOpener.DATABASE_NAME, null, cv);
            listInput (id, message, Send);

            etText.setText("");
        });
        alert (myListView);

        adapter = new MyAdapter();
        myListView.setAdapter(adapter);

        //adapter.notifyDataSetChanged();
        //myListView.setAdapter(adapter);

        printCursor(result, 1);
        }
    private void alert (ListView myListView){
        myListView.setOnItemLongClickListener( ( p, b, pos, id) -> {
            Message M=myList.get(pos);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?");
            alertDialogBuilder.setMessage("The selected row is: " + (pos+1)+ " " +
                    "The database id is:" + id);
            alertDialogBuilder.setPositiveButton("Yes", (click, arg) -> {
                //listDelete(p.getItemAtPosition(pos).toString());
//                   MyList.remove(adapter.getItem(pos));
//                   adapter.notifyDataSetChanged();
                myList.remove(M);
                DBdelete(M);
                adapter.notifyDataSetChanged();
                //myListView.setAdapter(new MyAdapter());
            });
            alertDialogBuilder.setNegativeButton("No", (click, arg) -> { });



            alertDialogBuilder.create().show();
            return true;
        });
    }

      private void getFromDB(){
          dbHelper = new MyDatabaseOpener(this);
          db = dbHelper.getWritableDatabase();


          String [] columns = {MyDatabaseOpener.ID, MyDatabaseOpener.MESSAGE, MyDatabaseOpener.ISSENT};

          result = db.query(false, MyDatabaseOpener.DATABASE_NAME, columns, null, null, null, null, null, null);

          int ColIndex = result.getColumnIndex(MyDatabaseOpener.ISSENT);
          int MessageColIndex = result.getColumnIndex(MyDatabaseOpener.MESSAGE);
          int IDColIndex = result.getColumnIndex(MyDatabaseOpener.ID);
          // result.moveToFirst();
          while(result.moveToNext())
          {
              Boolean send = Boolean.valueOf(result.getString(IDColIndex));
              String message = result.getString(MessageColIndex);
              long id = result.getLong(ColIndex);

              myList.add(new Message(id, message, send));
              // result.moveToNext();
          }
      }

    private void listInput(long id, String message, boolean Send) {
        Message message1 = new Message(id, message, Send);
        myList.add(message1);
        adapter.notifyDataSetChanged();

    }
    private void listDelete(String message) {
        Message message1 = new Message(message);
        myList.remove(message1);
        adapter.notifyDataSetChanged();

    }

    private void DBdelete(Message m){
        db.delete(MyDatabaseOpener.DATABASE_NAME, "_id=?", new String[]{ Long.toString(m.getId()) });
    }

    public void printCursor( Cursor c, int version){
        int IsSentColIndex = c.getColumnIndex(MyDatabaseOpener.ISSENT);
        int MessageColIndex = c.getColumnIndex(MyDatabaseOpener.MESSAGE);
        int IDColIndex = c.getColumnIndex(MyDatabaseOpener.ID);
        c.moveToFirst();
        Log.i("ChatRoomActivity", "The version number is " + db.getVersion());
        Log.i("ChatRoomActivity", "The number of items in the cursor is " + c.getColumnCount());
        Log.i("ChatRoomActivity", "The name of the columns are " + Arrays.toString(c.getColumnNames()));
        Log.i("ChatRoomActivity", "the number of rows is " + c.getCount());

        while (c.moveToNext()){
            String Message = c.getString(MessageColIndex);
            Long ID = c.getLong(IDColIndex);
            Boolean IsSent = Boolean.valueOf(c.getString(IsSentColIndex));
            Log.i("ChatRoomActivity", "the column ID is " + ID + ", The message is " + MyDatabaseOpener.MESSAGE + ", It is " + MyDatabaseOpener.ISSENT);


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

            return getItem(i).getId();
        }

        @Override
        public View getView(int i, View old, ViewGroup parent) {

            Message mess1 = getItem(i);
            myInflater = getLayoutInflater();
            View newView = old;
            if (!getItem(i).getIsSent ()) {
                if (newView == null) {
                    newView = myInflater.inflate(R.layout.left_chat, parent, false);
                    ImageView imv1 = newView.findViewById(R.id.img_left);
                    TextView tView = newView.findViewById(R.id.tv_left);
                    tView.setText(getItem(i).getMessage());
                }
            }
            if (getItem(i).getIsSent ()) {
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