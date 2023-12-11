package com.example.medibook;

import static com.example.medibook.MainActivity.shiftRef;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientBookingActivity extends AppCompatActivity {

    String[] specialty = {"family medicine", "internal medicine", "pediatrics", "obstetrics", "gynecology"};

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterItems;

    List<Booking> bookingList;

    BookingList bookingsAdapter;


    Button buttonSelectBookings;

    Button backBtn;
    ListView listViewBookings;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_booking);

        autoCompleteTextView = findViewById(R.id.autoCompleteText);
        adapterItems = new ArrayAdapter<String>(this, R.layout.activity_patient_booking_dropdown, specialty);

        autoCompleteTextView.setAdapter(adapterItems);

        createViews();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientBookingActivity.this,PatientInterface.class);
                startActivity(intent);
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(PatientBookingActivity.this,"Specialty"+item, Toast.LENGTH_SHORT).show();
                displayInformation(item);

            }
        });

    }

    private void createViews() {

        buttonSelectBookings = findViewById(R.id.buttonSelectBooking);
        listViewBookings = findViewById(R.id.listViewProducts);
        backBtn = findViewById(R.id.buttonBookingBack);


        bookingList = new ArrayList<>();
        bookingsAdapter = new BookingList(PatientBookingActivity.this, bookingList);
        listViewBookings.setAdapter(bookingsAdapter);

    }


//    doctor need specialty store in the shifts in firebase, need mauth to find the registered doctor then get the specialty----

    private void displayInformation(String selectedItem) {

        MainActivity.shiftRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingList.clear();

                if (snapshot.exists()) {
                    for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                        if (productSnapshot.child("status").getValue(String.class).equals("new")){
                            Booking shift = productSnapshot.getValue(Booking.class);
                            String s = productSnapshot.child("specialty").getValue(String.class);
                            if (s.equals(selectedItem)){
                                bookingList.add(shift);
                            }
                        }


                    }
                }
                bookingsAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors here
            }
        });
        // Use a switch statement or if-else conditions to handle different cases
        switch (selectedItem) {
            case "family medicine":
                // Display information for family medicine
                Toast.makeText(PatientBookingActivity.this, "Selected: Family Medicine", Toast.LENGTH_SHORT).show();
                break;
            case "internal medicine":
                // Display information for internal medicine
                Toast.makeText(PatientBookingActivity.this, "Selected: Internal Medicine", Toast.LENGTH_SHORT).show();
                break;
            case "pediatrics":
                // Display information for pediatrics
                Toast.makeText(PatientBookingActivity.this, "Selected: Pediatrics", Toast.LENGTH_SHORT).show();
                break;
            case "obstetrics":
                // Display information for obstetrics
                Toast.makeText(PatientBookingActivity.this, "Selected: Obstetrics", Toast.LENGTH_SHORT).show();
                break;
            case "gynecology":
                // Display information for gynecology
                Toast.makeText(PatientBookingActivity.this, "Selected: Gynecology", Toast.LENGTH_SHORT).show();
                break;
            default:
                // Handle the default case or unknown specialty
                Toast.makeText(PatientBookingActivity.this, "Unknown Specialty", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
