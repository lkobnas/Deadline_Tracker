//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app.Helper;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deadline_app.R;

import java.util.Calendar;

public class DatePicker extends AppCompatActivity {
    TextView tvDate;
    EditText etDate;
    Button dateButton;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_datepicker);

        tvDate = findViewById(R.id.tv_date);
        etDate = findViewById(R.id.et_date);
        dateButton = findViewById(R.id.dateButton);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DatePicker.this,R.style.DialogStyle,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
                month += 1;
                String date = dayOfMonth+"/"+month+"/"+year;
                tvDate.setText(date);
            }
        };
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DatePicker.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
                        month += 1;
                        String date=  day+"/"+month+"/"+year;
                        etDate.setText(date);
                    }

                },year,month,day);
                datePickerDialog.show();
            }
        });

    }







}
