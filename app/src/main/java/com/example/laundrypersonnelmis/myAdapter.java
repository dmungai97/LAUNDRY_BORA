package com.example.laundrypersonnelmis;

import static java.lang.Float.parseFloat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {
    Context context;
    ArrayList<Servicemenhelper>list;

    public myAdapter(Context context, ArrayList<Servicemenhelper> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.serviceman,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Servicemenhelper servicemenhelper = list.get(position);
        holder.name.setText(servicemenhelper.getName());
        holder.email.setText(servicemenhelper.getPassword());
        holder.phone.setText(servicemenhelper.getPhone());
        holder.location.setText(servicemenhelper.getLocation());
        holder.ratingBar.setRating(servicemenhelper.getRating());
        Glide.with(context).load(servicemenhelper.getImageUri()).circleCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

TextView phone,name,email,location,ratingk;
RatingBar ratingBar;
ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            phone=itemView.findViewById(R.id.phone);
            name=itemView.findViewById(R.id.name);
            email=itemView.findViewById(R.id.email);
            location=itemView.findViewById(R.id.location);
            ratingBar=itemView.findViewById(R.id.rating);
            imageView=itemView.findViewById(R.id.servicemanimg);

        }
    }
}
