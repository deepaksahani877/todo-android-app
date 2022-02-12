package com.app.todo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class},version = 1)
public abstract class TaskDB extends RoomDatabase {
    private  static TaskDB db;
    private static final String DATABASE_NAME="TODO_DB";
    public  static TaskDB getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context, TaskDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return db;
    }

    public abstract TaskDAO taskDAO();
}
