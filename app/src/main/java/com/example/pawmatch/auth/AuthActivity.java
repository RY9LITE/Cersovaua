package com.example.pawmatch.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pawmatch.R;
import com.example.pawmatch.data.FirebaseManager;
import com.example.pawmatch.ui.MainActivity;

public class AuthActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister;

    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {
            String e = etEmail.getText().toString().trim();
            String p = etPassword.getText().toString().trim();
            if(e.isEmpty() || p.isEmpty()){ Toast.makeText(this,"Заполните поля",Toast.LENGTH_SHORT).show(); return; }
            FirebaseManager.getInstance().getAuth().signInWithEmailAndPassword(e,p)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Ошибка входа: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        });

        btnRegister.setOnClickListener(v -> {
            String e = etEmail.getText().toString().trim();
            String p = etPassword.getText().toString().trim();
            if(e.isEmpty() || p.length()<6){ Toast.makeText(this,"Пароль минимум 6 символов",Toast.LENGTH_SHORT).show(); return; }
            FirebaseManager.getInstance().getAuth().createUserWithEmailAndPassword(e,p)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Ошибка регистрации: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        });
    }
}
