package com.example.kursovya.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Patterns;

public class AuthManager {

    private static final String PREFS = "auth_prefs";
    private static final String USERS = "users";
    private static final String CURRENT_USER = "current_user";

    private final SharedPreferences prefs;

    public AuthManager(Context context) {
        prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public boolean isValidEmail(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public boolean register(String email, String password) {
        if (!isValidEmail(email) || !isValidPassword(password)) {
            return false;
        }

        SharedPreferences.Editor editor = prefs.edit();

        if (prefs.contains(USERS + "_" + email)) {
            return false;
        }

        editor.putString(USERS + "_" + email, password);
        editor.putString(CURRENT_USER, email);
        editor.apply();

        return true;
    }

    public boolean login(String email, String password) {
        String savedPassword = prefs.getString(USERS + "_" + email, null);

        if (savedPassword != null && savedPassword.equals(password)) {
            prefs.edit()
                    .putString(CURRENT_USER, email)
                    .apply();
            return true;
        }

        return false;
    }

    public boolean isLoggedIn() {
        return prefs.getString(CURRENT_USER, null) != null;
    }

    public String getCurrentUser() {
        return prefs.getString(CURRENT_USER, null);
    }

    public void logout() {
        prefs.edit().remove(CURRENT_USER).apply();
    }
}
