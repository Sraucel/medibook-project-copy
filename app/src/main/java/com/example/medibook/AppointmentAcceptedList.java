package com.example.medibook;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppointmentAcceptedList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppointmentAcceptedListAdapter adapter;
    private List<Appointment> acceptAppointmentList;

    private Button buttonAppointmentAcceptedList1;

    FirebaseUser current = MainActivity.mAuth.getCurrentUser();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_accepted_list);

        recyclerView = findViewById(R.id.acceptedAppointmentList);
        buttonAppointmentAcceptedList1 = findViewById(R.id.buttonAppointmentAcceptedList1);


        fetchAppointments(userList -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new AppointmentAcceptedListAdapter(acceptAppointmentList, this);
            recyclerView.setAdapter(adapter);

        });
        buttonAppointmentAcceptedList1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentAcceptedList.this,DoctorInterface.class);
                startActivity(intent);


            }
        });


    }


    private void fetchAppointments(OnDataFetchedCallback callback) {
        acceptAppointmentList = new ArrayList<>();
        MainActivity.appointmentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                acceptAppointmentList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Appointment appointment = snapshot.getValue(Appointment.class);
                    String status = snapshot.child("status").getValue(String.class);
                    if ("Accepted".equals(status) && current.getUid().equals(snapshot.child("doctorShiftId").getValue(String.class))) { //only add when status is accepted
                        acceptAppointmentList.add(appointment);
                    }


                }
                callback.onDataFetched(acceptAppointmentList);

            }

            public void onCancelled(DatabaseError databaseError) {
                // Handle errors.
            }
        });
    }

    public interface OnDataFetchedCallback {
        void onDataFetched(List<Appointment> acceptAppointmentList);
    }









}

