//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deadline_app.Adapter.CalendarAdapter;
import com.example.deadline_app.Model.ToDoModel;
import com.example.deadline_app.Utils.DatabaseHandler;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CalendarActivity extends AppCompatActivity {               //Calendar View from SundeepK/CompactCalendarView on github

    CompactCalendarView calendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.UK);
    private SimpleDateFormat deadlineFormat = new SimpleDateFormat("d/M/yyyy");
    private SimpleDateFormat textFormat = new SimpleDateFormat("MMM-dd");
    private TextView monthText;
    private Button returnButton;
    private RecyclerView tasksRecyclerView;
    private CalendarAdapter tasksAdapter;
    private DatabaseHandler db;
    private List<ToDoModel> taskList;
    private List<ToDoModel> dayList;
    private TextView calendar_text;
    private int[] imgArray = {R.id.chill1,R.id.chill2,R.id.chill3,R.id.chill4,R.id.chill5,R.id.chill6,R.id.chill7
    ,R.id.chill8,R.id.chill9,R.id.chill10,R.id.chill11,R.id.chill12,R.id.chill13,R.id.chill14,R.id.chill15};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().hide();

        calendar_text = findViewById(R.id.calendar_text);
        Date date = new Date();
        monthText = findViewById(R.id.monthText);
        monthText.setText(dateFormatMonth.format(date));
        calendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        calendar.setUseThreeLetterAbbreviation(true);


        db = new DatabaseHandler(this);
        db.openDatabase();
        taskList = new ArrayList<>();
        dayList = new ArrayList<>();
        taskList = db.getALlTasks();

        tasksRecyclerView = findViewById(R.id.calendarRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new CalendarAdapter(db,dayList,this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        updateCalendarList(date);                       //For today

        //event
        for(int i=0; i<taskList.size();++i){
            Event event = null;
            try {
                if (taskList.get(i).getImportance()==1){
                    event = new Event(R.color.red, deadlineFormat.parse(taskList.get(i).getDeadline()).getTime(),taskList.get(i).getTask());
                }else{
                    event = new Event(R.color.yellow, deadlineFormat.parse(taskList.get(i).getDeadline()).getTime(),taskList.get(i).getTask());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendar.addEvent(event);
        }
        //Event event1 = new Event(R.color.gray,1638892800000L,"TEST1");
        //Log.d("Event1", "color: "+event1.getColor());
        //calendar.addEvent(event1);

        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                updateCalendarList(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthText.setText(dateFormatMonth.format(firstDayOfNewMonth));
                Log.d("MonthScroll", "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });
        returnButton = (Button) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {            //Return to List View
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void updateCalendarList(Date dateClicked){
        for(int i=1;i<=14;++i){                                     //Clear all image view
            ImageView imageView = findViewById(imgArray[i]);
            imageView.setVisibility(View.GONE);
        }
        String formatDate = deadlineFormat.format(dateClicked);
        List<ToDoModel> events = new ArrayList<>();
        for(int i=0;i<taskList.size();++i){
            Log.d("DateFormat: ", "Task format: "+taskList.get(i).getDeadline());
            Log.d("DateFormat: ", "Calendar format: "+dateClicked);
            if (taskList.get(i).getDeadline().equals(formatDate)){
                events.add(taskList.get(i));
                Log.d("onDayClick: ", "Task added: "+taskList.get(i).getTask());
            }
        }
        if(events.isEmpty()){
            final int random = new Random().nextInt(14)+1;
            String s = "R.id.chill"+Integer.toString(random);
            ImageView imageView = findViewById(imgArray[random]);
            imageView.setVisibility(View.VISIBLE);
            calendar_text.setText("No work on "+textFormat.format(dateClicked));
            tasksAdapter.setTasks(events);
        }else if(events.size()==1){
            calendar_text.setText("Task on "+textFormat.format(dateClicked));
            tasksAdapter.setTasks(events);
        }else{
            calendar_text.setText("Tasks on "+textFormat.format(dateClicked));
            tasksAdapter.setTasks(events);
        }

        Log.d("calendar", "Day was clicked: " + dateClicked + " with events " + events);
    }

}
