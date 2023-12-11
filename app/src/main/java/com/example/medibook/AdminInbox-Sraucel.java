package com.example.medibook;
import android.util.Log;
import android.widget.Button;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.medibook.classes.Doctor;
import com.example.medibook.classes.Patient;
import com.example.medibook.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminInbox extends AppCompatActivity {
        private Button clickBack;

        private static User tempUser;
        private static String tempUserId;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_admin_inbox);

                RecyclerView recyclerView = findViewById(R.id.recyclerUserInfo);

                fetchData(userList -> {
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));

                        AdminInboxAdapter adapter = new AdminInboxAdapter(this, userList);
                        adapter.setOnClickListener(new AdminInboxAdapter.OnClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                        AdminRejectedList.setTempUser(null);
                                        AdminRejectedList.setUserId(null);
                                        tempUser = userList.get(position);
                                        tempUserId = tempUser.getUserId();

                                        Intent intent = new Intent(AdminInbox.this,AdminConfirmReject.class); //Change to the inbox class
                                        startActivity(intent);
                                }
                        });
                        recyclerView.setAdapter(adapter);

                });

        }

        private void fetchData(OnDataFetchedCallback callback) {
                List<User> pendingList = new ArrayList<>();

                MainActivity.registrationRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                        String firstName = snapshot1.child("firstName").getValue(String.class);
                                        String lastName = snapshot1.child("lastName").getValue(String.class);
                                        String email = snapshot1.child("email").getValue(String.class);
                                        String phoneNumber = snapshot1.child("phoneNumber").getValue(String.class);
                                        String address = snapshot1.child("address").getValue(String.class);
                                        String password = snapshot1.child("password").getValue(String.class);
                                        String status = snapshot1.child("status").getValue(String.class);
                                        String userId = snapshot1.child("userId").getValue(String.class);
                                        String healthCardNUmberOrSP;
                                        int check;
                                        String employeeNumber = null;
                                        if (snapshot1.child("employeeNumber").getValue(String.class) != null){
                                                employeeNumber = snapshot1.child("employeeNumber").getValue(String.class);
                                        }

                                        if (snapshot1.child("healthCardNumber").getValue(String.class) != null){
                                                 healthCardNUmberOrSP = snapshot1.child("healthCardNumber").getValue(String.class);
                                                 check = 0;
                                        }
                                        else{
                                                healthCardNUmberOrSP = snapshot1.child("specialties").getValue(String.class);
                                                check = 1;
                                        }


                                        if(status.equals("pending")) {
                                                if (check == 0){
                                                        pendingList.add(new Patient(firstName, lastName, email, password, phoneNumber, address, status, healthCardNUmberOrSP, userId));
                                                }
                                                else if (check == 1){
                                                        pendingList.add(new Doctor(firstName, lastName, email, password, phoneNumber, address, status, employeeNumber,healthCardNUmberOrSP, userId));
                                                }

                                        }
                                }

                                // Now that data is available, call the callback function
                                callback.onDataFetched(pendingList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                                // Handle any errors here
                        }
                });
        }

        public static String getUserId() {
                return tempUserId;
        }
        public static User getTempUser() {
                return tempUser;
        }
        public static void setUserId(String id) {
                tempUserId = id;
        }

        public static void setTempUser(User user) {
                tempUser = user;
        }

        // Define a callback interface
        public interface OnDataFetchedCallback {
                void onDataFetched(List<User> pendingList);
        }
        




}
