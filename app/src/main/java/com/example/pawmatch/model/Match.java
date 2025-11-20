package com.example.pawmatch.model;

import com.google.firebase.Timestamp;

public class Match {
    private String id;
    private String userId;
    private String petId;
    private String shelterId;
    private Timestamp createdAt;

    public Match(){}
    public Match(String userId, String petId, String shelterId, Timestamp createdAt){
        this.userId=userId; this.petId=petId; this.shelterId=shelterId; this.createdAt=createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getPetId() { return petId; }
    public void setPetId(String petId) { this.petId = petId; }
    public String getShelterId() { return shelterId; }
    public void setShelterId(String shelterId) { this.shelterId = shelterId; }
    public com.google.firebase.Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(com.google.firebase.Timestamp createdAt) { this.createdAt = createdAt; }
}
