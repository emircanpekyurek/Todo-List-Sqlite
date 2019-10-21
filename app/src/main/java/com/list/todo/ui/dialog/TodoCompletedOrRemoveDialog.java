package com.list.todo.ui.dialog;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.list.todo.R;
import com.list.todo.databases.DatabaseHelper;
import com.list.todo.enums.TodoStatus;
import com.list.todo.model.TodoItem;
import com.list.todo.ui.base.BaseDialog;

import butterknife.OnClick;

public class TodoCompletedOrRemoveDialog extends BaseDialog {

    @Override
    public int getLayoutId() {
        return R.layout.dialog_todo_completed_or_remove;
    }

    private TodoItem todoItem;
    private CompletedOrDeleteListener listener;


    private TodoCompletedOrRemoveDialog(@NonNull Context context, TodoItem todoItem, CompletedOrDeleteListener listener) {
        super(context);
        this.todoItem = todoItem;
        this.listener = listener;
    }

    public static void showDialog(Context context,TodoItem todoItem, CompletedOrDeleteListener listener) {
        new TodoCompletedOrRemoveDialog(context, todoItem, listener).show();
    }

    @OnClick(R.id.tv_delete)
    void deleteTodoItem() {
        RemoveDialog.showDialog(getContext(), todoItem, item -> listener.onDelete(item));
        dismiss();
    }

    @OnClick(R.id.tv_completed)
    void completedTodoItem() {
        TodoStatus status = TodoStatus.COMPLETED;
        if (DatabaseHelper.getInstance().updateTodoItemStatus(todoItem.getId(), status)) {
            todoItem.setStatus(status.name());
            listener.onComplete(todoItem);
            dismiss();
        } else {
            Toast.makeText(getContext(), getContext().getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tv_cancel)
    public void cancel() {
        dismiss();
    }

    public interface CompletedOrDeleteListener {
        void onComplete(TodoItem todoItem);
        void onDelete(TodoItem item);
    }
}
