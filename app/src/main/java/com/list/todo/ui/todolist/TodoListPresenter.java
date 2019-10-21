package com.list.todo.ui.todolist;

import com.list.todo.TodoApplication;
import com.list.todo.databases.DatabaseHelper;
import com.list.todo.model.Todo;

import java.util.List;

public class TodoListPresenter implements TodoListContact.Presenter {

    private final TodoListContact.View view;

    TodoListPresenter(TodoListContact.View view) {
        this.view = view;
    }

    @Override
    public void todoList() {
        List<Todo> todoList = DatabaseHelper.getInstance().getTodoList(TodoApplication.getContext().getUser().getId());
        view.onTodoList(todoList);
    }

    @Override
    public void addTodo(String name) {
        int userId = TodoApplication.getContext().getUser().getId();
        int todoId = (int) DatabaseHelper.getInstance().insertTodoTables(userId, name);
        if (todoId > 0) {
            Todo todo = new Todo(todoId, userId, name);
            view.todoItemAdded(todo);
        } else {
            view.onInsertError();
        }
    }

    @Override
    public void removeTodo(Todo todo) {
        if (DatabaseHelper.getInstance().deleteTodo(todo.getId())) {
            view.todoItemRemoved(todo);
        } else {
            view.onRemoveError();
        }
    }
}
