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

        AuthManager auth = new AuthManager(this);

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etRepeat = findViewById(R.id.etPasswordRepeat);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString();
            String repeat = etRepeat.getText().toString();

            if (!pass.equals(repeat)) {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean ok = auth.register(email, pass);

            if (ok) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(
                        this,
                        "Ошибка регистрации (email или пароль)",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        tvLogin.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class))
        );
    }
}
