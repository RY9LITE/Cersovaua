package com.example.pawmatch.model;

import com.google.firebase.Timestamp;

public class Swipe {
    private String id;
    private String userId;
    private String petId;
    private boolean liked;
    private Timestamp createdAt;

    public Swipe(){}
    public Swipe(String userId, String petId, boolean liked, Timestamp createdAt){
        this.userId = userId; this.petId = petId; this.liked = liked; this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getPetId() { return petId; }
    public void setPetId(String petId) { this.petId = petId; }
    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
