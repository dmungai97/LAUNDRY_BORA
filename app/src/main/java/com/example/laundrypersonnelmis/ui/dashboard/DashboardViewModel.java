package com.example.laundrypersonnelmis.ui.dashboard;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laundrypersonnelmis.LaundryRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<LaundryRequest>> laundryRequests;

    public DashboardViewModel() {
        laundryRequests = new MutableLiveData<>();
    }

    public LiveData<ArrayList<LaundryRequest>> getLaundryRequests() {
        return laundryRequests;
    }

    public void fetchLaundryRequests() {
        Log.d("DashboardViewModel", "Fetching laundry requests from Firebase...");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("laundry_mart");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<LaundryRequest> requests = new ArrayList<>();
                for (DataSnapshot martSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot ordersSnapshot = martSnapshot.child("Orders").child("ClientRequest");
                    for (DataSnapshot requestSnapshot : ordersSnapshot.getChildren()) {
                        LaundryRequest request = requestSnapshot.getValue(LaundryRequest.class);
                        requests.add(request);
                    }
                }
                laundryRequests.setValue(requests);
                Log.d("DashboardViewModel", "Laundry requests fetched from Firebase: " + requests.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DashboardViewModel", "Failed to fetch laundry requests from Firebase: " + databaseError.getMessage());
            }
        });
    }




    public void setLaundryRequests(ArrayList<LaundryRequest> requests) {
        laundryRequests.setValue(requests);
        Log.d("DashboardViewModel", "Laundry requests set in ViewModel: " + requests.size());
    }
}
