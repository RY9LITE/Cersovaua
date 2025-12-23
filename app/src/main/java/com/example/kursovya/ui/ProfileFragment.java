package com.example.kursovya.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kursovya.R;
import com.example.kursovya.adapters.PetAdapter;
import com.example.kursovya.model.Pet;
import com.example.kursovya.viewmodel.SharedViewModel;
import com.google.android.material.snackbar.Snackbar;

import com.example.kursovya.auth.AuthManager;
import com.example.kursovya.auth.LoginActivity;


import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private static final String PREFS = "user_prefs";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_DESC = "user_desc";
    private static final String KEY_AVATAR = "user_avatar";

    private SharedViewModel sharedViewModel;
    private SharedPreferences prefs;

    private TextView tvUserName, tvUserDesc, tvFavoritesCount, tvEmptyFavorites;
    private ImageView imgAvatar;
    private RecyclerView recyclerFavorites;
    private PetAdapter adapter;

    private ActivityResultLauncher<String> imagePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        prefs = requireActivity().getSharedPreferences(PREFS, 0);

        imgAvatar = v.findViewById(R.id.imgAvatar);
        tvUserName = v.findViewById(R.id.tvUserName);
        tvUserDesc = v.findViewById(R.id.tvUserDesc);
        tvFavoritesCount = v.findViewById(R.id.tvFavoritesCount);
        tvEmptyFavorites = v.findViewById(R.id.tvEmptyFavorites);
        recyclerFavorites = v.findViewById(R.id.recyclerFavorites);
        Button btnEdit = v.findViewById(R.id.btnEditName);
        Button btnClear = v.findViewById(R.id.btnClearFavorites);
        Button btnLogout = v.findViewById(R.id.btnLogout);

        tvUserName.setText(prefs.getString(KEY_NAME, "Пользователь"));
        tvUserDesc.setText(prefs.getString(KEY_DESC, "Описание профиля"));

        String avatarUri = prefs.getString(KEY_AVATAR, null);
        if (avatarUri != null) {
            Glide.with(this)
                    .load(Uri.parse(avatarUri))
                    .into(imgAvatar);

        }

        imagePicker = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        Glide.with(this)
                                .load(uri)
                                .into(imgAvatar);

                        prefs.edit()
                                .putString(KEY_AVATAR, uri.toString())
                                .apply();
                    }
                });

        imgAvatar.setOnClickListener(v1 ->
                imagePicker.launch("image/*")
        );

        recyclerFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PetAdapter(new ArrayList<>(), pet -> {
            Intent i = new Intent(getActivity(), PetProfileActivity.class);
            i.putExtra("pet", pet);
            startActivity(i);
        });
        recyclerFavorites.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback swipeCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Pet removedPet = adapter.getItems().get(position);

                        sharedViewModel.removeFromFavorites(removedPet);

                        Snackbar.make(recyclerFavorites,
                                        "Удалено из избранного",
                                        Snackbar.LENGTH_LONG)
                                .setAction("ОТМЕНИТЬ", v ->
                                        sharedViewModel.addToFavorites(removedPet))
                                .show();
                    }

                    @Override
                    public void onChildDraw(Canvas c,
                                            RecyclerView recyclerView,
                                            RecyclerView.ViewHolder viewHolder,
                                            float dX, float dY,
                                            int actionState,
                                            boolean isCurrentlyActive) {

                        View itemView = viewHolder.itemView;

                        Paint paint = new Paint();
                        paint.setColor(Color.RED);

                        if (dX < 0) {
                            RectF background = new RectF(
                                    itemView.getRight() + dX,
                                    itemView.getTop(),
                                    itemView.getRight(),
                                    itemView.getBottom()
                            );
                            c.drawRect(background, paint);

                            Drawable icon = ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.ic_delete_white
                            );

                            int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                            int iconTop = itemView.getTop() + iconMargin;
                            int iconBottom = iconTop + icon.getIntrinsicHeight();
                            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                            int iconRight = itemView.getRight() - iconMargin;

                            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                            icon.draw(c);
                        }

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };

        new ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerFavorites);


        sharedViewModel = new ViewModelProvider(requireActivity())
                .get(SharedViewModel.class);

        sharedViewModel.favorites.observe(getViewLifecycleOwner(), pets -> {
            tvFavoritesCount.setText("Избранных: " + pets.size());

            if (pets.isEmpty()) {
                tvEmptyFavorites.setVisibility(View.VISIBLE);
                recyclerFavorites.setVisibility(View.GONE);
            } else {
                tvEmptyFavorites.setVisibility(View.GONE);
                recyclerFavorites.setVisibility(View.VISIBLE);
                adapter.updateList(pets);

            }
        });

        btnEdit.setOnClickListener(v12 -> showEditDialog());

        btnClear.setOnClickListener(v13 -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Подтверждение")
                    .setMessage("Очистить избранное?")
                    .setPositiveButton("Да", (d, w) ->
                            sharedViewModel.clearFavorites())
                    .setNegativeButton("Отмена", null)
                    .show();
        });

        btnLogout.setOnClickListener(v14 -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Выход")
                    .setMessage("Вы действительно хотите выйти?")
                    .setPositiveButton("Выйти", (dialog, which) -> {

                        AuthManager auth = new AuthManager(requireContext());
                        auth.logout();

                        Intent intent = new Intent(requireContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        requireActivity().finish();
                    })
                    .setNegativeButton("Отмена", null)
                    .show();
        });


        return v;
    }

    private void showEditDialog() {
        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_edit_profile, null);

        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etDesc = dialogView.findViewById(R.id.etDesc);

        etName.setText(tvUserName.getText());
        etDesc.setText(tvUserDesc.getText());

        new AlertDialog.Builder(getContext())
                .setTitle("Редактировать профиль")
                .setView(dialogView)
                .setPositiveButton("Сохранить", (d, w) -> {
                    String name = etName.getText().toString().trim();
                    String desc = etDesc.getText().toString().trim();

                    tvUserName.setText(name);
                    tvUserDesc.setText(desc);

                    prefs.edit()
                            .putString(KEY_NAME, name)
                            .putString(KEY_DESC, desc)
                            .apply();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}
