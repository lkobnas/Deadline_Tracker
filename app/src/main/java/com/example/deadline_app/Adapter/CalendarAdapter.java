//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deadline_app.CalendarActivity;
import com.example.deadline_app.Model.ToDoModel;
import com.example.deadline_app.R;
import com.example.deadline_app.Utils.DatabaseHandler;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {
    //private List<ToDoModel> todoList;
    private CalendarActivity activity;
    private DatabaseHandler db;
    private List<ToDoModel> dayList;

    public CalendarAdapter(DatabaseHandler db, List<ToDoModel> dayList, CalendarActivity activity){
        this.db = db;
        this.dayList = dayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        db.openDatabase();
        final ToDoModel item = dayList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.textview.setText(item.getDeadline());
        if(toBoolean(item.getStatus())==true){
            holder.itemView.setBackgroundColor(Color.parseColor("#C1B9B9"));
        }else{
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        if(item.getImportance()==1){
            holder.filledStar.setVisibility(View.VISIBLE);
            holder.emptyStar.setVisibility(View.GONE);
        }else{
            holder.filledStar.setVisibility(View.GONE);
            holder.emptyStar.setVisibility(View.VISIBLE);
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
            }
        });
        holder.filledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setImportance(0);
                db.updateImportance(item.getId(),0);
                holder.filledStar.setVisibility(View.GONE);
                holder.emptyStar.setVisibility(View.VISIBLE);
            }
        });
        holder.emptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setImportance(1);
                db.updateImportance(item.getId(),1);
                holder.emptyStar.setVisibility(View.GONE);
                holder.filledStar.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dayList.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //Declare objects here
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

    private boolean toBoolean(int n){
        return n!=0;
    }

    public Context getContext(){
        return activity;
    }

    public void setTasks(List<ToDoModel> list){
        this.dayList = list;
        notifyDataSetChanged();
    }
}
