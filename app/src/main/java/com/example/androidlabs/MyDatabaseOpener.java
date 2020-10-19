package com.example.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseOpener extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Lab5Database";
    public static final String TABLE_NAME = "Lab5Table";
    public static final String ID = "_id";
    public static final String MESSAGE = "Message";
    public static final String ISSENT = "IsSent";
    public static final int VERSION_NUMBER = 2;

    public MyDatabaseOpener(Activity ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUMBER);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MESSAGE + " TEXT," + ISSENT + " TEXT)");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
