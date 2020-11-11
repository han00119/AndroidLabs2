package com.example.androidlabs;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
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
import androidx.fragment.app.Fragment;

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
    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ListView myListView = findViewById(R.id.LV);
        EditText etText = findViewById(R.id.inputET);
        Button addButton1 = findViewById(R.id.sendBtn);
        Button addButton2 = findViewById(R.id.receiveBtn);
        isTablet = findViewById(R.id.fragDetail) != null;

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
                myListView.setAdapter(adapter);
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragDetail);
                getSupportFragmentManager().beginTransaction().remove(f).commit();

            });
            alertDialogBuilder.setNegativeButton("No", (click, arg) -> { });

            alertDialogBuilder.create().show();
            return true;
        });

        myListView.setOnItemClickListener((p, b, pos, id) ->{
            if (isTablet) {
                Bundle dataToPass = new Bundle();
                dataToPass.putString("Message", myList.get(pos).getMessage());
                dataToPass.putLong("id", myListView.getItemIdAtPosition(pos));
                dataToPass.putBoolean("isSend", myList.get(pos).getIsSent());

                DetailsFragment dFragment = new DetailsFragment();
                dFragment.setArguments(dataToPass);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragDetail, dFragment)
                        .commit();
            } else {
                Intent goToEmpty = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                goToEmpty.putExtra("Message", myList.get(pos).getMessage());
                goToEmpty.putExtra("id", myListView.getItemIdAtPosition(pos));
                goToEmpty.putExtra("isSend", myList.get(pos).getIsSent());
                startActivity(goToEmpty);
            }
        });



        addButton1.setOnClickListener( click -> {
            String message = etText.getText().toString();
            cv = new ContentValues();
            cv.put(MyDatabaseOpener.MESSAGE, message);
            cv.put(MyDatabaseOpener.ISSENT, "true");
            long id = db.insert(MyDatabaseOpener.TABLE_NAME, null, cv);
            Message message1 = new Message(id, message, true);
            myList.add(message1);
            adapter.notifyDataSetChanged();
            myListView.setAdapter(adapter);
            etText.setText("");
        });
        addButton2.setOnClickListener( click -> {
            String message = etText.getText().toString();
            cv = new ContentValues();
            cv.put(MyDatabaseOpener.MESSAGE, message);
            cv.put(MyDatabaseOpener.ISSENT, "false");
            long id = db.insert(MyDatabaseOpener.TABLE_NAME, null, cv);
            Message message2 = new Message(id, message, false);
            myList.add(message2);
            adapter.notifyDataSetChanged();
            myListView.setAdapter(adapter);
            etText.setText("");
        });

    }


    public void printCursor( Cursor c, int version) {
        //int IsSentColIndex = c.getColumnIndex(MyDatabaseOpener.ISSENT);
        //int MessageColIndex = c.getColumnIndex(MyDatabaseOpener.MESSAGE);
        //int IDColIndex = c.getColumnIndex(MyDatabaseOpener.ID);
        c.moveToFirst();
        Log.i("ChatRoomActivity", "The version number is " + db.getVersion());
        Log.i("ChatRoomActivity", "The number of columns in the cursor is " + c.getColumnCount());
        Log.i("ChatRoomActivity", "The name of the columns are " + Arrays.toString(c.getColumnNames()));
        Log.i("ChatRoomActivity", "the number of rows is " + c.getCount());

        if (c.getCount() != 0) {
            do {
                String SOR = null;
                String message = c.getString(messageColIndex);
                long ID = c.getLong(idColIndex);
                boolean IsSent = Boolean.parseBoolean(c.getString(isSentColumnIndex));
                if (IsSent == true) {
                    SOR = "a send message";
                }
                if (IsSent == false) {
                    SOR = "a receive message";
                }
                Log.i("ChatRoomActivity", "The Database ID is " + ID + ", The message is " + message + ", It is " + SOR);

            }
            while (c.moveToNext());
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