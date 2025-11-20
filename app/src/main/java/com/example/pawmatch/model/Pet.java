package com.example.pawmatch.model;

import java.util.List;

public class Pet {
    private String id;
    private String name;
    private String type;
    private String breed;
    private int age;
    private String description;
    private String shelterId;
    private List<String> photos;

    public Pet(){}

    public Pet(String id, String name, String type, String breed, int age, String description, String shelterId, List<String> photos) {
        this.id = id; this.name = name; this.type = type; this.breed = breed;
        this.age = age; this.description = description; this.shelterId = shelterId; this.photos = photos;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getShelterId() { return shelterId; }
    public void setShelterId(String shelterId) { this.shelterId = shelterId; }
    public List<String> getPhotos() { return photos; }
    public void setPhotos(List<String> photos) { this.photos = photos; }
}
