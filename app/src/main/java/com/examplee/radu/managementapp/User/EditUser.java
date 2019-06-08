package com.examplee.radu.managementapp.User;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;


import com.examplee.radu.managementapp.R;

import com.examplee.radu.managementapp.User.Details.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditUser extends AppCompatActivity {

    private static final String TAG = "ViewDatabase";


    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private DatabaseReference ref;
    //    private ListView mListView;
    String userID;

    TextView salary, project, title;
    EditText name, age;
    Button editUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be useable.
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        userID = user.getUid();
//        myRef = mFirebaseDatabase.getInstance().getReference().child("user").child(userID);
        myRef = mFirebaseDatabase.getReference().child("user");

        ref = mFirebaseDatabase.getReference("user");


        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        salary = (TextView) findViewById(R.id.salary);
        title = (TextView) findViewById(R.id.title);
        project = (TextView) findViewById(R.id.project);

        editUser = (Button) findViewById(R.id.updateUser);


        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });
    }

    private void updateUser() {
        final DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("user").child(userID);
        String nameText = name.getText().toString();
        String ageText = age.getText().toString();

        if(nameText.isEmpty())
        {
            name.setError("Name is requierd");
            name.requestFocus();
            return;
        }
        if(ageText.isEmpty())
        {
            age.setError("Name is requierd");
            age.requestFocus();
            return;
        }
        if(Integer.parseInt(ageText) < 20)
        {
            age.setError("Set your real age!");
            age.requestFocus();
            return;
        }
        Map<String,Object> hopperUpdates = new HashMap<>();
        hopperUpdates.put("name",nameText);
        hopperUpdates.put("age",ageText);
        mUserDB.updateChildren(hopperUpdates);

    }

    private void showData(DataSnapshot dataSnapshot) {
        UserInformation uInfo = new UserInformation();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Log.i("INFODS",ds.toString());
            if(ds.getKey().equals(userID))
            {

                uInfo.setName(ds.child("name").getValue().toString());
                uInfo.setAge(ds.child("age").getValue().toString());
                uInfo.setSalary(ds.child("salary").getValue().toString());
                uInfo.setTitle(ds.child("title").getValue().toString());
                uInfo.setProject(ds.child("project").getValue().toString());

                Log.i("INFO", uInfo.getName());
                Log.i("INFO", uInfo.getAge());
                Log.i("INFO", uInfo.getSalary());
                Log.i("INFO", uInfo.getTitle());
                Log.i("INFO", uInfo.getProject());
            }

            name.setText(uInfo.getName());
            age.setText(uInfo.getAge());
            salary.setText(uInfo.getSalary());
            title.setText(uInfo.getTitle());
            project.setText(uInfo.getProject());
        }
    }


}
