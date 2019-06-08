package com.examplee.radu.managementapp.ChatActivity;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.examplee.radu.managementapp.ChatActivity.User.UserListAdapter;
import com.examplee.radu.managementapp.ChatActivity.User.UserObject;
import com.examplee.radu.managementapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class FindUserActivity extends AppCompatActivity {
    //PART 2 - SHOW CONTACTS
    private RecyclerView mUserList;
    private RecyclerView.Adapter mUserListAdapter;
    private RecyclerView.LayoutManager mUserListLayoutManager;

    ArrayList<UserObject> contactList, userList;

    DatabaseReference reference;
    String uid;


    //PART 2 - SHOW CONTACTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

        //PART 2 - SHOW CONTACTS

        contactList = new ArrayList<>();
        //PART 4 - CHECK IF CONTACT IS ALSO AN USER
        userList = new ArrayList<>();
        //PART 4 - CHECK IF CONTACT IS ALSO AN USER
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        initialiseRecyclerView();


        Button mCreate = findViewById(R.id.create);
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChat();
                // nu este ortodox ce fac dar nu inteleg de ce imi reapar useri aici
                finish();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("user");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (!ds.getKey().equals(uid)) {
                        Log.i("CHILD", ds.getValue().toString());
                        UserObject mUser = new UserObject(ds.getKey(), ds.child("name").getValue().toString());
                        userList.add(mUser);
                        mUserListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createChat() {

        String key = FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();

        DatabaseReference chatInfoDb = FirebaseDatabase.getInstance().getReference().child("chat").child(key).child("info");
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("user");


        HashMap newChatMap = new HashMap();
        newChatMap.put("id", key);
        newChatMap.put("user/" + FirebaseAuth.getInstance().getUid(), true);

        Boolean validChat = false;


        for (UserObject mUser : userList) {
            if (mUser.getSelected()) {
                validChat = true;
                newChatMap.put("user/" + mUser.getUid(), true);
                userDb.child(mUser.getUid()).child("chat").child(key).setValue(true);

            }
        }
        if (validChat) {
            chatInfoDb.updateChildren(newChatMap);
            userDb.child(FirebaseAuth.getInstance().getUid()).child("chat").child(key).setValue(true);
        }
    }


    private void initialiseRecyclerView() {
        mUserList = (RecyclerView) findViewById(R.id.userList);
        //we are setting false in order to make the recycleview seems seamlessly
        mUserList.setNestedScrollingEnabled(false);
        //Size is not affected
        mUserList.setHasFixedSize(false);

        //Initialise layout manager
        //and set layout manager for userlist
        mUserListLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mUserList.setLayoutManager(mUserListLayoutManager);

        /*
        Pe urma cream clasele UserObject & UserListAdapter
         */
        //creating an userlist adapter
        //adn setting our userlistadapter
        mUserListAdapter = new UserListAdapter(userList);
        mUserList.setAdapter(mUserListAdapter);
    }
}

