package com.example.kursovya.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kursovya.R;
import com.example.kursovya.model.Pet;
import com.example.kursovya.viewmodel.SharedViewModel;

import java.util.UUID;

public class AddPetFragment extends Fragment {

    private Pet editingPet;
    private boolean isEditMode = false;

    private ImageView imgPet;
    private Uri selectedPhotoUri;

    private final ActivityResultLauncher<String> imagePicker =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            selectedPhotoUri = uri;
                            imgPet.setImageURI(uri);
                        }
                    }
            );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_pet, container, false);

        EditText etName = v.findViewById(R.id.etName);
        EditText etType = v.findViewById(R.id.etType);
        EditText etBreed = v.findViewById(R.id.etBreed);
        EditText etAge = v.findViewById(R.id.etAge);
        EditText etDesc = v.findViewById(R.id.etDesc);
        Button btnSave = v.findViewById(R.id.btnSave);
        Button btnPickImage = v.findViewById(R.id.btnPickImage);
        imgPet = v.findViewById(R.id.imgPet);

        SharedViewModel vm =
                new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        if (getArguments() != null && getArguments().containsKey("pet")) {
            editingPet = (Pet) getArguments().getSerializable("pet");
            isEditMode = true;

            etName.setText(editingPet.getName());
            etType.setText(editingPet.getType());
            etBreed.setText(editingPet.getBreed());
            etAge.setText(editingPet.getAge());
            etDesc.setText(editingPet.getDescription());

            if (editingPet.getPhotoUri() != null) {
                selectedPhotoUri = Uri.parse(editingPet.getPhotoUri());
                imgPet.setImageURI(selectedPhotoUri);
            }

            btnSave.setText("Сохранить изменения");
        }

        btnPickImage.setOnClickListener(v1 ->
                imagePicker.launch("image/*")
        );

        btnSave.setOnClickListener(view -> {
            if (isEditMode) {
                Pet updated = new Pet(
                        editingPet.getId(),
                        etName.getText().toString(),
                        etType.getText().toString(),
                        etBreed.getText().toString(),
                        etAge.getText().toString(),
                        etDesc.getText().toString(),
                        selectedPhotoUri != null
                                ? selectedPhotoUri.toString()
                                : editingPet.getPhotoUri()
                );
                vm.updatePet(updated);
            } else {
                Pet pet = new Pet(
                        UUID.randomUUID().toString(),
                        etName.getText().toString(),
                        etType.getText().toString(),
                        etBreed.getText().toString(),
                        etAge.getText().toString(),
                        etDesc.getText().toString(),
                        selectedPhotoUri != null
                                ? selectedPhotoUri.toString()
                                : null
                );
                vm.addPet(pet);
            }

            requireActivity()
                    .getSupportFragmentManager()
                    .popBackStack();
        });

        return v;
    }
}
