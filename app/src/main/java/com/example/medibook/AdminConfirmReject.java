package com.example.medibook;

import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class AdminConfirmReject extends AppCompatActivity {

    private Button acceptBtn, rejectBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_confirm_reject);

        createViews();

        acceptBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (AdminInbox.getUserId() != null) {
                    MainActivity.registrationRef.child(AdminInbox.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                MainActivity.userRef.child(AdminInbox.getUserId()).setValue(AdminInbox.getTempUser());
                                MainActivity.userRef.child(AdminInbox.getUserId()).child("status").setValue("Confirmed");
                                MainActivity.registrationRef.child(AdminInbox.getUserId()).removeValue();
                                }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                }
                if(AdminRejectedList.getUserId() != null) {
                    MainActivity.registrationRef.child(AdminRejectedList.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                MainActivity.userRef.child(AdminRejectedList.getUserId()).setValue(AdminRejectedList.getTempUser());
                                MainActivity.userRef.child(AdminRejectedList.getUserId()).child("status").setValue("Confirmed");
                                MainActivity.registrationRef.child(AdminRejectedList.getUserId()).removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                Intent a = new Intent(AdminConfirmReject.this, AdministratorInterface.class);
                startActivity(a);
            }

        });

        rejectBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                if(AdminInbox.getUserId() != null) {
                    MainActivity.registrationRef.child(AdminInbox.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                MainActivity.registrationRef.child(AdminInbox.getUserId()).child("status").setValue("rejected");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }

                Intent a = new Intent(AdminConfirmReject.this,AdministratorInterface.class);
                startActivity(a);
            }


        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a= new Intent(AdminConfirmReject.this,AdministratorInterface.class);
                startActivity(a);
            }
        });

    }











    public void createViews(){
        acceptBtn = findViewById(R.id.buttonAccept);
        rejectBtn = findViewById(R.id.buttonRejectAdmin);
        backBtn = findViewById(R.id.backBtn);


    }
}
