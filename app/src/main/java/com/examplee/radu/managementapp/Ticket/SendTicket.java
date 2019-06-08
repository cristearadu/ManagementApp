package com.examplee.radu.managementapp.Ticket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.examplee.radu.managementapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SendTicket extends AppCompatActivity {
    EditText text, ticketName;
    Button send;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String title;

    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_ticket);

        user = FirebaseAuth.getInstance().getCurrentUser();

        text = (EditText) findViewById(R.id.editText);
        ticketName = (EditText) findViewById(R.id.ticketName);
        send = (Button) findViewById(R.id.send);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTicket();
            }
        });

    }

    private void sendTicket() {

        //TODO ca la send message
        //TODO Adaugam numele si ID-ul
        //TODO Adaugam problema
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        title = radioButton.getText().toString();

        String ticketNameMessage = ticketName.getText().toString();
        String ticketDescription = text.getText().toString();

        final DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("ticket").child(ticketNameMessage);

        if (ticketNameMessage.isEmpty()) {
            ticketName.setError("Write a ticket name");
            ticketName.requestFocus();
            return;
        }
        if (ticketDescription.isEmpty()) {
            text.setError("Write something about the ticket");
            text.requestFocus();
            return;
        }

        Ticket ticket = new Ticket(ticketNameMessage, title, ticketDescription);
        //TODO ADD NAME
        Map<String, Object> ticketMap = new HashMap<>();
        ticketMap.put("ticket", ticketNameMessage);

        mUserDB.updateChildren(ticketMap);
        mUserDB.setValue(ticket);

        Toast.makeText(getApplicationContext(), "Your ticket has been sent to the admin", Toast.LENGTH_SHORT).show();

        finish();
    }
}
