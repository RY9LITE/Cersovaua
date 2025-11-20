package com.example.pawmatch.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseManager {
    private static FirebaseManager instance;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    private FirebaseManager(){
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public static synchronized FirebaseManager getInstance(){
        if(instance == null) instance = new FirebaseManager();
        return instance;
    }

    public FirebaseAuth getAuth(){ return auth; }
    public FirebaseUser getCurrentUser(){ return auth.getCurrentUser(); }
    public FirebaseFirestore getFirestore(){ return firestore; }
}
