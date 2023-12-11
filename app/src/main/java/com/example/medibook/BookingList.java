package com.example.medibook;

import static com.example.medibook.MainActivity.shiftRef;
import static com.example.medibook.MainActivity.userRef;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class BookingList extends ArrayAdapter<Booking> {

    private Activity context;
    private List<Booking> bookings;

    FirebaseUser current = MainActivity.mAuth.getCurrentUser();

    public BookingList(Activity context, List<Booking> bookings) {
        super(context, R.layout.activity_patient_booking_text, bookings);
        this.context = context;
        this.bookings = bookings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.activity_patient_booking_text, null);
        }

        // Assuming you have TextViews in your list item layout
        TextView dateTextView = view.findViewById(R.id.editDateB);
        TextView startTimeTextView = view.findViewById(R.id.editStartTimeB);
        TextView endTimeTextView = view.findViewById(R.id.editEndTimeB);
        Button bookItem = view.findViewById(R.id.buttonSelectBooking);

        // Get the DoctorShift object for this position
        Booking bookingItem = bookings.get(position);

        bookItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dateTextView.getText().toString();
                String startTime = startTimeTextView.getText().toString();
                String endTime = endTimeTextView.getText().toString();
                Log.d("DoctorShiftsActivity", "Booking Appointment");


                Booking booking = null;
                for (Booking e : bookings){
                    if (e.getDate().equals(date) && e.getStartTime().equals(startTime) && e.getEndTime().equals(endTime)){
                        booking = e;

                    }
                }


                if (booking != null) {



                    String key = MainActivity.appointmentRef.push().getKey();
                    shiftRef.child(booking.getUid()).child("status").setValue("Booked");

                    Appointment a = new Appointment(current.getUid(), booking.getDoctorID(), date, startTime,endTime,"new", key, booking.getUid(),"0");
                    MainActivity.appointmentRef.child(key).setValue(a);
                    bookings.remove(booking);
                    notifyDataSetChanged();


                }



            }
        });

        // Set the data to the TextViews
        dateTextView.setText(bookingItem.getDate());
        startTimeTextView.setText(bookingItem.getStartTime());
        endTimeTextView.setText(bookingItem.getEndTime());

        return view;
    }
}
