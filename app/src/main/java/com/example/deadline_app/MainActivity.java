//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.deadline_app.Adapter.ToDoAdapter;
import com.example.deadline_app.Helper.UserDialog;
import com.example.deadline_app.Model.ToDoModel;
import com.example.deadline_app.Utils.ChillString;
import com.example.deadline_app.Utils.DatabaseCloud;
import com.example.deadline_app.Utils.DatabaseHandler;
import com.example.deadline_app.Utils.SwipeHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener, MyCallback {

    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;
    private Button calendarButton;

    private List<ToDoModel> taskList;
    private DatabaseHandler db;
    private int closest;
    private ImageView chillImage;
    private int sortingMode;
    private Spinner spinner;
    private static final String[] sort_option = {
            "Most Urgent", "Most Important", "Recently Added",
    };
    private WidgetProvider widget;
    private DatabaseCloud cloud;
    private UserDialog dialog;
    private boolean isLogin;

    public TextView taskName;
    public TextView taskDeadline;
    Button button_user, button_login;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //Initialisation
        spinner = findViewById(R.id.sort_spinner);
        chillImage = findViewById(R.id.noTaskImage);
        button_user = findViewById(R.id.button_main_user);
        button_login = findViewById(R.id.button_main_login);
        taskName = findViewById(R.id.taskName);
        taskDeadline = findViewById(R.id.taskDaysLeft);
        db = new DatabaseHandler(this);
        sortingMode = 0;
        db.openDatabase();
        taskList = new ArrayList<>();
        taskList = db.getALlTasks();

        Intent i = getIntent();                                                         //Check if user login with account
        if (i.getExtras() != null) {        //with account
            Log.d("Intent", " : true");
            isLogin = true;
            cloud = new DatabaseCloud(i.getExtras().getString("phone"), db, this);
            cloud.checkUser(taskList);
            button_login.setVisibility(View.GONE);
            button_user.setText(i.getExtras().getString("username"));
            button_user.setVisibility(View.VISIBLE);
        } else {                          //no account
            Log.d("Intent", " : false");
            isLogin = false;
            button_user.setVisibility(View.GONE);
            button_login.setVisibility(View.VISIBLE);
        }

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);                                       //Set RecyclerView and Adapter
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);
        tasksAdapter.setTasks(taskList);

        button_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new UserDialog(MainActivity.this, db, cloud, taskList, i.getExtras().getString("username"));
                dialog.show(getSupportFragmentManager(), "UserDialog");
                try {
                    refreshTasklist(sortingMode);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                finish();
            }
        });

        fab = findViewById(R.id.fab);                                                                   //Add new task floating button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("fab", " Triggered");
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);                                        //Swipe to Refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    refreshTasklist(sortingMode);
                    updateMainText();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        calendarButton = findViewById(R.id.calendarButton);                                                 //Switch to calendar view
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,                          //Sort spinner
                R.layout.spinner_item, sort_option);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextSize(18);
                sortingMode = i;
                Log.d("sortingMode", "onItemSelected: "+i);
                try {
                    refreshTasklist(sortingMode);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    refreshTasklist(sortingMode);
                    updateMainText();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            refreshTasklist(sortingMode);
            updateMainText();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {                                                //update database
        super.onStop();
        if (isLogin) {
            cloud.updateCloud(taskList);
        }
        Log.d("Destroy :", "onDestroy: ");
    }

    private ToDoModel findTask(int isUrgent) throws ParseException {
        if (taskList.isEmpty()) {
            return null;
        }
        ToDoModel mostUrgent = null;

        for (int i = 0; i < taskList.size(); ++i) {                 //Find the first available task
            if (taskList.get(i).getStatus() == 0 && taskList.get(i).getImportance() == isUrgent) {
                mostUrgent = taskList.get(i);
                closest = untilDeadline(taskList.get(i));

                for (int j = i + 1; j < taskList.size(); ++j) {           //compare the all task with the first task
                    if (taskList.get(j).getStatus() == 0 && untilDeadline(taskList.get(j)) < closest && taskList.get(j).getImportance() == isUrgent) {
                        mostUrgent = taskList.get(j);
                        closest = untilDeadline(taskList.get(j));
                    }
                }
                break;
            }
        }
        return mostUrgent;

    }

    private int untilDeadline(ToDoModel toDoModel) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();

        Date today = c.getTime();
        Date deadline = sdf.parse(toDoModel.getDeadline());

        long result = deadline.getTime() - today.getTime();
        //Log.d("Time Now: ", today.toString());
        //Log.d("Time deadline: ", deadline.toString());
        //Log.d("result: ", Integer.toString((int)result));
        if (result < (24 * 60 * 60 * 1000)) {                 //deadline within 1 day
            if (result > 0) {
                result = 1;
            } else if(result<-(24*60*60*1000)){
                //result=-1;
            }else{
                result = 0;
            }
        } else {
            result = result / 1000 / 60 / 60 / 24 + 1;          //deadline > 1 day
        }
        return (int) result;

    }

    @Override
    public void handleDialogClose(DialogInterface dialog) throws ParseException {
        refreshTasklist(sortingMode);
        updateMainText();
        //dialog.dismiss();
        tasksAdapter.notifyDataSetChanged();

    }

    @Override
    public void updateMainText() throws ParseException {
        try {
            if (findTask(1) != null) {
                taskName.setText(findTask(1).getTask());
                taskDeadline.setTextSize(36);
                if(closest < 0){
                    taskDeadline.setTextColor(Color.RED);
                    taskDeadline.setText("Deadline Passed!!!");
                } else if (closest == 0) {
                    taskDeadline.setTextColor(Color.RED);
                    taskDeadline.setText("Due Today!");
                } else if (closest == 1) {
                    taskDeadline.setTextColor(Color.parseColor("#FF5F00"));
                    taskDeadline.setText("1 day left!");
                } else if (closest < 8) {
                    taskDeadline.setTextColor(Color.parseColor("#FFCD01"));
                    taskDeadline.setText(closest + " days left");
                } else {
                    taskDeadline.setTextColor(Color.BLACK);
                    taskDeadline.setText(closest + " days left");
                }

            } else if (findTask(0) != null) {
                taskDeadline.setTextColor(Color.BLACK);
                taskDeadline.setTextSize(32);
                taskName.setText("No important deadline remaining");
                taskDeadline.setText("Take your time");
            } else {
                taskDeadline.setTextColor(Color.BLACK);
                taskName.setText("You've finished all tasks");
                ChillString chillString = new ChillString();
                taskDeadline.setTextSize(32);
                taskDeadline.setText(chillString.getChillString());
            }
            if (taskList.isEmpty()) {
                chillImage.setVisibility(View.VISIBLE);
            } else {
                chillImage.setVisibility(View.GONE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshTasklist(int sortingMode) throws ParseException {
        taskList = db.getALlTasks();
        if(taskList==null || taskList.isEmpty()){
            tasksAdapter.setTasks(taskList);
            return;
        }
        int countChecked = countCheckedTask();
        switch (sortingMode) {
            case 0:                                               //Default, most Urgent
                sortingCheckbox(countChecked);
                sortingDeadline();
                sortingImportanceWithSameDeadline();
                break;
            case 1:                                               //most important
                sortingCheckbox(countChecked);
                sortingImportance();
                sortingDeadlineWithSameImportance();
                break;
            case 2:                                                //Recently Added
                sortingDayAdded();
                sortingCheckbox(countChecked);

                break;
        }
        tasksAdapter.setTasks(taskList);
        //widget.onUpdate(getContext);
    }

    private void sortingCheckbox(int countChecked) {
        int processed = 0;
        for (int i = 0; i < countChecked; ++i) {                        //Sort taskList with unfinished task first
            for (int j = 0; j < taskList.size() - processed; ++j) {
                if (taskList.get(j).getStatus() == 1) {
                    ToDoModel tempTask = taskList.get(j);
                    for (int k = j; k < taskList.size() - 1; ++k) {
                        taskList.set(k, taskList.get(k + 1));
                    }
                    taskList.set(taskList.size() - 1, tempTask);
                    processed++;
                    break;
                }
            }
        }
    }

    private void sortingDeadline() throws ParseException {                                                  //deadline
        for (int i = 1; i < taskList.size()-countCheckedTask(); ++i) {                                      //unchecked list
            ToDoModel tempTask = taskList.get(i);
            int j = i - 1;
            while (j >= 0 && untilDeadline(tempTask) < untilDeadline(taskList.get(j))) {
                j--;
            }
            for (int k = i; k > j + 1; k--) {
                taskList.set(k, taskList.get(k - 1));
            }
            taskList.set(j + 1, tempTask);
        }

        for (int i = taskList.size()-countCheckedTask()+1; i<taskList.size() ; ++i) {                       //checked list
            ToDoModel tempTask = taskList.get(i);
            int j = i - 1;
            while (j >= taskList.size()-countCheckedTask() && untilDeadline(tempTask) < untilDeadline(taskList.get(j))) {
                j--;
            }
            for (int k = i; k > j + 1; k--) {
                taskList.set(k, taskList.get(k - 1));
            }
            taskList.set(j + 1, tempTask);
        }
    }

    private void sortingImportance() throws ParseException {
        for (int i = 1; i < taskList.size()-countCheckedTask(); ++i) {                                                  //importance
            ToDoModel tempTask = taskList.get(i);
            int j = i - 1;
            while (j >= 0 && tempTask.getImportance() > taskList.get(j).getImportance()) {
                j--;
            }
            for (int k = i; k > j + 1; k--) {
                taskList.set(k, taskList.get(k - 1));
            }
            taskList.set(j + 1, tempTask);
        }

        for (int i = taskList.size()-countCheckedTask()+1; i<taskList.size() ; ++i) {                                                  //importance
            ToDoModel tempTask = taskList.get(i);
            int j = i - 1;
            while (j >= taskList.size()-countCheckedTask() && tempTask.getImportance() > taskList.get(j).getImportance()) {
                j--;
            }
            for (int k = i; k > j + 1; k--) {
                taskList.set(k, taskList.get(k - 1));
            }
            taskList.set(j + 1, tempTask);
        }


    }

    private void secondarySort(int mode, int start, int end) throws ParseException {
        if (end <= 0 || start==end) {
            return;
        }
        if (mode == 0) {
            for (int i = 1 + start; i <= end; ++i) {                                                //deadline
                ToDoModel tempTask = taskList.get(i);
                int j = i - 1;
                while (j >= start && untilDeadline(tempTask) < untilDeadline(taskList.get(j))) {
                    j--;
                }
                for (int k = i; k > j + 1; k--) {
                    taskList.set(k, taskList.get(k - 1));
                }
                taskList.set(j + 1, tempTask);
            }
        } else if (mode == 1) {                                                                     //importance
            for (int i = 1 + start; i <= end; ++i) {
                ToDoModel tempTask = taskList.get(i);
                int j = i - 1;
                while (j >= start && tempTask.getImportance() > taskList.get(j).getImportance()) {
                    j--;
                }
                for (int k = i; k > j + 1; k--) {
                    taskList.set(k, taskList.get(k - 1));
                }
                taskList.set(j + 1, tempTask);
            }
        }
    }

    private void sortingDayAdded() {                         //database is already sorted by day added
        Collections.reverse(taskList);
    }

    private int countCheckedTask() {
        int countChecked = 0;
        for (int i = 0; i < taskList.size(); ++i) {                 //Count the number of checked tasks
            if (taskList.get(i).getStatus() == 1) {
                countChecked++;
            }
        }
        return countChecked;
    }

    private int countImportantTask() {
        int countImportant = 0;
        for (int i = 0; i < taskList.size()-countCheckedTask(); ++i) {                 //Count the number of important tasks
            if (taskList.get(i).getImportance() == 1) {
                countImportant++;
            }
        }
        return countImportant;
    }

    private void sortingImportanceWithSameDeadline() throws ParseException {
        for(int i=0;i<taskList.size()-countCheckedTask();++i){
            String deadline = taskList.get(i).getDeadline();
            int countSameDeadline = 0;
            for(int j=i+1;j<taskList.size()-countCheckedTask();++j){
                if(taskList.get(j).getDeadline().equals(deadline)){
                    countSameDeadline++;
                }
            }
            secondarySort(1,i,i+countSameDeadline);
            i = i+countSameDeadline;
        }
        for(int i=taskList.size()-countCheckedTask(); i<taskList.size();++i){
            String deadline = taskList.get(i).getDeadline();
            int countSameDeadline = 0;
            for(int j=i+1;j<taskList.size();++j){
                if(taskList.get(j).getDeadline().equals(deadline)){
                    countSameDeadline++;
                }
            }
            secondarySort(1,i,i+countSameDeadline);
            i = i+countSameDeadline;
        }
    }

    /*
    private void sortingImportanceWithSameDeadline() throws ParseException {
        if(taskList.isEmpty()){
            return;
        }
        secondarySort(1,0,taskList.size()-countCheckedTask()-1);
        secondarySort(1,taskList.size()-countCheckedTask(),taskList.size()-1);
    }

     */
    private void sortingDeadlineWithSameImportance() throws ParseException {
        int pos=-1;
        for(int i=0;i<taskList.size()-countCheckedTask();++i){
            if(taskList.get(i).getImportance()==0){
                pos = i;
                secondarySort(0,0,i-1);
                break;
            }
        }
        if(pos==-1){
            secondarySort(0,0,taskList.size()-countCheckedTask()-1);
        }else{
            secondarySort(0,pos,taskList.size()-countCheckedTask()-1);
        }
        pos = -1;
        for(int i=taskList.size()-countCheckedTask(); i<taskList.size();++i){
            if(taskList.get(i).getImportance()==0){
                pos= i;
                secondarySort(0,taskList.size()-countCheckedTask(),i-1);
                break;
            }
        }
        if(pos==-1){
            secondarySort(0,taskList.size()-countCheckedTask(),taskList.size()-1);
        }else{
            secondarySort(0, pos,taskList.size()-1);
        }
    }

}