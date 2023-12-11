package com.example.medibook;

import static com.example.medibook.MainActivity.mAuth;
import static com.example.medibook.MainActivity.shiftRef;
import static com.example.medibook.MainActivity.userRef;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.time.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.TimeZone;

public class DoctorShiftsActivity extends AppCompatActivity {
    EditText editTextDate;
    EditText editTextStartTime;
    EditText editTextEndTime;
    Button buttonAddShifts;
    Button buttonDeleteShift;

    Button backBtn;
    ListView listViewShifts;

    static DoctorShiftsList productsAdapter;

    static List<DoctorShift> doctorShiftList;

    String specialty;

    FirebaseUser current = MainActivity.mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_shift_day);





        shiftRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctorShiftList.clear();




                    for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                        if (productSnapshot.exists() && current.getUid().equals(productSnapshot.child("doctorID").getValue(String.class))) {
                            DoctorShift shift = productSnapshot.getValue(DoctorShift.class);

                            doctorShiftList.add(shift);
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

        buttonAddShifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addShift();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorShiftsActivity.this, DoctorInterface.class);
                startActivity(intent);
            }
        });
        listViewShifts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DoctorShift shift = doctorShiftList.get(i);
                buttonDeleteShift.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("DoctorShiftsActivity", "Deleting shift");

                        deleteShift();
                    }
                });

            }



        });



    }

    private void createViews() {
        editTextDate = findViewById(R.id.editDate);
        editTextEndTime = findViewById(R.id.editEndTime);
        editTextStartTime = findViewById(R.id.editStartTime);
        buttonAddShifts = findViewById(R.id.addButton);
        listViewShifts = findViewById(R.id.listViewProducts);
        backBtn = findViewById(R.id.backButtonDoctorInterface);




        buttonDeleteShift = findViewById(R.id.buttonDeleteShift);
        if (buttonDeleteShift == null) {
            Log.e("DoctorShiftsActivity", "buttonDeleteShift is null");
        } else {
            Log.d("DoctorShiftsActivity", "buttonDeleteShift is not null");
        }

        if (buttonDeleteShift == null) {
            Log.e("DoctorShiftsActivity", "buttonDeleteShift is null");
        }

        doctorShiftList = new ArrayList<>();
        productsAdapter = new DoctorShiftsList(DoctorShiftsActivity.this, doctorShiftList);


        listViewShifts.setAdapter(productsAdapter);
    }
    public static List<DoctorShift> getArrayList(){
        return doctorShiftList;
    }
    public static DoctorShiftsList getProductsAdapter(){
        return productsAdapter;
    }

    private void addShift() {
        Log.d("DoctorShiftsActivity", "addShift() called");

        String date = editTextDate.getText().toString();
        String startTime = editTextStartTime.getText().toString();
        String endTime = editTextEndTime.getText().toString();

        if (date.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check time format
        if (!validTimeFormat(startTime) || !validTimeFormat(endTime)) {
            Toast.makeText(this, "Invalid time format. Please use HH:mm in 24 hour clock", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the time difference is at least 30 minutes
        if (!validTime30Min(startTime, endTime)) {
            Toast.makeText(this, "Times must end in :00 or :30", Toast.LENGTH_SHORT).show();
            return;
        }

        // finds out if day is in the past, invalid. Valid otherwise
        if (!dateValid(date) || !timeValid(startTime,endTime)){
            Toast.makeText(this, "Date or time must be in the present or future. ", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("DoctorShiftsActivity", "Date: " + date + ", Start Time: " + startTime + ", End Time: " + endTime);



        if (isShiftConflict(date, startTime, endTime)) {
            Log.d("DoctorShiftsActivity", "Shift conflicts with existing shifts");
            Toast.makeText(this, "Shift conflicts with existing shifts", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("DoctorShiftsActivity", "Adding shift to the list");



            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot2) {
                    if ((dataSnapshot2.exists()) && (dataSnapshot2.hasChild(current.getUid()))) {

                        specialty = dataSnapshot2.child(current.getUid()).child("specialties").getValue(String.class);

                        String key = MainActivity.shiftRef.push().getKey();

                        Log.d("DoctorShiftsActivity","speciality: " + specialty);
                        DoctorShift shift = new DoctorShift(date, startTime, endTime, specialty,key, "new", current.getUid());
                        MainActivity.shiftRef.child(key).setValue(shift);
                        doctorShiftList.add(shift);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            productsAdapter.notifyDataSetChanged();


        }


    }

    private void deleteShift() {
        String date = editTextDate.getText().toString();
        String startTime = editTextStartTime.getText().toString();
        String endTime = editTextEndTime.getText().toString();

        DoctorShift shift = findShiftByDate(date, startTime, endTime);

        if (shift != null) {
            shiftHasAppointments(shift, new OnAppointmentCheckListener() {
                @Override
                public void onCheckComplete(boolean hasAppointments) {
                    if (hasAppointments) {
                        Toast.makeText(DoctorShiftsActivity.this, "Cannot Delete this shift as a Patient has booked an appointment. ", Toast.LENGTH_LONG).show();
                    } else {
                        shiftRef.child(shift.getUid()).removeValue();

                        doctorShiftList.remove(shift);
                        productsAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

    }

    private void shiftHasAppointments(DoctorShift shift, OnAppointmentCheckListener listener){
        DatabaseReference appointmentsRef = FirebaseDatabase.getInstance().getReference("appointments");

        appointmentsRef.orderByChild("shiftID").equalTo(shift.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    listener.onCheckComplete(true);
                } else {
                    listener.onCheckComplete(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });

    }
    public interface OnAppointmentCheckListener {
        void onCheckComplete(boolean hasAppointments);
    }
    public DoctorShift findShiftByDate(String date, String startTime, String endTime){
        for (DoctorShift e : doctorShiftList){
            if (e.getDate().equals(date) && e.getStartTime().equals(startTime) && e.getEndTime().equals(endTime)){
                return e;
            }
        }
        return null;
    }



    private boolean dateValid(String date) {
        if (date.length() != 8) {
            Toast.makeText(this, "Invalid date format. Please use monthdayyear", Toast.LENGTH_SHORT).show();
            return false;
        }

        int month = Integer.parseInt(date.substring(0, 2));
        int day = Integer.parseInt(date.substring(2, 4));
        int year = Integer.parseInt(date.substring(4, 8));

        Calendar currentDate = Calendar.getInstance(TimeZone.getTimeZone("America/Toronto")); // Adjust to your specific time zone
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        Calendar enteredDate = new GregorianCalendar(TimeZone.getTimeZone("America/Toronto")); // Adjust to your specific time zone
        enteredDate.set(Calendar.YEAR, year);
        enteredDate.set(Calendar.MONTH, month-1);
        enteredDate.set(Calendar.DAY_OF_MONTH, day);
        enteredDate.set(Calendar.HOUR_OF_DAY, 0);
        enteredDate.set(Calendar.MINUTE, 0);
        enteredDate.set(Calendar.SECOND, 0);
        enteredDate.set(Calendar.MILLISECOND, 0);

        Log.d("DoctorShiftsActivity", String.valueOf(enteredDate));
        Log.d("DoctorShiftsActivity", String.valueOf(currentDate));

        if (enteredDate.after(currentDate) || enteredDate.equals(currentDate)) {
            return true;
        } else {
            Toast.makeText(this, "Please enter a future date", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean timeValid(String start, String end) {
        Date currentDate = new Date();


        String date = editTextDate.getText().toString();
        String startDateTime = date + " " + start;
        String endDateTime = date + " " + end;

        SimpleDateFormat e = new SimpleDateFormat("MMddyyyy HH:mm");

        try {
            Date startDate = e.parse(startDateTime);
            Date endDate = e.parse(endDateTime);

            // Check if the start and end times are in the past
            if (startDate.before(currentDate) || endDate.before(currentDate)) {
                Toast.makeText(this, "Please enter a time in the present or future", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (ParseException x) {
            x.printStackTrace();
            // Handle the parsing exception
            Toast.makeText(this, "Error parsing date or time", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isShiftConflict(String newDate, String newStartTime, String newEndTime) {
        DoctorShift newShift = new DoctorShift(newDate, newStartTime, newEndTime);
        for (DoctorShift existingShift : doctorShiftList) {
            if (existingShift.getDate().equals(newShift.getDate()) && overlap(existingShift.getStartTime(), existingShift.getEndTime(), newShift.getStartTime(), newShift.getEndTime())) {
                return true;  // conflict found
            }
        }
        return false;  // No conflict found, input shift
    }


    private boolean overlap(String start1, String end1, String start2, String end2) {
        Log.d("DoctorShiftsActivity", "Comparing time ranges: " + start1 + " - " + end1 + " with " + start2 + " - " + end2);

        boolean overlap = !((end1.compareTo(start2) <= 0) || (start1.compareTo(end2) >= 0));

        Log.d("DoctorShiftsActivity", "Overlap result: " + overlap);

        return overlap;
    }
    private boolean validTime30Min(String startTime, String endTime) {
        try {
            String[] startComponents = startTime.split(":");
            String[] endComponents = endTime.split(":");

            int startMinutes = Integer.parseInt(startComponents[1]);
            int endMinutes = Integer.parseInt(endComponents[1]);

            // Check if the minutes are either 0 or 30
            return (startMinutes == 0 || startMinutes == 30) && (endMinutes == 0 || endMinutes == 30);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            Log.e("DoctorShiftsActivity", "Error parsing time: " + e.getMessage());
            return false;
        }
    }


    private boolean validTimeFormat(String time) {
        if (time.contains(":")) { // entered time needs to have a colon
            String[] timeComponents = time.split(":");
            if (timeComponents.length == 2) {
                int hours = Integer.parseInt(timeComponents[0]);
                int minutes = Integer.parseInt(timeComponents[1]);

                // Check if hours and minutes are in the valid range
                if (hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59) {
                    return true;
                }
            }
        }
        return false;
    }

    public void errorMsg(Context context){
        Toast.makeText(context, "Shift is booked, can't delete", Toast.LENGTH_LONG).show();
    }


}

