package com.example.skepsi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class MyDatabase {
    SQLiteDatabase db;
    private Context context;
    private final MyHelper helper;
    private static final String TAG = "MyDatabase";

    public MyDatabase (Context c){
        context = c;
        helper = new MyHelper(context);
    }
    //insert data into the database
    public long insertData (String name, String lat, String longi, String address)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);
        contentValues.put(Constants.LAT, lat);
        contentValues.put(Constants.LONGI, longi);
        contentValues.put(Constants.ADD, address);
        long id = db.insert(Constants.TABLE_NAME, null, contentValues);
        return id;
    }

    //get data from the database
    public Cursor getData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {Constants.UID, Constants.NAME, Constants.LAT, Constants.LONGI, Constants.ADD};
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, null, null, null, null, null);

        return cursor;
    }


    public Cursor getSelectedData(String name)
    {
        //select plants from database of type 'herb'
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.NAME};

        String selection = Constants.NAME + "='" +name+ "'";  //Constants.TYPE = 'type'
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, selection, null, null, null, null);

        return cursor;


    }

    public int deleteRow(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs = {name};

        int count;


        db.beginTransaction();
        try {
            count = db.delete(Constants.TABLE_NAME, Constants.NAME + "=?", whereArgs);
            db.setTransactionSuccessful();
        } catch(Exception e) {
            //Error in between database transaction
            count = 0;
        } finally {
            db.endTransaction();
        }

        return count;
    }




}

