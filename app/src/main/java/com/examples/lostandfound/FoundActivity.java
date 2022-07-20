package com.examples.lostandfound;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FoundActivity extends AppCompatActivity implements FoundRVAdapter.FoundItemClickInterface {
    private FloatingActionButton add_found_item_btn;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView foundRV;
    private ProgressBar loadingPB;
    private ArrayList<FoundRVModal> foundRVModalArrayList;
    private RelativeLayout bottomSheetRL;
    private FoundRVAdapter foundRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);
        foundRV=findViewById(R.id.idRVItems);
        loadingPB =findViewById(R.id.idPBLoading);
        add_found_item_btn=findViewById(R.id.idAddItembutton);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference("Found_Items");
        foundRVModalArrayList=new ArrayList<>();
        bottomSheetRL=findViewById(R.id.idRLBSheet1);
        mAuth = FirebaseAuth.getInstance();
        add_found_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoundActivity.this, AddFoundItem.class);
                startActivity(i);
            }
        });
        foundRVAdapter = new FoundRVAdapter(foundRVModalArrayList,this,this::onFoundItemClick);
        foundRV.setLayoutManager(new LinearLayoutManager(this));
        foundRV.setAdapter(foundRVAdapter);
        getAllFoundItems();

    }
    private void getAllFoundItems(){
        foundRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                foundRVModalArrayList.add(snapshot.getValue(FoundRVModal.class));
                foundRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                foundRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                foundRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                foundRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onFoundItemClick(int position) {
        displayBottomSheet(foundRVModalArrayList.get(position));


    }
    private void displayBottomSheet(FoundRVModal foundRVModal){
        final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this);
        View layout= LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog1,bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView founditemnameTV= layout.findViewById(R.id.idTVItemName);
        TextView findernameTV= layout.findViewById(R.id.idTVFinderName);
        TextView finderphoneTV= layout.findViewById(R.id.idTVFinderPhone);
        TextView finderemailTV= layout.findViewById(R.id.idTVFinderEmail);
        TextView founditemlocTV= layout.findViewById(R.id.idTVItemLoc);
        TextView founditemdescTV= layout.findViewById(R.id.idTVItemDesc);
        Button delfounditem=layout.findViewById(R.id.idBtnDelFoundItem);
        Button findercontact=layout.findViewById(R.id.idBtnEmail);

        founditemnameTV.setText("Found Item: "+foundRVModal.getItemname());
        findernameTV.setText("Owner's Name: "+foundRVModal.getFindername());
        finderphoneTV.setText("PhNo.: "+foundRVModal.getFinderphone());
        finderemailTV.setText("E-mail: "+foundRVModal.getFinderemail());
        founditemlocTV.setText("Date/Location: "+foundRVModal.getItemloc());
        founditemdescTV.setText("Description: "+foundRVModal.getItemdesc());
        delfounditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();

            }
        });
        findercontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendmail();
            }
        });

    }

    private void deleteItem() {
        //on below line calling a method to delete the course.
        databaseReference.removeValue();
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();

    }

    private void sendmail() {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose an email client"));



    }
}

