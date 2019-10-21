package com.list.todo.ui.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.list.todo.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    public abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        onActivityReady(savedInstanceState);
    }

    @CallSuper
    protected void onActivityReady(Bundle savedInstanceState) {
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {
        showToast(message);
    }

    @Override
    public void onRemoveError() {
        showToast(getString(R.string.deletion_failed));
    }

    @Override
    public void onInsertError() {
        showToast(getString(R.string.insert_failed));
    }
}
