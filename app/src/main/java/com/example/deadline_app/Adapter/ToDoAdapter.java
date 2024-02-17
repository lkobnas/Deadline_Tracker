//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.deadline_app.AddNewTask;
import com.example.deadline_app.MainActivity;
import com.example.deadline_app.Model.ToDoModel;
import com.example.deadline_app.R;
import com.example.deadline_app.Utils.DatabaseHandler;

import java.text.ParseException;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> todoList;
    private Activity activity;
    private Context context;
    private DatabaseHandler db;


    public ToDoAdapter(DatabaseHandler db, Context context){
        this.db = db;
        this.context = context;
        //this.activity = activity;
        this.activity = (Activity) context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);

    }
    public void onBindViewHolder(ViewHolder holder, int position){
        db.openDatabase();
        final ToDoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        if(toBoolean(item.getStatus())==true){
            holder.itemView.setBackgroundColor(Color.parseColor("#C1B9B9"));
        }else{
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!compoundButton.isPressed()){                                                    //Strange bug: onCheckedChanged triggered by itself
                    return;
                }
                if(b){
                    item.setStatus(1);
                    db.updateStatus(item.getId(),1); //1
                }else{
                    item.setStatus(0);
                    db.updateStatus(item.getId(),0); //0
                }
                Log.d("ToDoAdapter", "ID: "+item.getTask()+" , onCheckedChanged: "+item.getStatus());
                try {
                    if(context instanceof MainActivity){
                        ((MainActivity)activity).updateMainText();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.textview.setText(item.getDeadline());
        if(item.getImportance()==1){
            holder.filledStar.setVisibility(View.VISIBLE);
            holder.emptyStar.setVisibility(View.GONE);
        }else{
            holder.filledStar.setVisibility(View.GONE);
            holder.emptyStar.setVisibility(View.VISIBLE);
        }

        holder.filledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setImportance(0);
                db.updateImportance(item.getId(),0);
                holder.filledStar.setVisibility(View.GONE);
                holder.emptyStar.setVisibility(View.VISIBLE);
                try {
                    if(context instanceof MainActivity){
                        ((MainActivity)activity).updateMainText();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.emptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setImportance(1);
                db.updateImportance(item.getId(),1);
                holder.emptyStar.setVisibility(View.GONE);
                holder.filledStar.setVisibility(View.VISIBLE);
                try {
                    if(context instanceof MainActivity){
                        ((MainActivity)activity).updateMainText();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public int getItemCount(){
        return todoList.size();
    }
    private boolean toBoolean(int n){
        return n!=0;
    }
    public void setTasks(List<ToDoModel> todoList){
        this.todoList = todoList;
        notifyDataSetChanged();
    }
    public Context getContext(){
        return activity;
    }

    public int getTaskStatus(int position){
        ToDoModel item = todoList.get(position);
        return item.getStatus();
    }

    public void editItem(int position){
        ToDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task",item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        if(context instanceof MainActivity){
            fragment.show(((MainActivity)activity).getSupportFragmentManager(), AddNewTask.TAG);
        }

    }
    public void editItemDeadline(int position){

    }
    public void editItemImportance(int position){
        ToDoModel item = todoList.get(position);
        if(item.getImportance()==0){
            item.setImportance(1);
            db.updateImportance(item.getId(),1);
        }else{
            item.setImportance(0);
            db.updateImportance(item.getId(),0);
        }
    }

    public void deleteItem(int position) throws ParseException {
        ToDoModel item = todoList.get(position);
        db.deleteTask(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
        if(context instanceof MainActivity){
            ((MainActivity)activity).updateMainText();
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        TextView textview;
        ImageView filledStar,emptyStar;
        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.todoCheckbox);
            textview = view.findViewById(R.id.todoDeadline);
            filledStar = view.findViewById(R.id.filledStar);
            emptyStar = view.findViewById(R.id.emptyStar);

        }
    }

}
