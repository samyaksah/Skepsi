package com.example.skepsi;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private static final String TAG = "MyAdapter";
    public ArrayList<String> list;
    Context context;


    public MyAdapter(ArrayList<String> mArrayList) {
        list = mArrayList;
        Log.d(TAG, "MyAdapter: started");
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.recordingname.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recordingname;
        public LinearLayout myLayout;

        Context context;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myLayout = (LinearLayout) itemView;
            recordingname = itemView.findViewById(R.id.recordingname);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View view) {
            String path = ((TextView)view.findViewById(R.id.recordingname)).getText().toString();
            Toast.makeText(context,
                    "You have clicked " + path,
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, playLastRecording.class);
            intent.putExtra("REC_NAME", path);
            context.startActivity(intent);
        }
    }
}
