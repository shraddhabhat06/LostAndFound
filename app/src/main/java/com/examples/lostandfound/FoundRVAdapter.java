package com.examples.lostandfound;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoundRVAdapter extends RecyclerView.Adapter<FoundRVAdapter.ViewHolder> {
    int lastPos=-1;
    private ArrayList<FoundRVModal> foundRVModalArrayList;
    private Context context;
    private FoundItemClickInterface foundItemClickInterface;

    public FoundRVAdapter(ArrayList<FoundRVModal> foundRVModalArrayList, Context context, FoundItemClickInterface foundItemClickInterface) {
        this.foundRVModalArrayList = foundRVModalArrayList;
        this.context = context;
        this.foundItemClickInterface = foundItemClickInterface;
    }

    @NonNull
    @Override
    public FoundRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.found_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull FoundRVAdapter.ViewHolder holder,int position) {
        FoundRVModal foundRVModal=foundRVModalArrayList.get(position);
        holder.found_item_nametv.setText("Found Item: "+foundRVModal.getItemname());
        holder.found_item_loctv.setText("Date/Location: "+foundRVModal.getItemloc());
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foundItemClickInterface.onFoundItemClick(position);
            }
        });

    }
    private void setAnimation(View itemView,int position){
        if(position>lastPos){
            Animation animation= AnimationUtils.loadAnimation(context , android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos=position;
        }

    }

    @Override
    public int getItemCount() {
        return foundRVModalArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView found_item_nametv,found_item_loctv;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            found_item_nametv = itemView.findViewById(R.id.idIVFoundItemName);
            found_item_loctv= itemView.findViewById(R.id.idIVFoundLocation);

        }
    }
    public interface FoundItemClickInterface{
        void onFoundItemClick(int position);
    }

}

