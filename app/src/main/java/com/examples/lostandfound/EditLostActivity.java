package com.examples.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditLostActivity extends AppCompatActivity {
    private Button editlostitembtn,deletelostitembtn;
    private TextInputEditText lost_item_name,loser_name,loser_phone,loser_email,item_location,item_desc;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String itemID;
    private LostRVModal lostRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lost);
        deletelostitembtn= findViewById(R.id.idBtnDeleteLost);
        editlostitembtn= findViewById(R.id.idBtnEditLost);
        lost_item_name = findViewById(R.id.idEdtItemName);
        loser_name = findViewById(R.id.idEdtLoserName);
        loser_phone = findViewById(R.id.idEdtPhoneNumber);
        loser_email= findViewById(R.id.idEdtEmail);
        item_location= findViewById(R.id.idEdtLocation);
        item_desc = findViewById(R.id.idEdtItemDescription);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase=firebaseDatabase.getInstance();
        lostRVModal=getIntent().getParcelableExtra("lostitems");
        if(lostRVModal!=null){
            lost_item_name.setText(lostRVModal.getItemName());

        }



        databaseReference=firebaseDatabase.getReference("Lost Items").child(itemID);
    }
}