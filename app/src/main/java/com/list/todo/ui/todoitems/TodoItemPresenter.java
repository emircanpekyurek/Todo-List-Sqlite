package com.list.todo.ui.todoitems;

import android.os.Build;

import com.list.todo.databases.DatabaseHelper;
import com.list.todo.enums.TodoStatus;
import com.list.todo.model.TodoItem;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TodoItemPresenter implements TodoItemContact.Presenter {
    private TodoItemContact.View view;
    private List<TodoItem> todoItemList;
    private int todoId;

    TodoItemPresenter(TodoItemContact.View view, int todoId) {
        this.view = view;
        this.todoId = todoId;
    }

    private List<TodoItem> getItemList() {
        return DatabaseHelper.getInstance().getTodoItemList(todoId);
    }

    @Override
    public void todoItemList() {
        todoItemList = getItemList();
        view.onTodoItemList(todoItemList);
    }

    @Override
    public void sortCreateDate() {
        Collections.sort(todoItemList, (oldTodo, newTodo) -> oldTodo.getDate().compareTo(newTodo.getDate()));
        view.onTodoItemList(todoItemList);
    }

    @Override
    public void sortDeadline() {
        Collections.sort(todoItemList, (oldTodo, newTodo) -> newTodo.getDate().compareTo(oldTodo.getDate()));
        view.onTodoItemList(todoItemList);
    }

    @Override
    public void sortName() {
        Collections.sort(todoItemList, (oldTodo, newTodo) -> oldTodo.getName().compareTo(newTodo.getName()));
        view.onTodoItemList(todoItemList);
    }

    @Override
    public void sortStatus() {
        Collections.sort(todoItemList, (oldTodo, newTodo) -> oldTodo.getStatus().compareTo(newTodo.getStatus()));
        view.onTodoItemList(todoItemList);
    }

    @Override
    public void filterCompleted() {
        view.onTodoItemList(filter(TodoStatus.COMPLETED.name()));
    }

    @Override
    public void filterNot() {
        view.onTodoItemList(filter(TodoStatus.NOT.name()));
    }

    @Override
    public void filterExpired() {
        view.onTodoItemList(filter(TodoStatus.EXPIRED.name()));
    }

    @Override
    public void filterAll() {
        view.onTodoItemList(filter(null));
    }

    @Override
    public void addTodoItem(String name, Date date) {
        long id = DatabaseHelper.getInstance().insertTodoItemTables(todoId, date, name);
        if (id > 0) {
            view.todoItemAdded(new TodoItem(id, todoId, name, date, TodoStatus.NOT.name()));
        } else {
            view.onInsertError();
        }
    }

    @Override
    public void removeTodoItem(TodoItem todoItem) {
        if (DatabaseHelper.getInstance().deleteTodoItem(todoItem.getId())) {
            view.todoItemRemoved(todoItem);
        } else {
            view.onRemoveError();
        }
    }

    private List<TodoItem> filter(String status) {
        todoItemList = getItemList();
        if (status != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                todoItemList = todoItemList.stream().filter(item ->
                        item.getStatus().equals(status)).collect(Collectors.toList());
            } else {
                Iterator<TodoItem> iterator = todoItemList.iterator();
                while (iterator.hasNext()) {
                    TodoItem item = iterator.next();
                    if (!item.getStatus().equals(status)) {
                        iterator.remove();
                    }
                }
            }
        }
        return todoItemList;
    }

}
