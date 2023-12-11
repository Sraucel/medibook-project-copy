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
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.time.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.TimeZone;

public class PatientUpcomingList extends ArrayAdapter<Appointment> {

    private Activity context;
    private List<Appointment> appointment;

    public PatientUpcomingList(Activity context, List<Appointment> appointment) {
        super(context, R.layout.activity_patient_upcoming_text, appointment);
        this.context = context;
        this.appointment = appointment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.activity_patient_upcoming_text, null);
        }

        // Assuming you have TextViews in your list item layout
        TextView dateTextView = view.findViewById(R.id.editDate2);
        TextView startTimeTextView = view.findViewById(R.id.editStartTime2);
        TextView endTimeTextView = view.findViewById(R.id.editEndTime2);
        Button cancel = view.findViewById(R.id.buttonCancelBooking);

        // Get the DoctorShift object for this position
        Appointment appointmentItem = appointment.get(position);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = dateTextView.getText().toString();
                String startTime = startTimeTextView.getText().toString();
                String endTime = endTimeTextView.getText().toString();

                Appointment appointment1 = null;
                for (Appointment e : appointment){
                    if (e.getDate().equals(date) && e.getStartTime().equals(startTime) && e.getEndTime().equals(endTime)){
                        appointment1 = e;

                    }
                }


                int month = Integer.parseInt(date.substring(0, 2));
                int day = Integer.parseInt(date.substring(2, 4));
                int year = Integer.parseInt(date.substring(4, 8));

                Calendar currentDate = Calendar.getInstance(TimeZone.getTimeZone("America/Toronto")); // Adjust to your specific time zone


                Calendar enteredDate = new GregorianCalendar(TimeZone.getTimeZone("America/Toronto")); // Adjust to your specific time zone
                enteredDate.set(Calendar.YEAR, year);
                enteredDate.set(Calendar.MONTH, month-1);
                enteredDate.set(Calendar.DAY_OF_MONTH, day);
                enteredDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime.substring(0,2)));
                enteredDate.set(Calendar.MINUTE, Integer.parseInt(startTime.substring(3,5)));
                enteredDate.set(Calendar.SECOND, 0);
                enteredDate.set(Calendar.MILLISECOND, 0);

                long differenceMil = (enteredDate.getTimeInMillis() - currentDate.getTimeInMillis()) /(60 * 1000);
                Log.d("Cancellation", "Current Date: " + currentDate.getTime());
                Log.d("Cancellation", "Entered Date: " + enteredDate.getTime());
                Log.d("Cancellation", "Time Difference (minutes): " + differenceMil);

                if (differenceMil < 60){
                    Toast.makeText(context,"Cannot cancel an appointment less than 60 minutes away. ", Toast.LENGTH_SHORT).show();
                    return;

                }



                if (appointment1 != null) {
                    Appointment finalAppointment = appointment1;
                    shiftRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                                if (productSnapshot.exists() && productSnapshot.child("uid").getValue(String.class).equals(finalAppointment.getShiftId())) {
                                    productSnapshot.getRef().child("status").setValue("new");


                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle any errors here
                        }
                    });
                    
                    appointmentRef.child(appointment1.getUid()).removeValue();
                    Log.d("DoctorShiftsActivity", "Deleting shift #2");
                    appointment.remove(appointment1);
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
