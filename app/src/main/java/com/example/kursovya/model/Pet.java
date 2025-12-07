package com.example.kursovya.model;

import java.io.Serializable;
import java.util.List;

public class Pet implements Serializable {
    private String id;
    private String name;
    private String type;
    private String breed;
    private String age;
    private String description;
    private List<String> photos;

    public Pet() {}

    public Pet(String id, String name, String type, String breed, String age, String description, List<String> photos) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.age = age;
        this.description = description;
        this.photos = photos;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getBreed() { return breed; }
    public String getAge() { return age; }
    public String getDescription() { return description; }
    public List<String> getPhotos() { return photos; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setBreed(String breed) { this.breed = breed; }
    public void setAge(String age) { this.age = age; }
    public void setDescription(String description) { this.description = description; }
    public void setPhotos(List<String> photos) { this.photos = photos; }
}

