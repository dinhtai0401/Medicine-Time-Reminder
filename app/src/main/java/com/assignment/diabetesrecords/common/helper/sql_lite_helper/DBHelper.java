package com.assignment.diabetesrecords.common.helper.sql_lite_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.assignment.diabetesrecords.modules.medicine.MedicineFragment;


public class DBHelper extends SQLiteOpenHelper
{
    String DBPATH;
    private final static String CREATE_EVENT_TABLE = "create table " + DBStructure.EVENT_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT ," +
            DBStructure.EVENT+ "TEXT, " + DBStructure.TIME+ "TEXT, " + DBStructure.DATE + "TEXT, " + DBStructure.MONTH+ "TEXT, "
            +DBStructure.YEAR+ "TEXT)";

    private  final static String DROP_EVENTS_TABLE = "DROP TABLE IF EXISTS " + DBStructure.EVENT_TABLE_NAME;
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version)
    {
        super(context, name, null, version);
    }

    public DBHelper(Context context) {
        super(context, "eventtable", null, DBStructure.DB_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table eventtable(id integer PRIMARY KEY AUTOINCREMENT, event text, time text, date text, month text, year text, notify text)");

        db.execSQL("create table diabetes_entry(diabetes_entry_id integer PRIMARY KEY AUTOINCREMENT,entry_date text,entry_time text,food_taken_status text,glucose_reading real,notes text)");

        db.execSQL("create table medicine_record(id integer PRIMARY KEY AUTOINCREMENT,entry_date text,entry_time text,food_taken_status text,title text,description text,insulineinformation text)");

        db.execSQL("create table doctor(id integer PRIMARY KEY AUTOINCREMENT,name text,phone text,emailid text)");

        db.execSQL("create table appointment(id integer PRIMARY KEY AUTOINCREMENT,doctor_id integer, doctor_name text, doctor_phone text, doctor_email text, appointment_date text,appointment_time text,appointment_reason text)");

        db.execSQL("create table my_profile(id integer PRIMARY KEY AUTOINCREMENT,first_name text,last_name text,middle_name text,dob text,diabetes_type text,gender text)");

        db.execSQL("create table reminder(id integer PRIMARY KEY AUTOINCREMENT, title text, description text, repeation_frequency text, reminder_date text,reminder_time text)");




        //db.execSQL("delete table category");
      //  db.execSQL("create table category(categoryid integer PRIMARY KEY AUTOINCREMENT,categoryname text)");
      //  db.execSQL("create table card(cardid integer PRIMARY KEY AUTOINCREMENT,businessname text,personname text,street text,city text,state text,lat real,lon real,contact1 text,contact2 text,email text,pic text,categoryid integer,country text,comment text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS diabetes_entry");
        db.execSQL("DROP TABLE IF EXISTS medicine_record");
        db.execSQL("DROP TABLE IF EXISTS doctor");
        db.execSQL("DROP TABLE IF EXISTS appointment");
        db.execSQL("DROP TABLE IF EXISTS my_profile");
        db.execSQL("DROP TABLE IF EXISTS reminder");
        db.execSQL(DROP_EVENTS_TABLE);
        onCreate(db);
    }

    public void SaveEvent(String event, String time, String date, String month, String year, String notify, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBStructure.EVENT,event);
        contentValues.put(DBStructure.TIME,time);
        contentValues.put(DBStructure.DATE,date);
        contentValues.put(DBStructure.MONTH,month);
        contentValues.put(DBStructure.YEAR,year);
        contentValues.put(DBStructure.Notify,notify);
        database.insert(DBStructure.EVENT_TABLE_NAME, null, contentValues);
    }

    public Cursor ReadEvents(String date, SQLiteDatabase database){
        String [] Projections = {DBStructure.EVENT,DBStructure.TIME,DBStructure.DATE,DBStructure.MONTH,DBStructure.YEAR};
        String Selection = DBStructure.DATE + "=?";
        String [] SelectionArgs = {date};

        return database.query(DBStructure.EVENT_TABLE_NAME, Projections, Selection, SelectionArgs, null, null, null);
    }

    public Cursor ReadIDEvents(String date, String event, String time, SQLiteDatabase database){
        String [] Projections = {DBStructure.ID,DBStructure.Notify};
        String Selection = DBStructure.DATE + "=? and " + DBStructure.EVENT + "=? and " + DBStructure.TIME + "=? ";
        String [] SelectionArgs = {date, event, time};

        return database.query(DBStructure.EVENT_TABLE_NAME, Projections, Selection, SelectionArgs, null, null, null);
    }

    public Cursor ReadEventsperMonth(String month, String year, SQLiteDatabase database){
        String [] Projections = {DBStructure.EVENT,DBStructure.TIME,DBStructure.DATE,DBStructure.MONTH,DBStructure.YEAR};
        String Selection = DBStructure.MONTH + "=? and " + DBStructure.YEAR+ "=?";
        String [] SelectionArgs = {month,year};
        return database.query(DBStructure.EVENT_TABLE_NAME, Projections, Selection, SelectionArgs, null, null, null);
    }

    public void deleteEvent(String event, String date, String time, SQLiteDatabase database){
        String selection = DBStructure.EVENT + "=? and " + DBStructure.DATE + "=? and " + DBStructure.TIME + "=? ";
        String [] selectionArg = {event, date, time};
        database.delete(DBStructure.EVENT_TABLE_NAME, selection, selectionArg);
    }

    public void updateEvent(String date, String event, String time,String notify, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBStructure.Notify,notify);
        String Selection = DBStructure.DATE + "=? and " + DBStructure.EVENT + "=? and " + DBStructure.TIME + "=? ";
        String [] SelectionArgs = {date, event, time};
        database.update(DBStructure.EVENT_TABLE_NAME, contentValues, Selection, SelectionArgs);
    }


}
