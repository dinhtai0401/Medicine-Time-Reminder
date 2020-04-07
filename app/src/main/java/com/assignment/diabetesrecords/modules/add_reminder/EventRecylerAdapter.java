package com.assignment.diabetesrecords.modules.add_reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.assignment.diabetesrecords.R;
import com.assignment.diabetesrecords.common.helper.sql_lite_helper.DBHelper;
import com.assignment.diabetesrecords.common.helper.sql_lite_helper.DBStructure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
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

        if (isAlarmed(events.getDATE(), events.getEVENT(), events.getTIME())){
            holder.setAlarm.setImageResource(R.drawable.notification_on);


        }else {
            holder.setAlarm.setImageResource(R.drawable.notification_off);

        }

        Calendar datecalendar = Calendar.getInstance();
        datecalendar.setTime(ConvertStringToDate(events.getDATE()));
        final int alramYear = datecalendar.get(Calendar.YEAR);
        final int alarmMonth = datecalendar.get(Calendar.MONTH);
        final int alarmDay = datecalendar.get(Calendar.DAY_OF_MONTH);
        Calendar timecalendar = Calendar.getInstance();
        timecalendar.setTime(ConvertStringToTime(events.getTIME()));
        final int alarmHour = timecalendar.get(Calendar.HOUR_OF_DAY);
        final int alarmMinute = timecalendar.get(Calendar.MINUTE);
        

        holder.setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAlarmed(events.getDATE(), events.getEVENT(), events.getTIME())){
                    holder.setAlarm.setImageResource(R.drawable.notification_off);
                    cancelAlarm(getRequestCode(events.getDATE(),events.getEVENT(), events.getTIME()));
                    updateEvent(events.getDATE(),events.getEVENT(), events.getTIME(), "off");
                    notifyDataSetChanged();
                }else {
                    holder.setAlarm.setImageResource(R.drawable.notification_on);
                    Calendar alarmcalendar = Calendar.getInstance();
                    alarmcalendar.set(alramYear, alarmMonth, alarmDay,alarmHour,alarmMinute);


                    setAlarm(alarmcalendar, events.getEVENT(), events.getTIME(), getRequestCode(events.getDATE(), events.getEVENT(), events.getTIME()));
                    updateEvent(events.getDATE(),events.getEVENT(), events.getTIME(), "on");
                    notifyDataSetChanged();


                }
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
        ImageButton setAlarm;


        public MyViewHolder(View itemView) {
            super(itemView);
            DateTxt = itemView.findViewById(R.id.eventdate);
            Event = itemView.findViewById(R.id.eventName);
            Time = itemView.findViewById(R.id.eventTime);
            delete = itemView.findViewById(R.id.delete);
            setAlarm = itemView.findViewById(R.id.alarmmeBtn);
        }
    }


    private Date ConvertStringToTime(String eventDate){
        SimpleDateFormat format = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        Date date = null;
        try{
            date = format.parse(eventDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private Date ConvertStringToDate(String eventDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try{
            date = format.parse(eventDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private void deleteCalendarEvent(String event, String date, String time){
        helper = new DBHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        helper.deleteEvent(event, date, time, database);
        helper.close();

    }


    private boolean isAlarmed(String date, String event, String time){
        boolean alarmed = false;
        helper = new DBHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = helper.ReadIDEvents(date, event, time, database);
        while (cursor.moveToNext()){
            String notify = cursor.getString(cursor.getColumnIndex(DBStructure.Notify));
            if(notify.equals("on")){
                alarmed = true;
            }else {
                alarmed = false;
            }
        }
        cursor.close();
        helper.close();
        return alarmed;
    }

    private void setAlarm(Calendar calendar, String event, String time, int RequestCode){
        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("event", event);
        intent.putExtra("time", time);
        intent.putExtra("id", RequestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RequestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager)context.getApplicationContext().getSystemService(context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm(int RequestCode){
        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RequestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager)context.getApplicationContext().getSystemService(context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }


    private int getRequestCode(String date, String event, String time){
        int code = 0;
        helper = new DBHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = helper.ReadIDEvents(date,event,time, database);
        while (cursor.moveToNext()){
            code = cursor.getInt(cursor.getColumnIndex(DBStructure.ID));

        }
        cursor.close();
        helper.close();

        return code;
    }

    private void updateEvent(String date, String event, String time, String notify){
        helper = new DBHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        helper.updateEvent(date, event, time,notify, database);
        helper.close();
    }

}
