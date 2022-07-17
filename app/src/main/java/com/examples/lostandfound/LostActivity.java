package com.examples.lostandfound;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LostActivity extends AppCompatActivity implements LostRVAdapter.LostItemClickInterface {
    private RecyclerView lostRV;
    private ProgressBar loadingPB;
    private FloatingActionButton add_item_btn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<LostRVModal> lostRVModalArrayList;
    private RelativeLayout bottomSheetRL;
    private LostRVAdapter lostRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        lostRV=findViewById(R.id.idRVItems);
        loadingPB =findViewById(R.id.idPBLoading);
        add_item_btn=findViewById(R.id.idAddItembutton);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference("Lost_Items");
        lostRVModalArrayList=new ArrayList<>();
        lostRVAdapter = new LostRVAdapter(lostRVModalArrayList,this,this);
        lostRV.setLayoutManager(new LinearLayoutManager(this));
        lostRV.setAdapter(lostRVAdapter);
        add_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LostActivity.this,AddLostItem.class));
            }
        });
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
        Button editlostitem=layout.findViewById(R.id.idBtnEditLostItem);
        Button losercontact=layout.findViewById(R.id.idBtnEmail);

        lostitemnameTV.setText(lostRVModal.getItemname());
        losernameTV.setText(lostRVModal.getLosername());
        loserphoneTV.setText(lostRVModal.getLoserphone());
        loseremailTV.setText(lostRVModal.getLoseremail());
        lostitemlocTV.setText(lostRVModal.getItemloc());
        lostitemdescTV.setText(lostRVModal.getItemdesc());
        editlostitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i=new Intent(LostActivity.this,EditLostItem.class);
               i.putExtra("lostitems",lostRVModal);
               startActivity(i);
            }
        });
        losercontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendmail();
            }
        });

    }

    private void sendmail() {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose an email client"));



    }
}