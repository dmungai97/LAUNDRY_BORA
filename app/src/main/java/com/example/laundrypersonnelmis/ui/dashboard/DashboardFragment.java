package com.example.laundrypersonnelmis.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundrypersonnelmis.LaundryRequestAdapter;
import com.example.laundrypersonnelmis.databinding.FragmentDashboardBinding;
import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;
    private RecyclerView recyclerView;
    private LaundryRequestAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new LaundryRequestAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observeLaundryRequests();
        dashboardViewModel.fetchLaundryRequests();
    }

    private void observeLaundryRequests() {
        dashboardViewModel.getLaundryRequests().observe(getViewLifecycleOwner(),
                laundryRequests -> {
                    if (laundryRequests == null || laundryRequests.isEmpty()) {
                        Log.d("DashboardFragment", "No laundry requests available");
                        // Display a message when there are no requests
                        recyclerView.setVisibility(View.GONE); // Hide RecyclerView
                        binding.textViewNoRequests.setVisibility(View.VISIBLE); // Show TextView
                    } else {
                        Log.d("DashboardFragment", "Updating RecyclerView with laundry requests");
                        recyclerView.setVisibility(View.VISIBLE); // Show RecyclerView
                        binding.textViewNoRequests.setVisibility(View.GONE); // Hide TextView
                        adapter = new LaundryRequestAdapter(laundryRequests);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }


    // Other lifecycle methods...

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
