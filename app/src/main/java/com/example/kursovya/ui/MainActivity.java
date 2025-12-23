package com.example.kursovya.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.kursovya.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        loadFragment(new HomeFragment());

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home)
                loadFragment(new HomeFragment());
            else if (item.getItemId() == R.id.nav_matches)
                loadFragment(new MatchesFragment());
            else if (item.getItemId() == R.id.nav_profile)
                loadFragment(new ProfileFragment());
            return true;
        });

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
