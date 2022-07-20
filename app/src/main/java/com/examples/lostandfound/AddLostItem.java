package com.examples.lostandfound;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddLostItem extends AppCompatActivity {
    private TextInputEditText item_name, loser_name, loser_phone, loser_email, item_loc, item_desc;
    private Button add_item_btn;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String lostitemid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lost_item);
        item_name=findViewById(R.id.idEdtItemName);
        loser_name=findViewById(R.id.idEdtLoserName);
        loser_phone=findViewById(R.id.idEdtPhoneNumber);
        loser_email=findViewById(R.id.idEdtEmail);
        item_loc=findViewById(R.id.idEdtLocation);
        item_desc=findViewById(R.id.idEdtItemDescription);
        add_item_btn=findViewById(R.id.idBtnAddLost);
        loadingPB=findViewById(R.id.idPBLoading);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Lost_Items");
        add_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String itemname =item_name.getText().toString();
                String losername =loser_name.getText().toString();
                String loserphone=loser_phone.getText().toString();
                String loseremail =loser_email.getText().toString();
                String itemloc=item_loc.getText().toString();
                String itemdesc =item_desc.getText().toString();
                lostitemid= itemname;
                LostRVModal lostRVModal=new LostRVModal(itemname,losername,loserphone,loseremail,itemloc,itemdesc,lostitemid);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(lostitemid).setValue(lostRVModal);
                        Toast.makeText(AddLostItem.this,"Item Added Successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddLostItem.this,LostActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddLostItem.this,"Error"+error.toString(),Toast.LENGTH_SHORT);
                    }
                });

            }
        });

    }


}