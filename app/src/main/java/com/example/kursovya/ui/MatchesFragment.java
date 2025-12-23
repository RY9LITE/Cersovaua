package com.example.kursovya.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursovya.R;
import com.example.kursovya.adapters.PetAdapter;
import com.example.kursovya.viewmodel.SharedViewModel;

public class MatchesFragment extends Fragment {

    private RecyclerView recyclerMatches;
    private TextView tvEmpty;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_matches, container, false);

        recyclerMatches = v.findViewById(R.id.recyclerMatches);
        tvEmpty = v.findViewById(R.id.tvEmptyMatches);

        recyclerMatches.setLayoutManager(new LinearLayoutManager(getActivity()));

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.favorites.observe(getViewLifecycleOwner(), pets -> {
            if (pets == null || pets.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                recyclerMatches.setVisibility(View.GONE);
            } else {
                tvEmpty.setVisibility(View.GONE);
                recyclerMatches.setVisibility(View.VISIBLE);

                PetAdapter adapter = new PetAdapter(pets, pet -> {
                    Intent i = new Intent(getActivity(), PetProfileActivity.class);
                    i.putExtra("pet", pet);
                    startActivity(i);
                });

                recyclerMatches.setAdapter(adapter);
            }
        });

        return v;
    }
}
