package com.app.todo;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import static androidx.room.OnConflictStrategy.REPLACE;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDAO {
    @Query("SELECT * FROM tasksTable")
    List<Task> getAll();

    @Insert(onConflict = REPLACE)
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Delete
    void clear(List<Task> taskList);

    @Query("UPDATE tasksTable SET task =:task WHERE  sNo=:sno")
    void update(int sno, String task);
}