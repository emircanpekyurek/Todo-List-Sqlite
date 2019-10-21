package com.list.todo.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.list.todo.R;
import com.list.todo.ui.base.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class AddTodoDialog extends BaseDialog {

    @Override
    public int getLayoutId() {
        return R.layout.dialog_add_todo;
    }

    @BindView(R.id.et_todo_name)
    EditText etTodoName;

    private AddTotoListener listener;

    public static void showDialog(Context context, AddTotoListener listener) {
        new AddTodoDialog(context, listener).show();
    }

    private AddTodoDialog(@NonNull Context context, AddTotoListener listener) {
        super(context);
        this.listener = listener;
    }

    @OnClick(R.id.tv_cancel)
    public void cancel() {
        dismiss();
    }

    @OnClick(R.id.tv_add)
    public void add() {
        String name = etTodoName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getContext(), getContext().getString(R.string.fill_in_all_blanks), Toast.LENGTH_SHORT).show();
        } else {
            listener.added(name);
            dismiss();
        }
    }

    public interface AddTotoListener {
        void added(String name);
    }

}
