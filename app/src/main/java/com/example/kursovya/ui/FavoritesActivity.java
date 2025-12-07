package com.example.kursovya.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursovya.R;
import com.example.kursovya.adapters.PetAdapter;
import com.example.kursovya.model.Pet;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerFavorites;
    private PetAdapter adapter;
    private ArrayList<Pet> favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerFavorites = findViewById(R.id.recyclerFavorites);
        favorites = (ArrayList<Pet>) getIntent().getSerializableExtra("favorites");
        if (favorites == null) favorites = new ArrayList<>();

        adapter = new PetAdapter(favorites, pet -> {
            // открываем профиль
            startActivity(PetProfileActivity.newIntent(this, pet));
        });

        recyclerFavorites.setLayoutManager(new LinearLayoutManager(this));
        recyclerFavorites.setAdapter(adapter);
    }
}

