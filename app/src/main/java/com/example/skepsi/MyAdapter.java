package com.example.skepsi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    MyDatabase db;

    private static final String TAG = "MyAdapter";
    public ArrayList<String> list;
    Context context;


    public MyAdapter(ArrayList<String> mArrayList, Context context) {
        list = mArrayList;
        this.context = context;
        Log.d(TAG, "MyAdapter: started");
    }


    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyAdapter.MyViewHolder holder, final int position) {

        String[] rows = (list.get(position)).split("\\?");
        String[] results = (rows[0]).split("/");
        String rec_name = results[4];
        String[] clean = rec_name.split("\\.");
        String final_name = clean[0];
        String latitude = rows[1];
        String longitude = rows[2];
        String address = rows[3];



        //set all the values and recoding names in the recycler view
        holder.recordingname.setText(final_name);
        holder.latit.setText(latitude);
        holder.longi.setText(longitude);
        holder.address.setText(address);

        // takes you to the play recoding activity
        holder.selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] rows = (list.get(position)).split("\\?");
                String path = rows[0];
//                Toast.makeText(context,
//                        "You have clicked " + path,
//                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, playLastRecording.class);
                intent.putExtra("REC_NAME", path);
                context.startActivity(intent);
            }
        });
        //menu button for each recording
        holder.menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating a popup menu
                db = new MyDatabase(context);
                PopupMenu popup = new PopupMenu(context, holder.kebab);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                //handle menu1 click
                                long count = db.deleteRow(list.get(position));
                                if(count != 0){
                                    Intent refresh = new Intent(context, record.class);
                                    refresh.putExtra("REFRESH", count);
//                                    Toast.makeText(context,
//                                            "sending data ",
//                                            Toast.LENGTH_SHORT).show();
                                    context.startActivity(refresh);
                                }
//                                Toast.makeText(context,
//                                        "To be Implemented with Room Persistence Library ",
//                                        Toast.LENGTH_SHORT).show();
//

                                break;
//                            case R.id.menu2:
//                                //handle menu2 click
////                                Toast.makeText(context,
////                                        "To be Implemented with Room Persistence Library ",
////                                        Toast.LENGTH_SHORT).show();
//                                break;
                            case R.id.menu2:
                                //handle menu3 click
//

                                String[] rows = (list.get(position)).split("\\?");
                                String path = rows[0];
//                Toast.makeText(context,
//                        "You have clicked " + path,
//                        Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(context, playLastRecording.class);
                                intent.putExtra("REC_NAME", path);
                                context.startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView recordingname, kebab, latit, longi, address;
        public LinearLayout myLayout;
        TableRow selection, menubar;

        Context context;

        public MyViewHolder( View itemView) {
            super(itemView);
            myLayout = (LinearLayout) itemView;
            recordingname = itemView.findViewById(R.id.recordingname);
            selection = itemView.findViewById(R.id.selection_box);
            menubar = itemView.findViewById(R.id.menubar);
            kebab = itemView.findViewById(R.id.textViewOptions);
            latit = itemView.findViewById(R.id.latit);
            longi = itemView.findViewById(R.id.longi);
            address = itemView.findViewById(R.id.address);


        }


    }
}
