package com.list.todo.ui.todolist;

import com.list.todo.model.Todo;
import com.list.todo.ui.base.BaseView;

import java.util.List;

class TodoListContact {

    interface Presenter {
        void todoList();

        void addTodo(String name);

        void removeTodo(Todo todo);
    }

    interface View extends BaseView {
        void onTodoList(List<Todo> todoList);

        void todoItemAdded(Todo todo);

        void todoItemRemoved(Todo todo);
    }
}
