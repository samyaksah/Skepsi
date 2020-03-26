package com.example.skepsi;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    RecyclerView myRecycler;
    MyDatabase db;
    RecyclerView.Adapter myAdapter;
    MyHelper helper;
    private RecyclerView.LayoutManager layoutManager;
    Cursor cursor;
    String x = "x";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        myRecycler = findViewById(R.id.recycler);

        db = new MyDatabase(this);
        helper = new MyHelper(this);
        layoutManager = new LinearLayoutManager(this);
        myRecycler.setLayoutManager(layoutManager);
        cursor = db.getData();



        int index1 = cursor.getColumnIndex(Constants.NAME);


        ArrayList<String> mArrayList = new ArrayList<String>();
        cursor.moveToFirst();



        while (!cursor.isAfterLast()) {
            String recordingName = cursor.getString(index1);
            String s = recordingName;
            mArrayList.add(s);
            cursor.moveToNext();
        }


        myAdapter = new MyAdapter(mArrayList);
//        Toast.makeText(getApplicationContext(), "getting closer" , Toast.LENGTH_SHORT).show();
        myRecycler.setAdapter(myAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout clickedRow = (LinearLayout) view;
        TextView recordTextView = (TextView) view.findViewById(R.id.recordingname);
        Toast.makeText(this, "row " + (1+position) + ":  " + recordTextView.getText() , Toast.LENGTH_LONG).show();
    }
}
