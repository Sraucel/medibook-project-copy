package com.example.medibook;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AppointmentAcceptedListAdapter extends RecyclerView.Adapter<AppointmentAcceptedListViewHolder> {
    private List<Appointment> appointments;
    private Context context;

    private AppointmentAcceptedList a;

    public AppointmentAcceptedListAdapter(List<Appointment> appointments, Context context) {
        this.appointments = appointments;
        this.context = context;
    }

    @NonNull
    @Override
    public AppointmentAcceptedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppointmentAcceptedListViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_recycler_accepted_appointment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAcceptedListViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);

        holder.shiftIdView.setText(appointment.getDoctorShiftId());
        holder.startTimeView.setText(appointment.getStartTime());
        holder.endTimeView.setText(appointment.getEndTime());
        holder.patientUidView.setText(appointment.getPatientUid());
        holder.statusView.setText(appointment.getStatus());

        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAppointmentStatus(appointment.getUid(), "new");
            }
        });

        holder.infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfo(appointment.getPatientUid());
            }
        });



    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    private void updateAppointmentStatus(String id, String newStatus) {

        MainActivity.appointmentRef.child(id).child("status").setValue(newStatus);

    }

    private void showInfo(String id) {
        Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.popup_window);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        TextView nameView = dialog.findViewById(R.id.first_nameView);
        TextView lastView = dialog.findViewById(R.id.last_nameView);
        TextView emailView = dialog.findViewById(R.id.emailView);
        TextView addressView = dialog.findViewById(R.id.addressView);
        TextView phoneView = dialog.findViewById(R.id.phoneView);
        TextView healthView = dialog.findViewById(R.id.healthView);

        MainActivity.userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child(id).child("firstName").getValue(String.class);
                String last = dataSnapshot.child(id).child("lastName").getValue(String.class);
                String email = dataSnapshot.child(id).child("email").getValue(String.class);
                String phone = dataSnapshot.child(id).child("phoneNumber").getValue(String.class);
                String address = dataSnapshot.child(id).child("address").getValue(String.class);
                String health = dataSnapshot.child(id).child("healthCardNumber").getValue(String.class);
                if (nameView != null) {
                    nameView.setText("First Name: "+name);
                    lastView.setText("Last Name:"+last);
                    emailView.setText("Email: "+email);
                    addressView.setText("Address:"+address);
                    phoneView.setText("Phone number :"+phone);
                    healthView.setText("Healthcard number: "+health);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error if needed
            }
        });

        dialog.show();
    }

}

