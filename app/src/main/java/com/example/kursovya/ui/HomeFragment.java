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
import android.widget.Toast;

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
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private RecyclerView recyclerPets;
    private PetAdapter adapter;
    private ArrayList<Pet> favorites = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerPets = v.findViewById(R.id.recyclerPets);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        adapter = new PetAdapter(createMockPets(), pet -> {
            Intent i = new Intent(getActivity(), PetProfileActivity.class);
            i.putExtra("pet", pet);
            startActivity(i);
        });

        recyclerPets.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerPets.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback callback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int pos = viewHolder.getAdapterPosition();
                        Pet swiped = adapter.removeAt(pos);

                        if (direction == ItemTouchHelper.RIGHT) {
                            sharedViewModel.addToFavorites(swiped);

                            Snackbar.make(recyclerPets,
                                            "Добавлено в избранное",
                                            Snackbar.LENGTH_LONG)
                                    .setAction("ОТМЕНИТЬ", v -> {
                                        sharedViewModel.removeFromFavorites(swiped);
                                    })
                                    .show();
                        }
                    }


                    @Override
                    public void onChildDraw(Canvas c,
                                            RecyclerView recyclerView,
                                            RecyclerView.ViewHolder viewHolder,
                                            float dX,
                                            float dY,
                                            int actionState,
                                            boolean isCurrentlyActive) {

                        View itemView = viewHolder.itemView;
                        Paint paint = new Paint();

                        if (dX > 0) {
                            paint.setColor(Color.parseColor("#4CAF50"));
                            c.drawRect(
                                    itemView.getLeft(),
                                    itemView.getTop(),
                                    itemView.getLeft() + dX,
                                    itemView.getBottom(),
                                    paint
                            );

                            Drawable icon = ContextCompat.getDrawable(
                                    getContext(),
                                    android.R.drawable.btn_star_big_on
                            );

                            int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                            int iconTop = itemView.getTop() + iconMargin;
                            int iconLeft = itemView.getLeft() + iconMargin;
                            int iconRight = iconLeft + icon.getIntrinsicWidth();
                            int iconBottom = iconTop + icon.getIntrinsicHeight();

                            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                            icon.draw(c);

                        } else if (dX < 0) {
                            paint.setColor(Color.parseColor("#F44336"));
                            c.drawRect(
                                    itemView.getRight() + dX,
                                    itemView.getTop(),
                                    itemView.getRight(),
                                    itemView.getBottom(),
                                    paint
                            );

                            Drawable icon = ContextCompat.getDrawable(
                                    getContext(),
                                    android.R.drawable.ic_menu_close_clear_cancel
                            );

                            int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                            int iconTop = itemView.getTop() + iconMargin;
                            int iconRight = itemView.getRight() - iconMargin;
                            int iconLeft = iconRight - icon.getIntrinsicWidth();
                            int iconBottom = iconTop + icon.getIntrinsicHeight();

                            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                            icon.draw(c);
                        }

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };

        new ItemTouchHelper(callback).attachToRecyclerView(recyclerPets);


        return v;
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
