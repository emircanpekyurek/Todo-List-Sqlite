package com.list.todo.ui.splash;

import com.list.todo.util.LoginManager;

public class SplashPresenter implements SplashContact.Presenter {

    private SplashContact.View view;

    SplashPresenter(SplashContact.View view) {
        this.view = view;
    }

    @Override
    public void loginControl() {
        if (LoginManager.loginControl()) {
            view.openTodoListActivity();
        } else {
            view.openLoginActivity();
        }
    }
}
