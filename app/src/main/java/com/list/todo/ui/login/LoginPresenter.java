package com.list.todo.ui.login;

import com.list.todo.R;
import com.list.todo.TodoApplication;
import com.list.todo.enums.SignInInfo;
import com.list.todo.model.User;
import com.list.todo.util.LoginManager;

public class LoginPresenter implements LoginContact.Presenter {
    private LoginContact.View view;

    LoginPresenter(LoginContact.View view) {
        this.view = view;
    }

    @Override
    public void login(String mail, String password) {
        User user = LoginManager.login(mail, password);
        if (user != null) {
            view.onSuccessLogin();
        } else {
            view.onFailLogin();
        }
    }

    @Override
    public void signUp(String mail, String password) {
        SignInInfo signUpInfo = LoginManager.signUp(mail, password);
        if (signUpInfo == SignInInfo.SUCCESSFUL) {
            view.onSuccessSignUp();
            return;
        }

        int warningMessageId;
        switch (signUpInfo) {
            case INVALID_MAIL:
                warningMessageId = R.string.invalid_mail;
                break;
            case FAIL:
                warningMessageId = R.string.fail;
                break;
            case MAIL_IS_USED:
                warningMessageId = R.string.mail_is_used;
                break;
            case SHORT_PASSWORD:
                warningMessageId = R.string.short_password;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + signUpInfo);
        }

        view.onError(TodoApplication.getContext().getString(warningMessageId));
    }
}
