package com.example.medibook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class DoctorInterface extends AppCompatActivity {

    private Button logOffBtn;

    private Button viewShiftsBtn;

    private Button appointmentListBtn;

    private Button appointmentAcceptedListBtn;

    private ToggleButton autoAcceptButton;

    private Button doctorPastAppointments1;

    public static boolean autoAccept = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_interface);
        createViews();

        logOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mAuth.signOut();
                Intent intent = new Intent(DoctorInterface.this,MainActivity.class);
                startActivity(intent);
            }
        });
        viewShiftsBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent(DoctorInterface.this, DoctorShiftsActivity.class);
                startActivity(intent);
            }
        });

        appointmentListBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent(DoctorInterface.this, AppointmentInbox.class);
                startActivity(intent);
            }
        });

        appointmentAcceptedListBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent(DoctorInterface.this, AppointmentAcceptedList.class);
                startActivity(intent);
            }
        });

        autoAcceptButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    autoAcceptButton.setText("On");
                    autoAccept = true;
                }
                else{
                    autoAcceptButton.setText("Off");
                    autoAccept = false;
                }
            }
        });

        doctorPastAppointments1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorInterface.this,DoctorPastAppointmentActivity.class);
                startActivity(intent);
            }
        });


        MainActivity.appointmentRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @NonNull
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String status = snapshot.child("status").getValue(String.class);
                    if (!"Rejected".equals(status) && !"Cancelled".equals(status) && autoAccept == true) {// Check if the current status is neither 'Rejected' nor 'Cancelled'
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



    public void createViews(
    ){
        logOffBtn = findViewById(R.id.logOutAsDoctor);
        viewShiftsBtn = findViewById(R.id.viewShiftsBtn);
        appointmentListBtn = findViewById(R.id.appointmentListButton);
        appointmentAcceptedListBtn = findViewById(R.id.appointmentAcceptedListButton);
        autoAcceptButton = findViewById(R.id.autoAcceptButton);
        doctorPastAppointments1 = findViewById(R.id.doctorPastAppointments1);

    }

}