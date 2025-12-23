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

    public static class Builder {

        private final String id;
        private String name;
        private String type;
        private String breed;
        private String age;
        private String description;
        private String photoUri;

        public Builder(String id) {
            this.id = id;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setBreed(String breed) {
            this.breed = breed;
            return this;
        }

        public Builder setAge(String age) {
            this.age = age;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPhotoUri(String photoUri) {
            this.photoUri = photoUri;
            return this;
        }

        public Pet build() {
            return new Pet(id, name, type, breed, age, description, photoUri);
        }
    }

}
