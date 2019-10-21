package com.list.todo.ui.login;

import com.list.todo.ui.base.BaseView;

public class LoginContact {

    public interface Presenter {
        void login(String mail, String password);

        void signUp(String mail, String password);
    }

    public interface View extends BaseView {
        void onSuccessLogin();

        void onFailLogin();

        void onSuccessSignUp();
    }

}
