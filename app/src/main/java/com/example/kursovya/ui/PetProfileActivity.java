package com.example.kursovya.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.kursovya.R;
import com.example.kursovya.model.Pet;

import java.util.List;

public class PetProfileActivity extends AppCompatActivity {

    public static Intent newIntent(Context ctx, Pet pet) {
        Intent i = new Intent(ctx, PetProfileActivity.class);
        i.putExtra("pet", pet);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        ImageView img = findViewById(R.id.imgProfile);
        TextView tvName = findViewById(R.id.tvProfileName);
        TextView tvDetails = findViewById(R.id.tvProfileDetails);
        TextView tvDesc = findViewById(R.id.tvProfileDesc);

        Pet pet = (Pet) getIntent().getSerializableExtra("pet");
        if (pet != null) {
            tvName.setText(pet.getName());
            tvDetails.setText(pet.getType() + " • " + pet.getBreed() + " • " + pet.getAge());
            tvDesc.setText(pet.getDescription());

            Glide.with(this)
                    .load(pet.getPhotoUrl())
                    .into(img);
        }
    }

}

