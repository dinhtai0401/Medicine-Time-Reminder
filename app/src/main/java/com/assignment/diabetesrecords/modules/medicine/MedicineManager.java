package com.assignment.diabetesrecords.modules.medicine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.assignment.diabetesrecords.common.helper.sql_lite_helper.DBHelper;
import com.assignment.diabetesrecords.entity.MedicineRecord;
import com.assignment.diabetesrecords.modules.summary.MedicineAdapter;

import java.util.ArrayList;


public class MedicineManager

{
    DBHelper helper;
    SQLiteDatabase db;
    Context ctx;

    public MedicineManager(Context ctx) {
        super();
        this.ctx = ctx;

        helper = new DBHelper(ctx, "DiabetesEntryDB",null,1);
    }


    public long insertMedicine(MedicineRecord medicineRecord)
    {
        long l = 0;

        db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("entry_date", medicineRecord.getEntryDate());
        cv.put("entry_time", medicineRecord.getEntryTime());
        cv.put("food_taken_status", medicineRecord.getFoodTakenStatus());
        cv.put("title", medicineRecord.getTitle());
        cv.put("description", medicineRecord.getDescription());
        cv.put("insulineinformation", medicineRecord.getInsulineInformation());

        l = db.insert("medicine_record", null, cv);
        db.close();
        return l;
    }

    public void deleteMedicine(int id, String entry_date, String entry_time, String food_taken_status, String title,String description, String insulineinformation ) {
        db = helper.getWritableDatabase();
        String selection =  " id =? and entry_date =? and entry_time =? and food_taken_status =? and title = ? and description =? and insulineinformation =? ";
        String [] selectionArg = {String.valueOf(id), entry_date, entry_time, food_taken_status, title, description , insulineinformation};
        db.delete("medicine_record", selection, selectionArg);
        db.close();
    }

    public long update(MedicineRecord medicineRecord)
    {
        long l = 0;

        db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("entry_date", medicineRecord.getEntryDate());
        cv.put("entry_time", medicineRecord.getEntryTime());
        cv.put("food_taken_status", medicineRecord.getFoodTakenStatus());
        cv.put("title", medicineRecord.getTitle());
        cv.put("description", medicineRecord.getDescription());
        l = db.update("medicine_record", cv, "insulineinformation = ?" , new String[] {medicineRecord.getInsulineInformation()});
        db.close();
        return l;
    }

    public long delete(int medicineRecord) {
        int l = 0;
        db = helper.getWritableDatabase();
        l = db.delete("medicine_record", null, null);
        db.close();
        return l;
    }






    public ArrayList<MedicineRecord> getAll(int iPageCount)
    {
        db = helper.getReadableDatabase();
        int iOffset=0;
        int iLimit=10;
        iOffset=iLimit*(iPageCount)-iLimit;
        Cursor cr = db.rawQuery("select * from medicine_record order by entry_date desc LIMIT " + iLimit + " OFFSET " +iOffset, null);
//        Cursor cr = db.rawQuery("select * from diabetes_entry order by diabetes_entry_id desc, entry_date asc LIMIT " + iLimit + " OFFSET " +iOffset, null);

        ArrayList<MedicineRecord> list = new ArrayList<MedicineRecord>();

        while(cr.moveToNext())
        {
            int Id= cr.getInt(0);
            Log.i("Hello", String.valueOf(Id));
            String EntryDate= cr.getString(1);
            String EntryTime= cr.getString(2);
            String FoodTakenStatus= cr.getString(3);

            String Title= cr.getString(4);
            String description= cr.getString(5);
            String insulineinformation= cr.getString(6);


            MedicineRecord medicineRecord = new MedicineRecord(Id, EntryDate,EntryTime,FoodTakenStatus,Title,description,insulineinformation);
            list.add(medicineRecord);
        }

        db.close();
        return list;
    }








}

