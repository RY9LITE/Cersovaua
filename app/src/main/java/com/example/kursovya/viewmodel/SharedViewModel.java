package com.example.kursovya.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kursovya.model.Pet;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {

    public MutableLiveData<List<Pet>> favorites = new MutableLiveData<>(new ArrayList<>());

    public void addToFavorites(Pet pet) {
        List<Pet> list = favorites.getValue();
        list.add(pet);
        favorites.setValue(list);
    }
}
