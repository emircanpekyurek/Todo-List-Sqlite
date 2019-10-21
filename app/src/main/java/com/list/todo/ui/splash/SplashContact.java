package com.list.todo.ui.splash;

public class SplashContact {

    interface Presenter {
        void loginControl();
    }

    interface View {
        void openLoginActivity();

        void openTodoListActivity();
    }
}
