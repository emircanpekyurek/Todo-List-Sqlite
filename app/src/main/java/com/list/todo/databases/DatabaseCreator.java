package com.list.todo.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.list.todo.databases.SqlConstants.DATABASE_NAME;
import static com.list.todo.databases.SqlConstants.DATABASE_VERSION;
import static com.list.todo.databases.SqlConstants.TodoItemTables;
import static com.list.todo.databases.SqlConstants.TodoTables;
import static com.list.todo.databases.SqlConstants.UserTables;

public class DatabaseCreator extends SQLiteOpenHelper {

    DatabaseCreator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createUserTable(sqLiteDatabase);
        createTodoTable(sqLiteDatabase);
        createTodoItemTable(sqLiteDatabase);
    }

    private void createTodoTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create Table if not exists " + TodoTables.TABLE_NAME + "(" +
                TodoTables.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TodoTables.USER_ID + " INTEGER," +
                TodoTables.NAME + " varchar(255));");
    }

    private void createUserTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create Table if not exists " + UserTables.TABLE_NAME + "(" +
                UserTables.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                UserTables.USERNAME + " varchar(255)," +
                UserTables.PASSWORD + " varchar(255));");
    }

    private void createTodoItemTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create Table if not exists " + TodoItemTables.TABLE_NAME + "(" +
                TodoItemTables.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TodoItemTables.TODO_ID + " INTEGER," +
                TodoItemTables.NAME + " varchar(255)," +
                TodoItemTables.DATE + " varchar(255)," +
                TodoItemTables.STATE + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserTables.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TodoTables.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TodoItemTables.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }


}
