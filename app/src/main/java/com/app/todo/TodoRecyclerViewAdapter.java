package com.app.todo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoRecyclerViewAdapter extends RecyclerView.Adapter<TodoRecyclerViewAdapter.ViewHolder> {

    ArrayList<Task> taskArrayList;
    Context context;
    TaskDB db;

    public TodoRecyclerViewAdapter(Context context, ArrayList<Task> taskArrayList) {
        this.taskArrayList = taskArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(taskArrayList.get(holder.getAdapterPosition()).getTask());
        db = TaskDB.getInstance(context);

        holder.getEditImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = taskArrayList.get(holder.getAdapterPosition());
                int sNo = task.getsNo();
                String task_text = task.getTask();
                View view = LayoutInflater.from(context).inflate(R.layout.edit_task_dialog, null);
                EditText editText = view.findViewById(R.id.edit_task_dialog);
                Button updateButton = view.findViewById(R.id.update_btn);
                editText.setText(task_text);


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit")
                        .setView(view)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                Dialog dialog = builder.create();
                dialog.show();
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.taskDAO().update(sNo, editText.getText().toString());
                        taskArrayList.clear();
                        taskArrayList = (ArrayList<Task>) db.taskDAO().getAll();
                        notifyDataSetChanged();
                        Toast.makeText(context,"Update success",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });


            }
        });


        holder.getDeleteImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Task task = taskArrayList.get(holder.getAdapterPosition());
                                db.taskDAO().delete(task);
                                taskArrayList.clear();
                                taskArrayList = (ArrayList<Task>) db.taskDAO().getAll();
                                notifyDataSetChanged();
                                Toast.makeText(context,"Successfully deleted",Toast.LENGTH_SHORT).show();

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

    void refreshRecyclerViewAdapter(ArrayList<Task> taskArrayList) {
        this.taskArrayList = taskArrayList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView edit_btn, delete_btn;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.textView);
            edit_btn = view.findViewById(R.id.edit_imageView);
            delete_btn = view.findViewById(R.id.delete_imageView);


        }

        ImageView getEditImageView() {
            return edit_btn;
        }

        ImageView getDeleteImageView() {
            return delete_btn;
        }

        TextView getTextView() {
            return textView;
        }


    }
}
