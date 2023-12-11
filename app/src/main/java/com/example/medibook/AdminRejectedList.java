package com.example.medibook;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.medibook.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class AdminRejectedList extends AppCompatActivity {

    private Button clickBack;
    private static String tempUserId;
    private static User tempUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rejected_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerRejectedUserInfo);

        fetchData(userList -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            AdminRejectedListAdapter adapter = new AdminRejectedListAdapter(this, userList);
            adapter.setOnClickListener(new AdminRejectedListAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    AdminInbox.setUserId(null);
                    AdminInbox.setTempUser(null);
                    tempUser = userList.get(position);
                    tempUserId = tempUser.getUserId();

                    Intent intent = new Intent(AdminRejectedList.this,AdminConfirmReject.class); //Change to the inbox class
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        });


    }

    private void fetchData(OnDataFetchedCallback callback) {
        List<User> rejectedList = new ArrayList<>();

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
                    String userID = snapshot1.child("userId").getValue(String.class);

                    if(status.equals("rejected")) {
                        if(password != null)
                            rejectedList.add(new User(firstName, lastName, email, password, phoneNumber, address, status, userID));
                    }
                }

                // Now that data is available, call the callback function
                callback.onDataFetched(rejectedList);
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
        void onDataFetched(List<User> rejectedList);
    }
}



