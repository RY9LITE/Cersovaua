package com.example.pawmatch.data;

import androidx.annotation.NonNull;

import com.example.pawmatch.model.Pet;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PetRepository {
    private FirebaseFirestore db;
    private CollectionReference petsRef;

    public PetRepository(){
        db = FirebaseManager.getInstance().getFirestore();
        petsRef = db.collection("pets");
    }

    public interface PetsCallback {
        void onSuccess(List<Pet> pets);
        void onError(Exception e);
    }

    public void getAllPets(final PetsCallback callback){
        petsRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                QuerySnapshot snap = task.getResult();
                List<Pet> list = new ArrayList<>();
                for(var doc : snap.getDocuments()){
                    Pet p = doc.toObject(Pet.class);
                    if(p!=null){
                        p.setId(doc.getId());
                        list.add(p);
                    }
                }
                callback.onSuccess(list);
            } else {
                callback.onError(task.getException());
            }
        });
    }
}
