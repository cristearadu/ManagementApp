package com.examplee.radu.managementapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.examplee.radu.managementapp.ChatActivity.MainChatActivity;
import com.examplee.radu.managementapp.LoginAndSignUp.MainActivity;
import com.examplee.radu.managementapp.Ticket.ReceiveTicket;
import com.examplee.radu.managementapp.Ticket.SendTicket;
import com.examplee.radu.managementapp.User.Details.UserInformation;
import com.examplee.radu.managementapp.User.EditUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

//TODO Delete Salary: Confidential
//TODO Description: zile libere
//TODO Change chat name
//TODO Termin Ticket: adaug user si ID
//TODO P.M. adds project
//TODO P.M. -> Task
//TODO Pontaj
//TODO Notes button + save notes


public class MainMenuActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button buttonChat, logout, editProfile, tickets;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference ref;
    String uid;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();

            Toast.makeText(getApplicationContext(), "hello " + uid, Toast.LENGTH_SHORT).show();
        }

        buttonChat = (Button) findViewById(R.id.buttonChat);

        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainChatActivity.class);
                startActivity(intent);
            }
        });

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "LOGGED OUT", Toast.LENGTH_SHORT).show();

                OneSignal.setSubscription(false);
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });

        editProfile = (Button) findViewById(R.id.buttonEdit);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditUser.class);
                startActivity(intent);
            }
        });

        tickets = (Button) findViewById(R.id.ticket);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        ref = mFirebaseDatabase.getReference("user");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                checkTitle(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.equals("Admin"))
                {
                    Intent intent = new Intent(getApplicationContext(), ReceiveTicket.class);
                    startActivity(intent);
                }
                else if (!title.equals("Admin"))
                {
                    Intent intent = new Intent(getApplicationContext(), SendTicket.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void checkTitle(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren())
        {
            if(ds.getKey().equals(uid))
            {
                title = ds.child("title").getValue().toString();
            }
        }

        switch (title)
        {
            case "Developer":
                break;
            case "Admin":
                tickets.setText("Tickets");
                break;
            case "HR":
                break;
            case "Project Manager":
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "LOGGED OUT", Toast.LENGTH_SHORT).show();

        OneSignal.setSubscription(false);
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return;
    }
}
