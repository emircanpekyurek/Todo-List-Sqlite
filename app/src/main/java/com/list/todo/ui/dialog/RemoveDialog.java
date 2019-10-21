package com.list.todo.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.list.todo.R;
import com.list.todo.ui.base.BaseDialog;

import butterknife.OnClick;

public class RemoveDialog<T> extends BaseDialog {

    @Override
    public int getLayoutId() {
        return R.layout.dialog_remove;
    }

    private RemoveListener<T> listener;
    private T item;

    public static <T> void showDialog(Context context, T item, RemoveListener<T> listener) {
        new RemoveDialog<>(context, item, listener).show();
    }

    private RemoveDialog(@NonNull Context context, T item, RemoveListener<T> listener) {
        super(context);
        this.listener = listener;
        this.item = item;
    }

    @OnClick(R.id.tv_cancel)
    public void cancel() {
        dismiss();
    }

    @OnClick(R.id.tv_delete)
    public void delete() {
        listener.remove(item);
        dismiss();
    }

    public interface RemoveListener<T> {
        void remove(T item);
    }
}
