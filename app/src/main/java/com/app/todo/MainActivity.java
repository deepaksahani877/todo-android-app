package com.app.todo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Task> taskList = new ArrayList<Task>();
    RecyclerView recyclerView;
    EditText editText;
    Button addButton, clearButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        addButton = findViewById(R.id.add_btn);
        clearButton = findViewById(R.id.clear_btn);
        editText = findViewById(R.id.taskEdittext);

        TaskDB taskdb = TaskDB.getInstance(this);
        taskList = (ArrayList<Task>) taskdb.taskDAO().getAll();
        initRecyclerView();

        TodoRecyclerViewAdapter adapter = new TodoRecyclerViewAdapter(MainActivity.this,taskList);
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(editText.getText().toString().length() < 1)) {
                    Task task = new Task();
                    task.setTask(editText.getText().toString());
                    taskdb.taskDAO().insert(task);
                    taskList.clear();
                    taskList = (ArrayList<Task>) taskdb.taskDAO().getAll();
                    adapter.refreshRecyclerViewAdapter(taskList);
                } else {
                    Toast.makeText(getApplicationContext(), "Text box cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Clear All")
                        .setMessage("Are you sure to clear all todos tasks")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                taskdb.taskDAO().clear(taskList);
                                taskList = (ArrayList<Task>) taskdb.taskDAO().getAll();
                                adapter.refreshRecyclerViewAdapter(taskList);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);
    }
}