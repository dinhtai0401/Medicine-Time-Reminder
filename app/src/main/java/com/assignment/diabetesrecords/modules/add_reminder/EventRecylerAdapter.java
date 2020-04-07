package com.assignment.diabetesrecords.modules.add_reminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.assignment.diabetesrecords.R;
import com.assignment.diabetesrecords.common.helper.sql_lite_helper.DBHelper;

import java.util.ArrayList;
import java.util.Date;

public class EventRecylerAdapter extends RecyclerView.Adapter<EventRecylerAdapter.MyViewHolder> {

    Context context;
    ArrayList<Events> arrayList;
    DBHelper helper;

    public EventRecylerAdapter(Context context, ArrayList<Events> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_rowlayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Events events = arrayList.get(position);
        holder.Event.setText(events.getEVENT());
        holder.DateTxt.setText(events.getDATE());
        holder.Time.setText(events.getTIME());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCalendarEvent(events.getEVENT(), events.getDATE(),events.getTIME());
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView DateTxt, Event, Time;
        Button delete;


        public MyViewHolder(View itemView) {
            super(itemView);
            DateTxt = itemView.findViewById(R.id.eventdate);
            Event = itemView.findViewById(R.id.eventName);
            Time = itemView.findViewById(R.id.eventTime);
            delete = itemView.findViewById(R.id.delete);
        }
    }


    private void deleteCalendarEvent(String event, String date, String time){
        helper = new DBHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        helper.deleteEvent(event, date, time, database);
        helper.close();

    }
}
