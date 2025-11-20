package com.example.pawmatch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawmatch.R;
import com.example.pawmatch.data.FirebaseManager;
import com.example.pawmatch.data.PetRepository;
import com.example.pawmatch.model.Match;
import com.example.pawmatch.model.Pet;
import com.example.pawmatch.model.Swipe;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PetCardAdapter adapter;
    private List<Pet> pets = new ArrayList<>();
    private PetRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(FirebaseManager.getInstance().getAuth().getCurrentUser() == null){
            startActivity(new Intent(this, com.example.pawmatch.auth.AuthActivity.class));
            finish();
            return;
        }

        repo = new PetRepository();
        RecyclerView rv = findViewById(R.id.rvCards);
        adapter = new PetCardAdapter(pets);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) { return false; }

            @Override public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                Pet swiped = pets.get(pos);
                boolean liked = direction == ItemTouchHelper.RIGHT;
                handleSwipe(swiped, liked);
                pets.remove(pos);
                adapter.notifyItemRemoved(pos);
            }
        });
        helper.attachToRecyclerView(rv);

        loadPets();
    }

    private void loadPets(){
        repo.getAllPets(new PetRepository.PetsCallback() {
            @Override public void onSuccess(List<Pet> petsList) {
                runOnUiThread(() -> {
                    pets.clear();
                    pets.addAll(petsList);
                    adapter.notifyDataSetChanged();
                });
            }
            @Override public void onError(Exception e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Ошибка загрузки питомцев", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void handleSwipe(Pet pet, boolean liked){
        String uid = FirebaseManager.getInstance().getAuth().getCurrentUser().getUid();
        Swipe s = new Swipe(uid, pet.getId(), liked, Timestamp.now());
        FirebaseManager.getInstance().getFirestore()
                .collection("swipes")
                .add(s)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        if(liked){
                            Match m = new Match(uid, pet.getId(), pet.getShelterId(), Timestamp.now());
                            FirebaseManager.getInstance().getFirestore().collection("matches").add(m);
                            Toast.makeText(MainActivity.this, "Вы лайкнули " + pet.getName(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Пропущено", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Ошибка сохранения свайпа", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
