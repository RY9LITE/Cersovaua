package com.example.kursovya.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "pets")
public class Pet implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;

    private String name;
    private String type;
    private String breed;
    private String age;
    private String description;

    private String photoUri;

    public Pet(@NonNull String id,
               String name,
               String type,
               String breed,
               String age,
               String description,
               String photoUri) {

        this.id = id;
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.age = age;
        this.description = description;
        this.photoUri = photoUri;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getBreed() {
        return breed;
    }

    public String getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoUri() {
        return photoUri;
    }
}
