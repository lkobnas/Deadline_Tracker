//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app.Utils;

import android.content.DialogInterface;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deadline_app.Adapter.ToDoAdapter;

import java.text.ParseException;

public class SwipeHelper extends ItemTouchHelper.SimpleCallback {
    private ToDoAdapter adapter;

    public SwipeHelper(ToDoAdapter adapter){
        super(0,ItemTouchHelper.LEFT);
        this.adapter = adapter;
    }
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            if(adapter.getTaskStatus(position)==0) {                //check if item unchecked
                AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
                builder.setTitle("Delete Unfinished Task");
                builder.setMessage("Are you sure you want to delete this Task?");
                builder.setPositiveButton(Html.fromHtml("<font color='#D10000'>Delete Task</font>"),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    adapter.deleteItem(position);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{                                              //delete directly
                try {
                    adapter.deleteItem(position);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
