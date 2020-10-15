package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseOpener extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Lab5Database";
    public static final String ID = "_id";

    public MyDatabaseOpener(Context ctx){
        super(ctx, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + DATABASE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }
}
