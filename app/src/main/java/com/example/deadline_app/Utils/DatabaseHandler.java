//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.deadline_app.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 3;
    private static final String NAME = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String DEADLINE = "deadline";
    private static final String IMPORTANCE = "importance";
    private static final String STATUS = "status";

    //private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, "
    //        + STATUS + " INTEGER)";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, "
            + DEADLINE + " TEXT, "+ IMPORTANCE + " INTEGER, " + STATUS + " INTEGER)";
    private SQLiteDatabase db;

    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //DROP
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        //CREATE
        onCreate(db);
    }
    public void openDatabase(){
        db = this.getWritableDatabase();
    }
    public void insertTask(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(DEADLINE, task.getDeadline());
        cv.put(IMPORTANCE, task.getImportance());
        cv.put(STATUS, task.getStatus());
        db.insert(TODO_TABLE, null, cv);
    }
    @SuppressLint("Range")
    public List<ToDoModel> getALlTasks(){
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(TODO_TABLE, null,null,null,null,null,null,null);
            if(cur !=null){
                if(cur.moveToFirst()){
                    do{
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setTask(cur.getString(cur.getColumnIndex(TASK)));
                        task.setDeadline(cur.getString(cur.getColumnIndex(DEADLINE)));
                        task.setImportance(cur.getInt(cur.getColumnIndex(IMPORTANCE)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        taskList.add(task);
                    }while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }
    public void updateStatus(int  id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE,cv,ID+"=?", new String[]{String.valueOf(id)});
    }
    public void updateTask(int id, String task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE,cv, ID + "=?", new String[]{String.valueOf(id)});
    }
    public void updateDeadline(int id, String deadline){
        ContentValues cv = new ContentValues();
        cv.put(DEADLINE, deadline);
        db.update(TODO_TABLE,cv, ID + "=?", new String[]{String.valueOf(id)});
    }
    public void updateImportance(int id, int importance){
        ContentValues cv = new ContentValues();
        cv.put(IMPORTANCE, importance);
        db.update(TODO_TABLE,cv,ID+"=?", new String[]{String.valueOf(id)});
    }
    public void deleteTask(int id){
        db.delete(TODO_TABLE, ID + "=?", new String[]{String.valueOf(id)});
    }
    public void replaceDatabase(){
        db.delete(TODO_TABLE,null,null);
        //db.execSQL(CREATE_TODO_TABLE);
    }

}
