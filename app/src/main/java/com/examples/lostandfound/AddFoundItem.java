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

public class AddFoundItem extends AppCompatActivity {
    private TextInputEditText item_name, finder_name, finder_phone, finder_email, item_loc, item_desc;
    private Button add_item_btn;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String founditemid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_found_item);
        item_name=findViewById(R.id.idEdtItemName);
        finder_name=findViewById(R.id.idEdtFinderName);
        finder_phone=findViewById(R.id.idEdtPhoneNumber);
        finder_email=findViewById(R.id.idEdtEmail);
        item_loc=findViewById(R.id.idEdtLocation);
        item_desc=findViewById(R.id.idEdtItemDescription);
        add_item_btn=findViewById(R.id.idBtnAddFound);
        loadingPB=findViewById(R.id.idPBLoading);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Found_Items");
        add_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String itemname =item_name.getText().toString();
                String findername =finder_name.getText().toString();
                String finderphone=finder_phone.getText().toString();
                String finderemail =finder_email.getText().toString();
                String itemloc=item_loc.getText().toString();
                String itemdesc =item_desc.getText().toString();
                founditemid= itemname;
                FoundRVModal foundRVModal=new FoundRVModal(itemname,findername,finderphone,finderemail,itemloc,itemdesc,founditemid);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(founditemid).setValue(foundRVModal);
                        Toast.makeText(AddFoundItem.this,"Item Added Successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddFoundItem.this,FoundActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddFoundItem.this,"Error"+error.toString(),Toast.LENGTH_SHORT);
                    }
                });

            }
        });

    }


}

