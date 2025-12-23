package com.example.kursovya.auth;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthManager {

    private static AuthManager instance;
    private final SharedPreferences prefs;

    private static final String PREFS = "auth_prefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_LOGGED = "logged";

    private AuthManager(Context context) {
        prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public static AuthManager getInstance(Context context) {
        if (instance == null) {
            instance = new AuthManager(context.getApplicationContext());
        }
        return instance;
    }

    public boolean login(String email, String password) {
        String savedEmail = prefs.getString(KEY_EMAIL, null);
        String savedPass = prefs.getString(KEY_PASSWORD, null);

        if (email.equals(savedEmail) && password.equals(savedPass)) {
            prefs.edit().putBoolean(KEY_LOGGED, true).apply();
            return true;
        }
        return false;
    }

    public void register(String email, String password) {
        prefs.edit()
                .putString(KEY_EMAIL, email)
                .putString(KEY_PASSWORD, password)
                .apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED, false);
    }

    public void logout() {
        prefs.edit().putBoolean(KEY_LOGGED, false).apply();
    }
}

