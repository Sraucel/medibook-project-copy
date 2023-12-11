package com.example.medibook;

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

import java.util.List;

public class DoctorShiftsList extends ArrayAdapter<DoctorShift> {

    private Activity context;
    private List<DoctorShift> doctorShift;

    private DoctorShiftsActivity doctorShiftsActivity;

    public DoctorShiftsList(Activity context, List<DoctorShift> doctorShift) {
        super(context, R.layout.activity_doctor_shift_signup, doctorShift);
        this.context = context;
        this.doctorShift = doctorShift;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.activity_doctor_shift_signup, null);
        }

        // Assuming you have TextViews in your list item layout
        TextView dateTextView = view.findViewById(R.id.editDate);
        TextView startTimeTextView = view.findViewById(R.id.editStartTime);
        TextView endTimeTextView = view.findViewById(R.id.editEndTime);
        Button deleteShift = view.findViewById(R.id.buttonDeleteShift);

        // Get the DoctorShift object for this position
        DoctorShift doctorShiftItem = doctorShift.get(position);

        deleteShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DoctorShiftsActivity", "Deleting shift");

                String date = dateTextView.getText().toString();
                String startTime = startTimeTextView.getText().toString();
                String endTime = endTimeTextView.getText().toString();

                DoctorShift shift = null;
                for (DoctorShift e : doctorShift){
                    if (e.getDate().equals(date) && e.getStartTime().equals(startTime) && e.getEndTime().equals(endTime)){
                        shift = e;

                    }
                }



                if (shift != null) {
                    if (shift.getStatus().equals("new") || shift.getStatus().equals("Canceled")){
                        shiftRef.child(shift.getUid()).removeValue();
                        Log.d("DoctorShiftsActivity", "Deleting shift #2");
                        doctorShift.remove(shift);
                        DoctorShiftsActivity.getProductsAdapter().notifyDataSetChanged();

                    }
                    else{
                        doctorShiftsActivity = new DoctorShiftsActivity();
                        doctorShiftsActivity.errorMsg(context);
                    }



                }
            }
        });



        // Set the data to the TextViews
        dateTextView.setText(doctorShiftItem.getDate());
        startTimeTextView.setText(doctorShiftItem.getStartTime());
        endTimeTextView.setText(doctorShiftItem.getEndTime());






        return view;


    }


}
