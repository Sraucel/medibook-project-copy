package com.example.medibook;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.medibook.classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppointmentInbox extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppointmentInboxAdapter adapter;
    private List<Appointment> appointmentList;
    private Button acceptAllBtn; // Button to accept all appointments

    private Button doctorAppointmentInboxBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointmentinbox);

        recyclerView = findViewById(R.id.recyclerAppointmentList);


        acceptAllBtn = findViewById(R.id.appointmentAcceptAllBtn);
        doctorAppointmentInboxBack = findViewById(R.id.doctorAppointmentInboxBack);



        fetchAppointments(userList -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new AppointmentInboxAdapter(appointmentList, this);
            recyclerView.setAdapter(adapter);

        });


        doctorAppointmentInboxBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentInbox.this,DoctorInterface.class);
                startActivity(intent);
            }
        });


        acceptAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptAllAppointments();
                Intent intent = new Intent(AppointmentInbox.this, DoctorInterface.class);
                startActivity(intent);
            }
        });
    }


    private void fetchAppointments(OnDataFetchedCallback callback) {
        appointmentList = new ArrayList<>();
        MainActivity.appointmentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                appointmentList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Appointment appointment = snapshot.getValue(Appointment.class);
                    if(DoctorInterface.autoAccept == true)
                        snapshot.getRef().child("status").setValue("Accepted");
                    String status = snapshot.child("status").getValue(String.class);
                    if ("new".equals(status)) { //only add when status is new
                        appointmentList.add(appointment);
                    }

                }
                callback.onDataFetched(appointmentList);

            }

            public void onCancelled(DatabaseError databaseError) {
                // Handle errors.
            }
        });
    }
    private void acceptAllAppointments() {
        MainActivity.appointmentRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @NonNull
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String status = snapshot.child("status").getValue(String.class);
                    if (!"Rejected".equals(status) && !"Cancelled".equals(status)) {// Check if the current status is neither 'Rejected' nor 'Cancelled'
                        snapshot.getRef().child("status").setValue("Accepted");
                    }
                }
            }
            @NonNull
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    // Define a callback interface
    public interface OnDataFetchedCallback {
        void onDataFetched(List<Appointment> appointmentList);
    }







}

