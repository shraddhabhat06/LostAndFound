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

public class LostRVAdapter extends RecyclerView.Adapter<LostRVAdapter.ViewHolder> {
    int lastPos=-1;
    private ArrayList<LostRVModal> lostRVModalArrayList;
    private Context context;
    private LostItemClickInterface lostItemClickInterface;

    public LostRVAdapter(ArrayList<LostRVModal> lostRVModalArrayList, Context context, LostItemClickInterface lostItemClickInterface) {
        this.lostRVModalArrayList = lostRVModalArrayList;
        this.context = context;
        this.lostItemClickInterface = lostItemClickInterface;
    }

    @NonNull
    @Override
    public LostRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lost_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull LostRVAdapter.ViewHolder holder,int position) {
        LostRVModal lostRVModal=lostRVModalArrayList.get(position);
        holder.lost_item_nametv.setText("Lost Item: "+lostRVModal.getItemname());
        holder.lost_item_loctv.setText("Date/Location: "+lostRVModal.getItemloc());
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lostItemClickInterface.onLostItemClick(position);
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
        return lostRVModalArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView lost_item_nametv,lost_item_loctv;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            lost_item_nametv = itemView.findViewById(R.id.idIVLostItemName);
            lost_item_loctv= itemView.findViewById(R.id.idIVLostLocation);

        }
    }
    public interface LostItemClickInterface{
        void onLostItemClick(int position);
    }

}
