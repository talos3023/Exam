package com.example.exam.data;

import android.content.Context;
import android.content.SharedPreferences;

public class UserStorage extends Storage {
    private static String USER_FILE_NAME = "user_file_name";
    private static String LOGGED_USER = "logged_user";

    public User getLoggedUser(Context context) {
        return (User) getObject(context, LOGGED_USER, User.class);
    }

    public void saveLoggedUser(Context context, User user) {
        add(context, LOGGED_USER, user);
    }

    @Override
    public SharedPreferences getInstance(Context context) {
        return context.getSharedPreferences(USER_FILE_NAME, Context.MODE_PRIVATE);
    }
}
