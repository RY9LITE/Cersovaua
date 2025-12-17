package com.example.kursovya.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursovya.R;
import com.example.kursovya.adapters.PetAdapter;
import com.example.kursovya.model.Pet;
import com.example.kursovya.viewmodel.SharedViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerFavorites;
    private TextView tvEmpty;
    private PetAdapter adapter;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerFavorites = v.findViewById(R.id.recyclerFavorites);
        tvEmpty = v.findViewById(R.id.tvEmpty);

        recyclerFavorites.setLayoutManager(new LinearLayoutManager(getActivity()));

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.favorites.observe(getViewLifecycleOwner(), pets -> {
            if (pets == null || pets.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                recyclerFavorites.setVisibility(View.GONE);
            } else {
                tvEmpty.setVisibility(View.GONE);
                recyclerFavorites.setVisibility(View.VISIBLE);

                adapter = new PetAdapter(pets, pet -> {
                    Intent i = new Intent(getActivity(), PetProfileActivity.class);
                    i.putExtra("pet", pet);
                    startActivity(i);
                });

                recyclerFavorites.setAdapter(adapter);
            }
        });

        ItemTouchHelper.SimpleCallback callback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int pos = viewHolder.getAdapterPosition();
                        Pet pet = adapter.getItems().get(pos);

                        sharedViewModel.removeFromFavorites(pet);

                        Snackbar.make(recyclerFavorites,
                                        "Удалено из избранного",
                                        Snackbar.LENGTH_LONG)
                                .setAction("ОТМЕНИТЬ", v -> {
                                    sharedViewModel.addToFavorites(pet);
                                })
                                .show();
                    }

                };

        new ItemTouchHelper(callback).attachToRecyclerView(recyclerFavorites);

        return v;
    }
}

