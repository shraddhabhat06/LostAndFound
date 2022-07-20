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

public class LostActivity extends AppCompatActivity implements LostRVAdapter.LostItemClickInterface {
    private FloatingActionButton add_lost_item_btn;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView lostRV;
    private ProgressBar loadingPB;
    private ArrayList<LostRVModal> lostRVModalArrayList;
    private RelativeLayout bottomSheetRL;
    private LostRVAdapter lostRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        lostRV=findViewById(R.id.idRVItems);
        loadingPB =findViewById(R.id.idPBLoading);
        add_lost_item_btn=findViewById(R.id.idAddItembutton);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference("Lost_Items");
        lostRVModalArrayList=new ArrayList<>();
        bottomSheetRL=findViewById(R.id.idRLBSheet);
        mAuth = FirebaseAuth.getInstance();
        add_lost_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LostActivity.this, AddLostItem.class);
                startActivity(i);
            }
        });
        lostRVAdapter = new LostRVAdapter(lostRVModalArrayList,this,this::onLostItemClick);
        lostRV.setLayoutManager(new LinearLayoutManager(this));
        lostRV.setAdapter(lostRVAdapter);
        getAllLostItems();

    }
    private void getAllLostItems(){
        lostRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                lostRVModalArrayList.add(snapshot.getValue(LostRVModal.class));
                lostRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                lostRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                lostRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                lostRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onLostItemClick(int position) {
        displayBottomSheet(lostRVModalArrayList.get(position));


    }
    private void displayBottomSheet(LostRVModal lostRVModal){
        final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this);
        View layout= LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView lostitemnameTV= layout.findViewById(R.id.idTVItemName);
        TextView losernameTV= layout.findViewById(R.id.idTVLoserName);
        TextView loserphoneTV= layout.findViewById(R.id.idTVLoserPhone);
        TextView loseremailTV= layout.findViewById(R.id.idTVLoserEmail);
        TextView lostitemlocTV= layout.findViewById(R.id.idTVItemLoc);
        TextView lostitemdescTV= layout.findViewById(R.id.idTVItemDesc);
        Button dellostitem=layout.findViewById(R.id.idBtnDelLostItem);
        Button losercontact=layout.findViewById(R.id.idBtnEmail);

        lostitemnameTV.setText("Lost Item: "+lostRVModal.getItemname());
        losernameTV.setText("Owner's Name: "+lostRVModal.getLosername());
        loserphoneTV.setText("PhNo.: "+lostRVModal.getLoserphone());
        loseremailTV.setText("E-mail: "+lostRVModal.getLoseremail());
        lostitemlocTV.setText("Date/Location: "+lostRVModal.getItemloc());
        lostitemdescTV.setText("Description: "+lostRVModal.getItemdesc());
        dellostitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();

            }
        });
        losercontact.setOnClickListener(new View.OnClickListener() {
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