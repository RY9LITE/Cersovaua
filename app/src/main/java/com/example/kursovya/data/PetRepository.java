package com.example.kursovya.data;

import android.content.Context;

import com.example.kursovya.model.Pet;

import java.util.List;

public class PetRepository {

    private PetDao petDao;

    public PetRepository(Context context) {
        petDao = AppDatabase.getInstance(context).petDao();
    }

    public void addPet(Pet pet) {
        petDao.insert(pet);
    }

    public List<Pet> getFavorites() {
        return petDao.getAll();
    }

    public void removePet(String id) {
        petDao.deleteById(id);
    }
}

