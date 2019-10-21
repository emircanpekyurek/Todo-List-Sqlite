package com.list.todo.databases;

class SqlConstants {

    static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "TODO_DATABASE";

    static class UserTables {
        static final String TABLE_NAME = "users";
        static final String ID = "user_id";
        static final String USERNAME = "username";
        static final String PASSWORD = "password";
    }

    static class TodoTables {
        static final String TABLE_NAME = "todo_names";
        static final String ID = "todo_name_id";
        static final String USER_ID = UserTables.ID;
        static final String NAME = "name";
    }

    static class TodoItemTables {
        static final String TABLE_NAME = "todo_items";
        static final String ID = "todo_id";
        static final String TODO_ID = TodoTables.ID;
        static final String NAME = "name";
        static final String DATE = "date";
        static final String STATE = "state";
    }

}