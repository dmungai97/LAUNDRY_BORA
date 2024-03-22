package com.example.laundrypersonnelmis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.laundrypersonnelmis.LaundryRequest;
import java.util.ArrayList;

public class LaundryRequestAdapter extends RecyclerView.Adapter<LaundryRequestAdapter.ViewHolder> {

    private ArrayList<LaundryRequest> requests;

    public LaundryRequestAdapter(ArrayList<LaundryRequest> requests) {
        this.requests = requests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.laundry_request_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(requests.get(position));
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItem = itemView.findViewById(R.id.textViewItem);
        }

        public void bind(LaundryRequest request) {
            textViewItem.setText(request.toString());
        }
    }
}
