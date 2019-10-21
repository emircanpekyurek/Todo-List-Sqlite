package com.list.todo.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;

import com.list.todo.R;

import butterknife.ButterKnife;

public abstract class BaseDialog extends Dialog {

    public abstract int getLayoutId();

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }
}
