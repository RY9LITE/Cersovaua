package com.example.kursovya.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursovya.R;
import com.example.kursovya.adapters.PetAdapter;
import com.example.kursovya.model.Pet;
import com.example.kursovya.viewmodel.SharedViewModel;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerFavorites;
    private PetAdapter adapter;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerFavorites = v.findViewById(R.id.recyclerFavorites);
        recyclerFavorites.setLayoutManager(new LinearLayoutManager(getActivity()));

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.favorites.observe(getViewLifecycleOwner(), pets -> {
            adapter = new PetAdapter(pets, pet -> {
                Intent i = new Intent(getActivity(), PetProfileActivity.class);
                i.putExtra("pet", pet);
                startActivity(i);
            });
            recyclerFavorites.setAdapter(adapter);
        });

        return v;
    }
}
