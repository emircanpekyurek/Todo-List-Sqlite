package com.list.todo.ui.todoitems;

import com.list.todo.model.TodoItem;
import com.list.todo.ui.base.BaseView;

import java.util.Date;
import java.util.List;

public class TodoItemContact {

    interface Presenter {
        void todoItemList();
        void sortCreateDate();
        void sortDeadline();
        void sortName();
        void sortStatus();
        void filterCompleted();
        void filterNot();
        void filterExpired();
        void filterAll();
        void addTodoItem(String name, Date date);
        void removeTodoItem(TodoItem todoItem);
    }

    interface View extends BaseView {
        void onTodoItemList(List<TodoItem> todoItemList);
        void todoItemAdded(TodoItem todoItem);
        void todoItemRemoved(TodoItem todoItem);
    }
}
