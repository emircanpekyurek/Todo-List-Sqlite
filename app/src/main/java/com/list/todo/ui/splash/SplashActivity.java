package com.list.todo.ui.splash;

import android.os.Bundle;
import android.os.Handler;

import com.list.todo.R;
import com.list.todo.ui.base.BaseActivity;
import com.list.todo.ui.login.LoginActivity;
import com.list.todo.ui.todolist.TodoListActivity;

public class SplashActivity extends BaseActivity implements SplashContact.View {

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onActivityReady(Bundle savedInstanceState) {
        super.onActivityReady(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        SplashPresenter splashPresenter = new SplashPresenter(this);
        splashPresenter.loginControl();
    }

    @Override
    public void openLoginActivity() {
        openNewActivityWithDelay(() -> LoginActivity.startActivity(SplashActivity.this));
    }

    @Override
    public void openTodoListActivity() {
        openNewActivityWithDelay(() -> TodoListActivity.startActivity(SplashActivity.this));
    }

    private void openNewActivityWithDelay(Runnable runnable) {
        new Handler().postDelayed(() -> {
            finish();
            runnable.run();
        }, 2000);
    }
}
