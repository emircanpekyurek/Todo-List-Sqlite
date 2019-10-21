package com.list.todo.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.list.todo.TodoApplication;
import com.list.todo.enums.SignInInfo;
import com.list.todo.enums.TodoStatus;
import com.list.todo.model.Todo;
import com.list.todo.model.TodoItem;
import com.list.todo.model.User;
import com.list.todo.util.AesUtils;
import com.list.todo.util.Constants;
import com.list.todo.util.DateUtils;
import com.list.todo.util.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends DatabaseCreator {
    private static DatabaseHelper databaseHelper;

    private DatabaseHelper(Context context) {
        super(context);
    }

    public static synchronized DatabaseHelper getInstance() {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(TodoApplication.getContext());
        }
        return databaseHelper;
    }

    private boolean isUserFound(String username) {
        String usersSelectQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                SqlConstants.UserTables.TABLE_NAME, SqlConstants.UserTables.USERNAME);
        Cursor cursor = getWritableDatabase().rawQuery(usersSelectQuery, new String[]{String.valueOf(username)});
        boolean isUserFound = cursor.moveToFirst();
        cursor.close();
        return isUserFound;
    }

    public User getUserForLogin(int id) {
        User user = null;
        String usersSelectQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                SqlConstants.UserTables.TABLE_NAME, SqlConstants.UserTables.ID);
        Cursor cursor = getWritableDatabase().rawQuery(usersSelectQuery, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(SqlConstants.UserTables.ID)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(SqlConstants.UserTables.USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(SqlConstants.UserTables.PASSWORD)));
        }
        cursor.close();
        return user;
    }

    public User getUserForLogin(String mail, String password) {
        User user = null;
        String usersSelectQuery = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?",
                SqlConstants.UserTables.TABLE_NAME, SqlConstants.UserTables.USERNAME, SqlConstants.UserTables.PASSWORD);
        Cursor cursor = getWritableDatabase().rawQuery(usersSelectQuery, new String[]{mail, AesUtils.encrypt(password)});
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(SqlConstants.UserTables.ID)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(SqlConstants.UserTables.USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(SqlConstants.UserTables.PASSWORD)));
        }
        cursor.close();
        return user;
    }

    public SignInInfo insertNewUser(String mail, String password) {
        if (isUserFound(mail)) {
            return SignInInfo.MAIL_IS_USED;
        }
        if (!Utils.isValidEmail(mail)) {
            return SignInInfo.INVALID_MAIL;
        }
        if (password.length() < Constants.MIN_CHARACTER) {
            return SignInInfo.SHORT_PASSWORD;
        }
        return insertUsersTable(mail, password) > 0 ? SignInInfo.SUCCESSFUL : SignInInfo.FAIL;
    }

    private long insertUsersTable(String username, String password) {
        ContentValues values = new ContentValues();
        values.put(SqlConstants.UserTables.USERNAME, username);
        values.put(SqlConstants.UserTables.PASSWORD, AesUtils.encrypt(password));
        return getWritableDatabase().insert(SqlConstants.UserTables.TABLE_NAME, null, values);
    }

    public long insertTodoTables(int userId, String name) {
        ContentValues values = new ContentValues();
        values.put(SqlConstants.TodoTables.NAME, name);
        values.put(SqlConstants.TodoTables.USER_ID, userId);
        return getWritableDatabase().insert(SqlConstants.TodoTables.TABLE_NAME, null, values);
    }

    public long insertTodoItemTables(int todoId, Date date, String name) {
        ContentValues values = new ContentValues();
        values.put(SqlConstants.TodoItemTables.TODO_ID, todoId);
        values.put(SqlConstants.TodoItemTables.DATE, DateUtils.dateToString(date));
        values.put(SqlConstants.TodoItemTables.NAME, name);
        values.put(SqlConstants.TodoItemTables.STATE, TodoStatus.NOT.name());
        return getWritableDatabase().insert(SqlConstants.TodoItemTables.TABLE_NAME, null, values);
    }

    public boolean deleteTodo(int id) {
        getWritableDatabase().delete(SqlConstants.TodoItemTables.TABLE_NAME, SqlConstants.TodoItemTables.TODO_ID + "=" + id, null);
        long todoDeleted = deleteSQL(SqlConstants.TodoTables.TABLE_NAME, SqlConstants.TodoTables.ID, id);
        return todoDeleted > 0;
    }

    public boolean deleteTodoItem(long todoItemId) {
        return deleteSQL(SqlConstants.TodoItemTables.TABLE_NAME, SqlConstants.TodoItemTables.ID, todoItemId) > 0;
    }

    public List<Todo> getTodoList(int userId) {
        List<Todo> todoList = new ArrayList<>();
        String usersSelectQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                SqlConstants.TodoTables.TABLE_NAME, SqlConstants.TodoTables.USER_ID);
        Cursor cursor = getWritableDatabase().rawQuery(usersSelectQuery, new String[]{String.valueOf(userId)});
        while (cursor.moveToNext()) {
            Todo todo = new Todo();
            todo.setId(Integer.parseInt(cursorColumnString(cursor, SqlConstants.TodoTables.ID)));
            todo.setUserId(Integer.parseInt(cursorColumnString(cursor, SqlConstants.TodoTables.USER_ID)));
            todo.setName(cursorColumnString(cursor, SqlConstants.TodoTables.NAME));
            todoList.add(todo);
        }
        cursor.close();
        return todoList;
    }

    public boolean updateTodoItemStatus(long todoId ,TodoStatus status) {
        ContentValues values = new ContentValues();
        values.put(SqlConstants.TodoItemTables.STATE, status.name());
        return getWritableDatabase().update(SqlConstants.TodoItemTables.TABLE_NAME, values
                , SqlConstants.TodoItemTables.ID + "=" + todoId, null) > 0;
    }

    public List<TodoItem> getTodoItemList(int todoId) {
        List<TodoItem> todoItemList = new ArrayList<>();
        String usersSelectQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                SqlConstants.TodoItemTables.TABLE_NAME, SqlConstants.TodoItemTables.TODO_ID);
        Cursor cursor = getWritableDatabase().rawQuery(usersSelectQuery, new String[]{String.valueOf(todoId)});
        if (cursor.moveToFirst()) {
            do {
                try {
                    TodoItem todoItem = new TodoItem();
                    Date date = DateUtils.stringToDate(cursorColumnString(cursor, SqlConstants.TodoItemTables.DATE));
                    String state = cursorColumnString(cursor, SqlConstants.TodoItemTables.STATE);
                    int id = Integer.parseInt(cursorColumnString(cursor, SqlConstants.TodoItemTables.ID));
                    String name = cursorColumnString(cursor, SqlConstants.TodoItemTables.NAME);

                    todoItem.setId(id);
                    todoItem.setDate(date);
                    todoItem.setName(name);
                    todoItem.setTodoId(todoId);

                    if (DateUtils.nowDate().after(date) && !state.equals(TodoStatus.COMPLETED.name())) {
                        state = TodoStatus.EXPIRED.name();
                    }
                    todoItem.setStatus(state);

                    todoItemList.add(todoItem);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.d("fsafa", e.getMessage());
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return todoItemList;
    }

    private String cursorColumnString(Cursor cursor, String indexName) {
        return cursor.getString(cursor.getColumnIndex(indexName));
    }

    private long deleteSQL(String tableName, String tableIdName, long id) {
        return getWritableDatabase().delete(tableName, tableIdName + "=" + id, null);
    }


}
