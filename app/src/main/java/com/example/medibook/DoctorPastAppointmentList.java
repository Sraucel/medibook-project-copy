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

public class DoctorPastAppointmentList extends ArrayAdapter<Appointment> {

    private Activity context;
    private List<Appointment> appointment;

    public DoctorPastAppointmentList(Activity context, List<Appointment> appointment) {
        super(context, R.layout.activity_doctor_past_appointment_text, appointment);
        this.context = context;
        this.appointment = appointment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.activity_doctor_past_appointment_text, null);
        }

        // Assuming you have TextViews in your list item layout
        TextView dateTextView = view.findViewById(R.id.editDate4);
        TextView startTimeTextView = view.findViewById(R.id.editStartTime4);
        TextView endTimeTextView = view.findViewById(R.id.editEndTime4);
        TextView ratingTextView = view.findViewById(R.id.docRating);


        // Get the DoctorShift object for this position
        Appointment appointmentItem = appointment.get(position);





        // Set the data to the TextViews
        dateTextView.setText(appointmentItem.getDate());
        startTimeTextView.setText(appointmentItem.getStartTime());
        endTimeTextView.setText(appointmentItem.getEndTime());
        ratingTextView.setText(appointmentItem.getRating());




        return view;


    }


}
