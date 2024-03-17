package com.example.laundrypersonnelmis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.laundrypersonnelmis.R;

import java.util.ArrayList;

public class Laundry_Mart_Adapter extends RecyclerView.Adapter<Laundry_Mart_Adapter.MyViewHolder> {
    private Context context;
    private ArrayList<laundryMart> martList;

    public Laundry_Mart_Adapter(Context context, ArrayList<laundryMart> martList) {
        this.context = context;
        this.martList = martList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.laundry_mart_adapter_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        laundryMart mart = martList.get(position);
        holder.name.setText(mart.getName());
        holder.email.setText(mart.getEmail());
        holder.phone.setText(mart.getPhone());
        holder.location.setText(mart.getLocation());
        Glide.with(context).load(mart.getImageUrl()).circleCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return martList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, phone, location;
        ImageView imageView;
        Button buttonSelectVendor; // Button added here

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.laundry_mart_name);
            email = itemView.findViewById(R.id.laundry_mart_email);
            phone = itemView.findViewById(R.id.laundry_mart_phone);
            location = itemView.findViewById(R.id.laundry_mart_location);
            imageView = itemView.findViewById(R.id.laundry_mart_image);
            buttonSelectVendor = itemView.findViewById(R.id.button_select_vendor); // Initialize the button
            buttonSelectVendor.setVisibility(View.VISIBLE);
        }
    }
}
