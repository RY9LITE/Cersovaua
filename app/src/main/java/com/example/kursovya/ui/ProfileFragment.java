package com.example.kursovya.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.kursovya.R;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        Button logout = v.findViewById(R.id.btnLogout);
        logout.setOnClickListener(view -> {
            // позже здесь будет FirebaseAuth.getInstance().signOut();
            // пока просто:
            System.out.println("Выход из профиля");
        });

        return v;
    }
}