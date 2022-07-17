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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditLostItem extends AppCompatActivity {
    private TextInputEditText item_name, loser_name, loser_phone, loser_email, item_loc, item_desc;
    private Button edit_item_btn,delete_item_btn;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String lostitemid;
    private LostRVModal lostRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lost_item);
        firebaseDatabase=FirebaseDatabase.getInstance();
        item_name=findViewById(R.id.idEdtItemName);
        loser_name=findViewById(R.id.idEdtLoserName);
        loser_phone=findViewById(R.id.idEdtPhoneNumber);
        loser_email=findViewById(R.id.idEdtEmail);
        item_loc=findViewById(R.id.idEdtLocation);
        item_desc=findViewById(R.id.idEdtItemDescription);
        edit_item_btn=findViewById(R.id.idBtnEditLost);
        delete_item_btn=findViewById(R.id.idBtnDeleteLost);
        loadingPB=findViewById(R.id.idPBLoading);
        lostRVModal=getIntent().getParcelableExtra("lostitems");
        if(lostRVModal!=null){
            item_name.setText(lostRVModal.getItemname());
            loser_name.setText(lostRVModal.getLosername());
            loser_phone.setText(lostRVModal.getLoserphone());
            loser_email.setText(lostRVModal.getLoseremail());
            item_loc.setText(lostRVModal.getItemloc());
            item_desc.setText(lostRVModal.getItemdesc());
            lostitemid=lostRVModal.getLostitemid();
        }


        databaseReference=firebaseDatabase.getReference("Lost_Items").child(lostitemid);
        edit_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String itemname =item_name.getText().toString();
                String losername =loser_name.getText().toString();
                String loserphone=loser_phone.getText().toString();
                String loseremail =loser_email.getText().toString();
                String itemloc=item_loc.getText().toString();
                String itemdesc =item_desc.getText().toString();

                Map<String,Object>map=new HashMap<>();
                map.put("itemname",item_name);
                map.put("losername",loser_name);
                map.put("loserphone",loser_phone);
                map.put("loseremail",loser_email);
                map.put("itemloc",item_loc);
                map.put("itemdesc",item_desc);
                map.put("lostitemid",lostitemid);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility((View.GONE));
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditLostItem.this,"Item Updated Successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditLostItem.this,LostActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditLostItem.this,"Failed to update",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        delete_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });

    }
    private void deleteItem(){
        databaseReference.removeValue();
        Toast.makeText(EditLostItem.this,"Item DeletedSuccessfully",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditLostItem.this,LostActivity.class));
    }
}