package com.example.kursovya.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kursovya.R;
import com.example.kursovya.ui.MainActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AuthManager auth = AuthManager.getInstance(this);


        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etRepeat = findViewById(R.id.etPasswordRepeat);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.register(email, pass);

            Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
            finish();
        });


        tvLogin.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class))
        );
    }
}
