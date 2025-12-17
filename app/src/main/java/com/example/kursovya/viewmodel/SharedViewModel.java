package com.example.kursovya.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.kursovya.data.PetRepository;
import com.example.kursovya.model.Pet;

import java.util.List;

public class SharedViewModel extends AndroidViewModel {

    private PetRepository repository;
    public MutableLiveData<List<Pet>> favorites = new MutableLiveData<>();

    public SharedViewModel(Application application) {
        super(application);
        repository = new PetRepository(application);
        loadFavorites();
    }

    public void addToFavorites(Pet pet) {
        repository.addPet(pet);
        loadFavorites();
    }

    public void removeFromFavorites(Pet pet) {
        repository.removePet(pet.getId());
        loadFavorites();
    }

    public void loadFavorites() {
        favorites.setValue(repository.getFavorites());
    }
}

