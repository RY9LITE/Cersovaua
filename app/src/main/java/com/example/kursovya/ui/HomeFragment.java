package com.example.kursovya.ui;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.core.content.ContextCompat;
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

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private RecyclerView recyclerPets;
    private PetAdapter adapter;
    private List<Pet> allPets = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerPets = v.findViewById(R.id.recyclerPets);
        Spinner spinner = v.findViewById(R.id.spinnerFilter);
        Button btnAddPet = v.findViewById(R.id.btnAddPet);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        adapter = new PetAdapter(new ArrayList<>(), pet -> {

            Bundle bundle = new Bundle();
            bundle.putSerializable("pet", pet);

            AddPetFragment fragment = new AddPetFragment();
            fragment.setArguments(bundle);

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });


        recyclerPets.setLayoutManager(new StackLayoutManager());
        recyclerPets.setAdapter(adapter);

        sharedViewModel.pets.observe(getViewLifecycleOwner(), pets -> {
            allPets = pets;
            adapter.updateList(pets);
        });

        if (sharedViewModel.pets.getValue() == null ||
                sharedViewModel.pets.getValue().isEmpty()) {
            sharedViewModel.pets.setValue(createMockPets());
        }

        btnAddPet.setOnClickListener(view ->
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new AddPetFragment())
                        .addToBackStack(null)
                        .commit()
        );

        ArrayAdapter<CharSequence> spinnerAdapter =
                ArrayAdapter.createFromResource(
                        getContext(),
                        R.array.pet_filters,
                        android.R.layout.simple_spinner_item
                );

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilter(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ItemTouchHelper.SimpleCallback callback =
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

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

                        if (direction == ItemTouchHelper.RIGHT) {
                            sharedViewModel.addToFavorites(pet);

                            Snackbar.make(recyclerPets,
                                            "Добавлено в избранное",
                                            Snackbar.LENGTH_SHORT)
                                    .show();
                        }

                        if (direction == ItemTouchHelper.LEFT) {
                            sharedViewModel.removeFromFavorites(pet);

                            Snackbar.make(recyclerPets,
                                            "Удалено",
                                            Snackbar.LENGTH_SHORT)
                                    .show();
                        }

                        sharedViewModel.removePet(pet);
                        recyclerPets.post(() -> recyclerPets.requestLayout());
                    }


                    @Override
                    public void onChildDraw(Canvas c,
                                            RecyclerView recyclerView,
                                            RecyclerView.ViewHolder viewHolder,
                                            float dX, float dY,
                                            int actionState,
                                            boolean isCurrentlyActive) {

                        View itemView = viewHolder.itemView;
                        PetAdapter.ViewHolder holder = (PetAdapter.ViewHolder) viewHolder;

                        float progress = Math.min(1f, Math.abs(dX) / recyclerView.getWidth());

                        itemView.setRotation(dX / 20f);
                        itemView.setScaleX(1f - progress * 0.05f);
                        itemView.setScaleY(1f - progress * 0.05f);

                        Paint paint = new Paint();

                        if (dX > 0) {

                            paint.setColor(Color.parseColor("#4CAF50"));
                            c.drawRect(itemView.getLeft(), itemView.getTop(),
                                    itemView.getLeft() + dX, itemView.getBottom(), paint);

                            holder.imgLike.setAlpha(progress);
                            holder.imgDislike.setAlpha(0f);

                        } else if (dX < 0) {

                            paint.setColor(Color.parseColor("#F44336"));
                            c.drawRect(itemView.getRight() + dX, itemView.getTop(),
                                    itemView.getRight(), itemView.getBottom(), paint);

                            holder.imgDislike.setAlpha(progress);
                            holder.imgLike.setAlpha(0f);
                        }

                        super.onChildDraw(c, recyclerView, viewHolder,
                                dX, dY, actionState, isCurrentlyActive);
                    }


                };

        new ItemTouchHelper(callback).attachToRecyclerView(recyclerPets);


        return v;
    }

    private void applyFilter(String filter) {
        List<Pet> filtered = new ArrayList<>();
        for (Pet pet : allPets) {
            if (filter.equals("Все") || pet.getType().equalsIgnoreCase(filter)) {
                filtered.add(pet);
            }
        }
        adapter.updateList(filtered);
    }

    private List<Pet> createMockPets() {
        List<Pet> list = new ArrayList<>();

        list.add(new Pet("1", "Барсик", "Кот", "Дворовый", "2 года",
                "Добрый", "https://placekitten.com/800/500"));

        list.add(new Pet("2", "Мурка", "Кошка", "Сиамская", "1 год",
                "Игривая", "https://placekitten.com/801/500"));

        list.add(new Pet("3", "Шарик", "Собака", "Дворняга", "3 года",
                "Друг", "https://placedog.net/800/500"));

        return list;
    }
}
