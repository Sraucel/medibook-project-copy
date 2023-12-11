package com.example.medibook;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.medibook.MainActivity.shiftRef;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.time.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.TimeZone;

public class DoctorPastAppointmentActivity extends AppCompatActivity {
    ListView listViewPastAppointment;

    List<Appointment> appointmentList;

    DoctorPastAppointmentList pastAdapter;

    Button patientPastAppointmentBackButton;


    FirebaseUser current = MainActivity.mAuth.getCurrentUser();


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_past_appointment);


        MainActivity.appointmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Appointment appointment = snapshot.getValue(Appointment.class);
                    // Check if the appointment is in the past
                    if (current.getUid().equals(snapshot.child("doctorShiftId").getValue(String.class))) {
                        Log.d("PatientPastAppointmentActivity", "pass");

                        appointmentList.add(appointment);
                    }
                }


                pastAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        createViews();

        patientPastAppointmentBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorPastAppointmentActivity.this, DoctorInterface.class);
                startActivity(intent);

            }
        });





    }

    private void createViews(){
        listViewPastAppointment = findViewById(R.id.listViewPastDoctor);
        patientPastAppointmentBackButton = findViewById(R.id.PastAppointmentBackButton);

        appointmentList = new ArrayList<>();
        pastAdapter = new DoctorPastAppointmentList(DoctorPastAppointmentActivity.this,appointmentList);
        listViewPastAppointment.setAdapter(pastAdapter);


    }




}
