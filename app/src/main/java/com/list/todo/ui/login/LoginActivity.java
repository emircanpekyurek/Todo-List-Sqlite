package com.list.todo.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.list.todo.R;
import com.list.todo.ui.base.BaseActivity;
import com.list.todo.ui.todolist.TodoListActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContact.View {
    @BindView(R.id.et_mail)
    EditText etMail;
    @BindView(R.id.et_password)
    EditText etPassword;

    LoginPresenter loginPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityReady(Bundle savedInstanceState) {
        super.onActivityReady(savedInstanceState);
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.btn_signup)
    void clickSignIn() {
        loginPresenter.signUp(etMail.getText().toString(), etPassword.getText().toString());
    }


    @OnClick(R.id.btn_login)
    void clickLogin() {
        loginPresenter.login(etMail.getText().toString(), etPassword.getText().toString());
    }

    @Override
    public void onSuccessLogin() {
        showToast(getString(R.string.successful_login));
        finish();
        TodoListActivity.startActivity(this);
    }

    @Override
    public void onFailLogin() {
        showToast(getString(R.string.fail_login));
    }

    @Override
    public void onSuccessSignUp() {
        showToast(getString(R.string.successful_signup));
        finish();
        TodoListActivity.startActivity(this);
    }
}
