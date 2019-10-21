package com.list.todo;

import android.app.Application;

import com.list.todo.model.User;

public class TodoApplication extends Application {

    private static TodoApplication todoApplication;
    private User user;

    @Override
    public void onCreate() {
        super.onCreate();
        todoApplication = this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static TodoApplication getContext() {
        return todoApplication;
    }
}
