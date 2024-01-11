package com.example.laundrypersonnelmis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClienthistAdapter extends RecyclerView.Adapter<ClienthistAdapter.MyViewHolder> {


    Context context;
    ArrayList<Client_req_history> list;


        public ClienthistAdapter(Context context, ArrayList<Client_req_history> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.clienthist,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Client_req_history client_req_history = list.get(position);
            holder.name.setText(client_req_history.getName());

            holder.phone.setText(client_req_history.getPhone());

            holder.message.setText(client_req_history.getMessage());

        }

        @Override
        public int getItemCount() {

            return list.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder{

            TextView phone,name,message;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                phone=itemView.findViewById(R.id.phone);
                name=itemView.findViewById(R.id.name);

                message=itemView.findViewById(R.id.message);

            }
        }
    }


