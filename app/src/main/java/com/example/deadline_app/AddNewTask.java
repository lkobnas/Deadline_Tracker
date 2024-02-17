//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.deadline_app.Model.ToDoModel;
import com.example.deadline_app.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.ParseException;
import java.util.Calendar;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";

    private EditText newTaskText;
    private TextView newTaskDate;
    private Button newTaskSaveButton;
    private Switch newTaskImportanceSwitch;
    private DatabaseHandler db;
    DatePickerDialog.OnDateSetListener setListener;
    private String date;
    private int importance =1;
    private boolean taskNameEmpty = true;
    private boolean taskDeadlineEmpty = true;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
        Log.d(TAG, " Called");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        Log.d(TAG, " onCreateView");
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newTaskText = getView().findViewById(R.id.newTaskText);
        newTaskDate = getView().findViewById(R.id.newTaskDate);
        newTaskSaveButton = getView().findViewById(R.id.newTaskButton);
        newTaskImportanceSwitch = getView().findViewById(R.id.newTaskImportanceSwitch);

        Log.d(TAG, " onViewCreated");

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if(bundle!= null){
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);
            if(task.length()>0){
                newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(),R.color.picton_blue));
            }
        }

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    taskNameEmpty = true;
                }else{
                    taskNameEmpty = false;
                }
                updateSaveButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        newTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        view.getContext(),R.style.DialogStyle,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                datePickerDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
                month += 1;
                date = dayOfMonth+"/"+month+"/"+year;
                newTaskDate.setText(date);
                taskDeadlineEmpty = false;
                updateSaveButton();
                //simpleDateFormat = date;
                //simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            }
        };

        newTaskImportanceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    importance = 1;
                }else{
                    importance = 0;
                }
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newTaskSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String text = newTaskText.getText().toString();
                if(finalIsUpdate){
                    db.updateTask(bundle.getInt("id"),text);
                }else{
                    ToDoModel task = new ToDoModel();
                    task.setTask(text);
                    task.setDeadline(date);
                    task.setImportance(importance);
                    task.setStatus(0);
                    db.insertTask(task);
                }
                dismiss();
            }
        });


    }
    @Override
    public void onDismiss(DialogInterface dialog){
        Activity activity = getActivity();
        Log.d(TAG, "onDismiss called ");
        if(activity instanceof DialogCloseListener){
            try {
                ((DialogCloseListener)activity).handleDialogClose(dialog);
                Log.d(TAG, "handleDialogClose called ");
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
       dismissAllowingStateLoss();

    }


    private void updateSaveButton(){
        if(taskNameEmpty||taskDeadlineEmpty){
            newTaskSaveButton.setEnabled(false);
            newTaskSaveButton.setTextColor(Color.GRAY);
        }else{
            newTaskSaveButton.setEnabled(true);
            newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(),R.color.picton_blue));
        }
    }
}
