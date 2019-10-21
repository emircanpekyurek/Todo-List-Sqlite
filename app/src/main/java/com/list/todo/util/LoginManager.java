package com.list.todo.util;

import android.content.Context;

import com.list.todo.TodoApplication;
import com.list.todo.databases.DatabaseHelper;
import com.list.todo.enums.SignInInfo;
import com.list.todo.model.User;
import com.list.todo.ui.login.LoginActivity;
import com.list.todo.util.Constants;
import com.list.todo.util.SharedPrefsUtils;

import static com.list.todo.util.Constants.SharedPref.ID_KEY;
import static com.list.todo.util.Constants.SharedPref.ID_LOGOUT_VALUE;

public class LoginManager {

    public static User login(String mail, String password) {
        User user = DatabaseHelper.getInstance().getUserForLogin(mail, password);
        if (user != null) {
            SharedPrefsUtils.getInstance().putInt(ID_KEY, user.getId());
            TodoApplication.getContext().setUser(user);
        }
        return user;
    }

    public static void logout(Context context) {
        SharedPrefsUtils.getInstance().putInt(ID_KEY, ID_LOGOUT_VALUE);
        TodoApplication.getContext().setUser(null);
        LoginActivity.startActivity(context);
    }

    public static SignInInfo signUp(String mail, String password) {
        SignInInfo signUpInfo = DatabaseHelper.getInstance().insertNewUser(mail, password);
        if (signUpInfo == SignInInfo.SUCCESSFUL) {
            login(mail, password);
        }
        return signUpInfo;
    }

    public static boolean loginControl() {
        int userId = SharedPrefsUtils.getInstance().getInt(Constants.SharedPref.ID_KEY);
        if (userId == Constants.SharedPref.ID_LOGOUT_VALUE) {
            return false;
        } else {
            User user = DatabaseHelper.getInstance().getUserForLogin(userId);
            TodoApplication.getContext().setUser(user);
            return true;
        }
    }


}
