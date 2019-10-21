package com.list.todo.ui.todolist;

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
import com.list.todo.model.Todo;
import com.list.todo.ui.base.BaseActivity;
import com.list.todo.ui.dialog.AddTodoDialog;
import com.list.todo.ui.dialog.RemoveDialog;
import com.list.todo.util.LoginManager;
import com.list.todo.ui.todoitems.TodoItemActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TodoListActivity extends BaseActivity implements TodoListContact.View, RecyclerItemClickListener<Todo> {
    @BindView(R.id.rv_todo_list)
    RecyclerView rvTodoList;

    private TodoListPresenter todoListPresenter;
    private TodoListAdapter todoAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_todo_list;
    }

    @Override
    protected void onActivityReady(Bundle savedInstanceState) {
        super.onActivityReady(savedInstanceState);
        todoListPresenter = new TodoListPresenter(this);
        todoListPresenter.todoList();
    }

    @OnClick(R.id.fab_add)
    public void showAddTodoDialog() {
        AddTodoDialog.showDialog(this, name -> todoListPresenter.addTodo(name));
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, TodoListActivity.class));
    }

    @Override
    public void onTodoList(List<Todo> todoList) {
        rvTodoList.setLayoutManager(new LinearLayoutManager(this));
        todoAdapter = new TodoListAdapter(this, todoList, this);
        rvTodoList.setAdapter(todoAdapter);
    }

    @Override
    public void todoItemAdded(Todo todo) {
        todoAdapter.addItem(todo);
    }

    @Override
    public void todoItemRemoved(Todo todo) {
        todoAdapter.removeItem(todo);
    }

    @Override
    public void onItemClick(Todo item) {
        TodoItemActivity.startActivity(this, item.getId());
    }

    @Override
    public void onItemLongClick(Todo item) {
        RemoveDialog.showDialog(this, item, removeItem -> todoListPresenter.removeTodo(removeItem));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_logout) {
            LoginManager.logout(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
