package com.list.todo.model;

import java.util.Date;

public class TodoItem {
    private long id;
    private int todoId;
    private String name;
    private Date date;
    private String status;

    public TodoItem() { }

    public TodoItem(long id, int todoId, String name, Date date, String status) {
        this.id = id;
        this.todoId = todoId;
        this.name = name;
        this.date = date;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", todoId=" + todoId +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
