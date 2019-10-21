package com.list.todo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.list.todo.TodoApplication;

import static com.list.todo.util.Constants.SharedPref.ID_LOGOUT_VALUE;
import static com.list.todo.util.Constants.SharedPref.PREFS_NAME;

public class SharedPrefsUtils {
    private static SharedPrefsUtils instance;
    private static SharedPreferences sharedPreferences;

    private SharedPrefsUtils() { }

    public synchronized static SharedPrefsUtils getInstance() {
        if (instance == null || sharedPreferences == null) {
            instance = new SharedPrefsUtils();
            sharedPreferences = TodoApplication.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
        return instance;
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, ID_LOGOUT_VALUE);
    }

    public void putInt(String key, int value) {
        sharedPreferences
                .edit()
                .putInt(key, value)
                .apply();
    }
}