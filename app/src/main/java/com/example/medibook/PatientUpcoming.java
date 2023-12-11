package com.example.medibook;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class PatientUpcoming extends AppCompatActivity {


    Button buttonCancel;

    Button backBtn;
    ListView listViewUpcoming;

    List<Appointment> appointmentList;

    PatientUpcomingList productsAdapter;


    FirebaseUser current = MainActivity.mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_upcoming);


        MainActivity.appointmentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointmentList.clear();


                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    if (productSnapshot.exists() && current.getUid().equals(productSnapshot.child("patientUid").getValue(String.class)) && productSnapshot.child("status").getValue(String.class).equals("Accepted")) {
                        Appointment appointment = productSnapshot.getValue(Appointment.class);
                        appointmentList.add(appointment);
                    }
                }

                productsAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors here
            }
        });



        createViews();



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientUpcoming.this, PatientInterface.class);
                startActivity(intent);
            }
        });



    }

    private void createViews() {

        buttonCancel = findViewById(R.id.buttonCancelBooking);
        listViewUpcoming = findViewById(R.id.listViewUpcoming);


        backBtn = findViewById(R.id.patientUpcomingBackButton1);




        appointmentList = new ArrayList<>();
        productsAdapter = new PatientUpcomingList(PatientUpcoming.this, appointmentList);


        listViewUpcoming.setAdapter(productsAdapter);
    }



}
