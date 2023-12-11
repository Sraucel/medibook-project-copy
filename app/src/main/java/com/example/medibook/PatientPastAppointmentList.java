package com.example.medibook;

import static com.example.medibook.MainActivity.appointmentRef;
import static com.example.medibook.MainActivity.shiftRef;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PatientPastAppointmentList extends ArrayAdapter<Appointment> {

    private Activity context;
    private List<Appointment> appointment;

    public PatientPastAppointmentList(Activity context, List<Appointment> appointment) {
        super(context, R.layout.activity_patient_past_appointment_text, appointment);
        this.context = context;
        this.appointment = appointment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.activity_patient_past_appointment_text, null);
        }

        // Assuming you have TextViews in your list item layout
        TextView dateTextView = view.findViewById(R.id.editDate3);
        TextView startTimeTextView = view.findViewById(R.id.editStartTime3);
        TextView endTimeTextView = view.findViewById(R.id.editEndTime3);
        Button Rate = view.findViewById(R.id.buttonRate);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);

        // Get the DoctorShift object for this position
        Appointment appointmentItem = appointment.get(position);

        Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = String.valueOf(ratingBar.getRating());

                String date = dateTextView.getText().toString();
                String startTime = startTimeTextView.getText().toString();
                String endTime = endTimeTextView.getText().toString();

                Appointment appointment1 = null;
                for (Appointment e : appointment){
                    if (e.getDate().equals(date) && e.getStartTime().equals(startTime) && e.getEndTime().equals(endTime)){
                        appointment1 = e;

                    }
                }

                if (appointment1 != null) {
                    appointment1.setRating(s);
                    Appointment finalAppointment = appointment1;
                    appointmentRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                                if (productSnapshot.exists() && productSnapshot.child("uid").getValue(String.class).equals(finalAppointment.getUid())) {
                                    productSnapshot.getRef().child("rating").setValue(s);


                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle any errors here
                        }
                    });
                    notifyDataSetChanged();



                }
            }
        });



        // Set the data to the TextViews
        dateTextView.setText(appointmentItem.getDate());
        startTimeTextView.setText(appointmentItem.getStartTime());
        endTimeTextView.setText(appointmentItem.getEndTime());




        return view;


    }


}
