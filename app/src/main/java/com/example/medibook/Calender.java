package com.example.medibook;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Calender extends AppCompatActivity {

    CalendarView calendarView;
    Calendar calendar;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_shift_layout);

        calendarView = findViewById(R.id.calendarView2);
        calendar = Calendar.getInstance();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(Calender.this, month +", "+ dayOfMonth+ ", " + year,Toast.LENGTH_SHORT ).show();
            }
        });


    }
    public void getCalendarDate(){
        long date = calendarView.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        calendar.setTimeInMillis(date);
        String clickedDate = simpleDateFormat.format(calendar.getTime());
        Toast.makeText(this,clickedDate,Toast.LENGTH_SHORT).show();

    }

    public void setCalendarDate(int dayOfMonth,int month, int year){
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month -1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        long milli = calendar.getTimeInMillis();
        calendarView.setDate(milli);

    }
}
