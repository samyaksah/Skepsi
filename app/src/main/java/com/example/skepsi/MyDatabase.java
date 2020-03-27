package com.example.skepsi;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class MyDatabase {
    SQLiteDatabase db;
    private Context context;
    private final MyHelper helper;

    public MyDatabase (Context c){
        context = c;
        helper = new MyHelper(context);
    }

    public long insertData (String name)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);
        long id = db.insert(Constants.TABLE_NAME, null, contentValues);

        return id;
    }

    public Cursor getData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {Constants.UID, Constants.NAME};
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

//        StringBuffer buffer = new StringBuffer();
//        while (cursor.moveToNext()) {
//
//            int index1 = cursor.getColumnIndex(Constants.NAME);
//            int index2 = cursor.getColumnIndex(Constants.TYPE);
//            int index3 = cursor.getColumnIndex(Constants.LOCATION);
//            int index4 = cursor.getColumnIndex(Constants.LATIN_NAME);
//            String plantName = cursor.getString(index1);
//            String plantType = cursor.getString(index2);
//            String plantLocation = cursor.getString(index3);
//            String plantLatinName = cursor.getString(index4);
//            buffer.append(plantName + " " + plantType + " " + plantLocation + " " + plantLatinName + "\n");
//        }
//        return buffer.toString();
    }

    public int deleteRow(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs = {"herb"};
        int count = db.delete(Constants.TABLE_NAME, Constants.NAME + "=?", whereArgs);
        return count;
    }


}

