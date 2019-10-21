package com.list.todo.ui.todoitems;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.list.todo.R;
import com.list.todo.RecyclerItemClickListener;
import com.list.todo.enums.TodoStatus;
import com.list.todo.model.TodoItem;
import com.list.todo.ui.base.BaseActivity;
import com.list.todo.ui.dialog.AddTodoItemDialog;
import com.list.todo.ui.dialog.TodoCompletedOrRemoveDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.list.todo.util.Constants.Intent.TODO_ID;

public class TodoItemActivity extends BaseActivity implements TodoItemContact.View, RecyclerItemClickListener<TodoItem> {

    @BindView(R.id.rv_todo_items)
    RecyclerView rvTodoItems;

    private TodoItemPresenter todoItemPresenter;
    private TodoItemAdapter todoAdapter;

    public static void startActivity(Context context, int todoId) {
        Intent intent = new Intent(context, TodoItemActivity.class);
        intent.putExtra(TODO_ID, todoId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_todo_item;
    }

    @Override
    protected void onActivityReady(Bundle savedInstanceState) {
        super.onActivityReady(savedInstanceState);
        int todoId = getIntent().getExtras().getInt(TODO_ID);
        todoItemPresenter = new TodoItemPresenter(this, todoId);
        todoItemPresenter.todoItemList();
    }

    @OnClick(R.id.fab_add)
    public void showAddTodoDialog() {
        AddTodoItemDialog.showDialog(this, (name, date) -> todoItemPresenter.addTodoItem(name, date));
    }

    @Override
    public void onTodoItemList(List<TodoItem> todoItemList) {
        if (todoAdapter == null) {
            rvTodoItems.setLayoutManager(new LinearLayoutManager(this));
            todoAdapter = new TodoItemAdapter(this, this);
            rvTodoItems.setAdapter(todoAdapter);
        }
        todoAdapter.setItems(todoItemList);
    }

    @Override
    public void todoItemAdded(TodoItem todoItem) {
        todoAdapter.addItem(todoItem);
    }

    @Override
    public void todoItemRemoved(TodoItem todoItem) {
        todoAdapter.removeItem(todoItem);
    }

    @Override
    public void onItemClick(TodoItem item) {
        showToast(item.toString());
    }

    @Override
    public void onItemLongClick(TodoItem item) {
        TodoCompletedOrRemoveDialog.showDialog(this, item, new TodoCompletedOrRemoveDialog.CompletedOrDeleteListener() {
            @Override
            public void onComplete(TodoItem todoItem) {
                todoAdapter.updateItem(item, todoItem);
            }

            @Override
            public void onDelete(TodoItem item) {
                todoItemPresenter.removeTodoItem(item);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_todo_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_sort_create_date:
                todoItemPresenter.sortCreateDate();
                return true;
            case R.id.item_sort_deadline:
                todoItemPresenter.sortDeadline();
                return true;
            case R.id.item_sort_name:
                todoItemPresenter.sortName();
                return true;
            case R.id.item_sort_status:
                todoItemPresenter.sortStatus();
                return true;
            case R.id.item_filter_completed:
                todoItemPresenter.filterCompleted();
                return true;
            case R.id.item_filter_not:
                todoItemPresenter.filterNot();
                return true;
            case R.id.item_filter_expired:
                todoItemPresenter.filterExpired();
                return true;
            case R.id.item_filter_all:
                todoItemPresenter.filterAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
