package com.example.kursovya.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.kursovya.model.Pet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends AndroidViewModel {

    private static final String PREFS = "app_storage";
    private static final String KEY_PETS = "all_pets";
    private static final String KEY_FAVORITES = "favorites";

    public MutableLiveData<List<Pet>> pets =
            new MutableLiveData<>(new ArrayList<>());

    public MutableLiveData<List<Pet>> favorites =
            new MutableLiveData<>(new ArrayList<>());

    private final Application app;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        this.app = application;
        loadPets();
        loadFavorites();
    }


    public void addPet(Pet pet) {
        List<Pet> list = new ArrayList<>(pets.getValue());
        list.add(pet);
        pets.setValue(list);
        savePets();
    }

    public void removePet(Pet pet) {
        List<Pet> list = new ArrayList<>(pets.getValue());
        list.remove(pet);
        pets.setValue(list);
        savePets();
    }

    private void savePets() {
        SharedPreferences prefs =
                app.getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        prefs.edit()
                .putString(KEY_PETS, gson.toJson(pets.getValue()))
                .apply();
    }

    private void loadPets() {
        SharedPreferences prefs =
                app.getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        String json = prefs.getString(KEY_PETS, null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Pet>>() {}.getType();
            pets.setValue(gson.fromJson(json, type));
        }
    }

    public void addToFavorites(Pet pet) {
        List<Pet> list = new ArrayList<>(favorites.getValue());
        if (!list.contains(pet)) {
            list.add(pet);
            favorites.setValue(list);
            saveFavorites();
        }
    }

    public void removeFromFavorites(Pet pet) {
        List<Pet> list = new ArrayList<>(favorites.getValue());
        list.remove(pet);
        favorites.setValue(list);
        saveFavorites();
    }

    public void clearFavorites() {
        favorites.setValue(new ArrayList<>());
        saveFavorites();
    }

    private void saveFavorites() {
        SharedPreferences prefs =
                app.getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        prefs.edit()
                .putString(KEY_FAVORITES, gson.toJson(favorites.getValue()))
                .apply();
    }

    private void loadFavorites() {
        SharedPreferences prefs =
                app.getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        String json = prefs.getString(KEY_FAVORITES, null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Pet>>() {}.getType();
            favorites.setValue(gson.fromJson(json, type));
        }
    }

    public void updatePet(Pet updatedPet) {
        List<Pet> list = new ArrayList<>(pets.getValue());

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(updatedPet.getId())) {
                list.set(i, updatedPet);
                break;
            }
        }

        pets.setValue(list);
        savePets();
    }

}
