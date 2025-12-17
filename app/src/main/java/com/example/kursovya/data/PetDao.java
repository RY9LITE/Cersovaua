package com.example.kursovya.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.kursovya.model.Pet;

import java.util.List;

@Dao
public interface PetDao {

    @Insert
    void insert(Pet pet);

    @Query("SELECT * FROM pets")
    List<Pet> getAll();

    @Query("DELETE FROM pets WHERE id = :id")
    void deleteById(String id);

    @Query("DELETE FROM pets")
    void clear();
}

