package com.assignment.diabetesrecords.modules.add_reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.assignment.diabetesrecords.R;

import java.text.DateFormat;
import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity {
    CustomCalendarView customCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_reminder);
        customCalendarView = (CustomCalendarView)findViewById(R.id.id_custom_calendar_view);
    }



}