package com.example.skepsi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


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

    public long deleteRow(String name){
        long count;
        Log.d(TAG, "deleteRow: " + name);
        String[] rows = (name).split("\\?");
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs = {rows[0]};
        count = db.delete(Constants.TABLE_NAME, Constants.NAME + "=?", whereArgs);
        return count;

//        String strSQL = "DELETE FROM " + Constants.TABLE_NAME + " WHERE name = " + "'" + name + "'" + ";" ;
//        count = db.execSQL(strSQL);



    }




}

