package com.examples.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddLostItem extends AppCompatActivity {

    //creating variables for our button, edit text,firebase database, database reference, progress bar.
    private Button addlostitembtn;
    private TextInputEditText lost_item_name,loser_name,loser_phone,loser_email,item_location,item_desc;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lost_item);
        //initializing all our variables.
        addlostitembtn= findViewById(R.id.idBtnAddLost);
        lost_item_name = findViewById(R.id.idEdtItemName);
        loser_name = findViewById(R.id.idEdtLoserName);
        loser_phone = findViewById(R.id.idEdtPhoneNumber);
        loser_email= findViewById(R.id.idEdtEmail);
        item_location= findViewById(R.id.idEdtLocation);
        item_desc = findViewById(R.id.idEdtItemDescription);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Lost Items");
        //adding click listener for our add course button.
        addlostitembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                //getting data from our edit text.
                String itemName = lost_item_name.getText().toString();
                String loserName =loser_name.getText().toString();
                String loserPhone = loser_phone.getText().toString();
                String loserEmail = loser_email.getText().toString();
                String itemLocation = item_location.getText().toString();
                String itemDesc = item_desc.getText().toString();
                itemID = itemName;
                //on below line we are passing all data to our modal class.
                LostRVModal lostRVModal = new LostRVModal();
                //on below line we are calling a add value event to pass data to firebase database.
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //on below line we are setting data in our firebase database.
                        databaseReference.child(itemID).setValue(lostRVModal);
                        //displaying a toast message.
                        Toast.makeText(AddLostItem.this, "Lost Item Added", Toast.LENGTH_SHORT).show();
                        //starting a main activity.
                        startActivity(new Intent(AddLostItem.this, LostActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //displaying a failure message on below line.
                        Toast.makeText(AddLostItem.this, "Fail to add item", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}