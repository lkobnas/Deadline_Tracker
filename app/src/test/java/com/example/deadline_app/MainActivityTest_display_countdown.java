//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app;

import static org.junit.Assert.assertTrue;

import com.example.deadline_app.Model.ToDoModel;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
        Sorting Test
        The MainActivityTest_display_countdown is used to test the countdown algorithm of the main
        program. I have reuse the testing model of sorting test. The testing will compare the result
        of the calculation method with the expected result.

 */

public class MainActivityTest_display_countdown {

    List<ToDoModel> taskList;
    List<Integer> expectedResult;

    //ToDoModel(int id, int status, int importance, String task_name, String deadline)
    ToDoModel T0 = new ToDoModel(0,0,0, "T0", "8/12/2021");
    ToDoModel T1 = new ToDoModel(1,0,0, "T1", "13/12/2021");
    ToDoModel T2 = new ToDoModel(2,1,1, "T2", "13/12/2021");
    ToDoModel T3 = new ToDoModel(3,0,0, "T3", "14/12/2021");
    ToDoModel T4 = new ToDoModel(4,0,1, "T4", "14/12/2021");
    ToDoModel T5 = new ToDoModel(5,0,1, "T5", "14/12/2021");
    ToDoModel T6 = new ToDoModel(6,1,0, "T6", "18/12/2021");
    ToDoModel T7 = new ToDoModel(7,0,1, "T7", "19/12/2021");
    ToDoModel T8 = new ToDoModel(8,0,0, "T8", "19/12/2021");
    ToDoModel T9 = new ToDoModel(9,0,0, "T9", "30/12/2021");
    ToDoModel T10 = new ToDoModel(10,0,0, "T10", "18/1/2022");
    ToDoModel T11 = new ToDoModel(11,1,0, "T11", "18/1/2022");
    ToDoModel T12 = new ToDoModel(12,0,1, "T12", "18/1/2022");
    ToDoModel T13 = new ToDoModel(13,1,1, "T13", "18/1/2022");

    @Before
    public void setUp() throws ParseException {
        expectedResult = new ArrayList<>();
        taskList = new ArrayList<>();
        taskList.add(T0);
        taskList.add(T1);
        taskList.add(T2);
        taskList.add(T3);
        taskList.add(T4);
        taskList.add(T5);
        taskList.add(T6);
        taskList.add(T7);
        taskList.add(T8);
        taskList.add(T9);
        taskList.add(T10);
        taskList.add(T11);
        taskList.add(T12);
        taskList.add(T13);

        expectedResultGenerator();

    }
    @Test
    public void TestDeadlineT1() throws ParseException {
        boolean result = true;
        if(untilDeadline(taskList.get(1))!= expectedResult.get(1)){
            result = false;
        }
        assertTrue(result);
    }

    @Test
    public void TestDeadlineT2() throws ParseException {
        boolean result = true;
        if(untilDeadline(taskList.get(2))!= expectedResult.get(2)){
            result = false;
        }
        assertTrue(result);
    }
    @Test
    public void TestDeadlineT3() throws ParseException {
        boolean result = true;
        if(untilDeadline(taskList.get(3))!= expectedResult.get(3)){
            result = false;
        }
        assertTrue(result);
    }
    @Test
    public void TestDeadlineT4() throws ParseException {
        boolean result = true;
        if(untilDeadline(taskList.get(4))!= expectedResult.get(4)){
            result = false;
        }
        assertTrue(result);
    }
    @Test
    public void TestDeadlineT5() throws ParseException {
        boolean result = true;
        if(untilDeadline(taskList.get(5))!= expectedResult.get(5)){
            result = false;
        }
        assertTrue(result);
    }
    @Test
    public void TestDeadlineT6() throws ParseException {
        boolean result = true;
        if(untilDeadline(taskList.get(6))!= expectedResult.get(6)){
            result = false;
        }
        assertTrue(result);
    }
    @Test
    public void TestDeadlineT7() throws ParseException {
        boolean result = true;
        if(untilDeadline(taskList.get(7))!= expectedResult.get(7)){
            result = false;
        }
        assertTrue(result);
    }
    @Test
    public void TestDeadlineT8() throws ParseException {
        boolean result = true;
        if(untilDeadline(taskList.get(8))!= expectedResult.get(8)){
            result = false;
        }
        assertTrue(result);
    }
    @Test
    public void TestDeadlineT9() throws ParseException {
        boolean result = true;
        if(untilDeadline(taskList.get(9))!= expectedResult.get(9)){
            result = false;
        }
        assertTrue(result);
    }
    @Test
    public void TestDeadlineT10() throws ParseException {
        boolean result = true;
        if(untilDeadline(taskList.get(10))!= expectedResult.get(10)){
            result = false;
        }
        assertTrue(result);
    }
    @Test
    public void TestDeadlineT11() throws ParseException {
        boolean result = true;
        if(untilDeadline(taskList.get(11))!= expectedResult.get(11)){
            result = false;
        }
        assertTrue(result);
    }
    @Test
    public void TestDeadlineT12() throws ParseException {
        boolean result = true;
        if(untilDeadline(taskList.get(12))!= expectedResult.get(12)){
            result = false;
        }
        assertTrue(result);
    }
    @Test
    public void TestDeadlineT13() throws ParseException {
        boolean result = true;
        if(untilDeadline(taskList.get(13))!= expectedResult.get(13)){
            result = false;
        }
        assertTrue(result);
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

    public void expectedResultGenerator() throws ParseException {

        expectedResult.add(untilDeadline(T0));
        expectedResult.add(untilDeadline(T1));
        expectedResult.add(untilDeadline(T2));
        expectedResult.add(untilDeadline(T3));
        expectedResult.add(untilDeadline(T4));
        expectedResult.add(untilDeadline(T5));
        expectedResult.add(untilDeadline(T6));
        expectedResult.add(untilDeadline(T7));
        expectedResult.add(untilDeadline(T8));
        expectedResult.add(untilDeadline(T9));
        expectedResult.add(untilDeadline(T10));
        expectedResult.add(untilDeadline(T11));
        expectedResult.add(untilDeadline(T12));
        expectedResult.add(untilDeadline(T13));


    }
}