package com.app.todo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasksTable")

public class Task {
    @PrimaryKey(autoGenerate = true)
    public int sNo;

    @ColumnInfo(name = "task")
    public String task_text;

    public String getTask(){
        return task_text;
    }
    public void setTask(String task){
        this.task_text = task;
    }

    public int getsNo(){
        return sNo;
    }
    public void setsNo(int sNo){
        this.sNo = sNo;
    }
}