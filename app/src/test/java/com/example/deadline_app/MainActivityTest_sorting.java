//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app;

import static org.junit.Assert.*;

import com.example.deadline_app.Model.ToDoModel;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/*
        Sorting Test
        The MainActivityTest_sorting is used to test the sorting algorithm of the main program.
        I have created 3 sorting method for the user to choose. In this test programme, I have
        designed 13 object models and 7 test list. Since there are 3 sorting method, there will
        be 21 test cases in this program. All test lists are designed to simulate edge cases and
        different combinations. 2 significant bug is founded and fixed (details in project report)


 */

public class MainActivityTest_sorting {
    List<ToDoModel> taskList;
    //Expected Correct Task List
    List<ToDoModel> expectedTaskList0_sortingMode0, expectedTaskList0_sortingMode1, expectedTaskList0_sortingMode2;
    List<ToDoModel> expectedTaskList1_sortingMode0, expectedTaskList1_sortingMode1, expectedTaskList1_sortingMode2;
    List<ToDoModel> expectedTaskList2_sortingMode0, expectedTaskList2_sortingMode1, expectedTaskList2_sortingMode2;
    List<ToDoModel> expectedTaskList3_sortingMode0, expectedTaskList3_sortingMode1, expectedTaskList3_sortingMode2;
    List<ToDoModel> expectedTaskList4_sortingMode0, expectedTaskList4_sortingMode1, expectedTaskList4_sortingMode2;
    List<ToDoModel> expectedTaskList5_sortingMode0, expectedTaskList5_sortingMode1, expectedTaskList5_sortingMode2;
    List<ToDoModel> expectedTaskList6_sortingMode0, expectedTaskList6_sortingMode1, expectedTaskList6_sortingMode2;

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


    List<ToDoModel> testModel(int test){
        taskList = new ArrayList<>();
        switch(test){
            case 0:                             //empty task
                break;
            case 1:                             //same status, different deadline, different status
                taskList.add(T3);
                taskList.add(T8);
                taskList.add(T9);
                break;
            case 2:                             //different status, same deadline, different importance
                taskList.add(T10);
                taskList.add(T11);
                taskList.add(T12);
                taskList.add(T13);
                break;
            case 3:                             //different status, different deadline, same importance, overdue task included
                taskList.add(T0);
                taskList.add(T1);
                taskList.add(T3);
                taskList.add(T6);
                taskList.add(T10);
                break;
            case 4:                             //mixed list with different attributes
                taskList.add(T2);
                taskList.add(T4);
                taskList.add(T5);
                taskList.add(T7);
                taskList.add(T8);
                taskList.add(T9);
                taskList.add(T12);
                break;
            case 5:                             //mixed list with different attributes and insert with different order
                taskList.add(T7);
                taskList.add(T4);
                taskList.add(T9);
                taskList.add(T10);
                taskList.add(T3);
                taskList.add(T6);
                taskList.add(T12);
                taskList.add(T1);
                break;
            case 6:                             //long task list
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
                break;
        }
        return taskList;
    }

    @Before
    public void setUp(){
        expectedTaskList0_sortingMode0 = new ArrayList<>();
        expectedTaskList0_sortingMode1 = new ArrayList<>();
        expectedTaskList0_sortingMode2 = new ArrayList<>();
        expectedTaskList1_sortingMode0 = new ArrayList<>();
        expectedTaskList1_sortingMode1 = new ArrayList<>();
        expectedTaskList1_sortingMode2 = new ArrayList<>();
        expectedTaskList2_sortingMode0 = new ArrayList<>();
        expectedTaskList2_sortingMode1 = new ArrayList<>();
        expectedTaskList2_sortingMode2 = new ArrayList<>();
        expectedTaskList3_sortingMode0 = new ArrayList<>();
        expectedTaskList3_sortingMode1 = new ArrayList<>();
        expectedTaskList3_sortingMode2 = new ArrayList<>();
        expectedTaskList4_sortingMode0 = new ArrayList<>();
        expectedTaskList4_sortingMode1 = new ArrayList<>();
        expectedTaskList4_sortingMode2 = new ArrayList<>();
        expectedTaskList5_sortingMode0 = new ArrayList<>();
        expectedTaskList5_sortingMode1 = new ArrayList<>();
        expectedTaskList5_sortingMode2 = new ArrayList<>();
        expectedTaskList6_sortingMode0 = new ArrayList<>();
        expectedTaskList6_sortingMode1 = new ArrayList<>();
        expectedTaskList6_sortingMode2 = new ArrayList<>();

        expectedListGenerator();
    }

    @Test
    public void testTaskList0_sortingMode0() throws ParseException {
        boolean result = true;
        taskList = testModel(0);
        refreshTasklist(0);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList0_sortingMode0.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList0_sortingMode1() throws ParseException {
        boolean result = true;
        taskList = testModel(0);
        refreshTasklist(1);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList0_sortingMode1.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList0_sortingMode2() throws ParseException {
        boolean result = true;
        taskList = testModel(0);
        refreshTasklist(2);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList0_sortingMode2.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList1_sortingMode0() throws ParseException {
        boolean result = true;
        taskList = testModel(1);
        refreshTasklist(0);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList1_sortingMode0.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList1_sortingMode1() throws ParseException {
        boolean result = true;
        taskList = testModel(1);
        refreshTasklist(1);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList1_sortingMode1.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList1_sortingMode2() throws ParseException {
        boolean result = true;
        taskList = testModel(1);
        refreshTasklist(2);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList1_sortingMode2.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList2_sortingMode0() throws ParseException {
        boolean result = true;
        taskList = testModel(2);
        refreshTasklist(0);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList2_sortingMode0.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList2_sortingMode1() throws ParseException {
        boolean result = true;
        taskList = testModel(2);
        refreshTasklist(1);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList2_sortingMode1.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList2_sortingMode2() throws ParseException {
        boolean result = true;
        taskList = testModel(2);
        refreshTasklist(2);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList2_sortingMode2.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }

    @Test
    public void testTaskList3_sortingMode0() throws ParseException {
        boolean result = true;
        taskList = testModel(3);
        refreshTasklist(0);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList3_sortingMode0.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList3_sortingMode1() throws ParseException {
        boolean result = true;
        taskList = testModel(3);
        refreshTasklist(1);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList3_sortingMode1.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList3_sortingMode2() throws ParseException {
        boolean result = true;
        taskList = testModel(3);
        refreshTasklist(2);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList3_sortingMode2.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }

    @Test
    public void testTaskList4_sortingMode0() throws ParseException {
        boolean result = true;
        taskList = testModel(4);
        refreshTasklist(0);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList4_sortingMode0.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList4_sortingMode1() throws ParseException {
        boolean result = true;
        taskList = testModel(4);
        refreshTasklist(1);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList4_sortingMode1.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList4_sortingMode2() throws ParseException {
        boolean result = true;
        taskList = testModel(4);
        refreshTasklist(2);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList4_sortingMode2.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }

    @Test
    public void testTaskList5_sortingMode0() throws ParseException {
        boolean result = true;
        taskList = testModel(5);
        refreshTasklist(0);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList5_sortingMode0.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList5_sortingMode1() throws ParseException {
        boolean result = true;
        taskList = testModel(5);
        refreshTasklist(1);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList5_sortingMode1.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList5_sortingMode2() throws ParseException {
        boolean result = true;
        taskList = testModel(5);
        refreshTasklist(2);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList5_sortingMode2.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }

    @Test
    public void testTaskList6_sortingMode0() throws ParseException {
        boolean result = true;
        taskList = testModel(6);
        refreshTasklist(0);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList6_sortingMode0.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList6_sortingMode1() throws ParseException {
        boolean result = true;
        taskList = testModel(6);
        refreshTasklist(1);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList6_sortingMode1.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    @Test
    public void testTaskList6_sortingMode2() throws ParseException {
        boolean result = true;
        taskList = testModel(6);
        refreshTasklist(2);
        for(int i=0; i<taskList.size();i++){
            if(taskList.get(i).getId()!=expectedTaskList6_sortingMode2.get(i).getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }


    public void refreshTasklist(int sortingMode) throws ParseException {
        if(taskList==null || taskList.isEmpty()){
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

    public void expectedListGenerator(){                            //Expected Task List created by human logic
        expectedTaskList1_sortingMode0.add(T3);
        expectedTaskList1_sortingMode0.add(T8);
        expectedTaskList1_sortingMode0.add(T9);

        expectedTaskList1_sortingMode1.add(T3);
        expectedTaskList1_sortingMode1.add(T8);
        expectedTaskList1_sortingMode1.add(T9);

        expectedTaskList1_sortingMode2.add(T9);
        expectedTaskList1_sortingMode2.add(T8);
        expectedTaskList1_sortingMode2.add(T3);

        expectedTaskList2_sortingMode0.add(T12);
        expectedTaskList2_sortingMode0.add(T10);
        expectedTaskList2_sortingMode0.add(T13);
        expectedTaskList2_sortingMode0.add(T11);

        expectedTaskList2_sortingMode1.add(T12);
        expectedTaskList2_sortingMode1.add(T10);
        expectedTaskList2_sortingMode1.add(T13);
        expectedTaskList2_sortingMode1.add(T11);

        expectedTaskList2_sortingMode2.add(T12);
        expectedTaskList2_sortingMode2.add(T10);
        expectedTaskList2_sortingMode2.add(T13);
        expectedTaskList2_sortingMode2.add(T11);

        expectedTaskList3_sortingMode0.add(T0);
        expectedTaskList3_sortingMode0.add(T1);
        expectedTaskList3_sortingMode0.add(T3);
        expectedTaskList3_sortingMode0.add(T10);
        expectedTaskList3_sortingMode0.add(T6);

        expectedTaskList3_sortingMode1.add(T0);
        expectedTaskList3_sortingMode1.add(T1);
        expectedTaskList3_sortingMode1.add(T3);
        expectedTaskList3_sortingMode1.add(T10);
        expectedTaskList3_sortingMode1.add(T6);

        expectedTaskList3_sortingMode2.add(T10);
        expectedTaskList3_sortingMode2.add(T3);
        expectedTaskList3_sortingMode2.add(T1);
        expectedTaskList3_sortingMode2.add(T0);
        expectedTaskList3_sortingMode2.add(T6);

        expectedTaskList4_sortingMode0.add(T4);
        expectedTaskList4_sortingMode0.add(T5);
        expectedTaskList4_sortingMode0.add(T7);
        expectedTaskList4_sortingMode0.add(T8);
        expectedTaskList4_sortingMode0.add(T9);
        expectedTaskList4_sortingMode0.add(T12);
        expectedTaskList4_sortingMode0.add(T2);

        expectedTaskList4_sortingMode1.add(T4);
        expectedTaskList4_sortingMode1.add(T5);
        expectedTaskList4_sortingMode1.add(T7);
        expectedTaskList4_sortingMode1.add(T12);
        expectedTaskList4_sortingMode1.add(T8);
        expectedTaskList4_sortingMode1.add(T9);
        expectedTaskList4_sortingMode1.add(T2);

        expectedTaskList4_sortingMode2.add(T12);
        expectedTaskList4_sortingMode2.add(T9);
        expectedTaskList4_sortingMode2.add(T8);
        expectedTaskList4_sortingMode2.add(T7);
        expectedTaskList4_sortingMode2.add(T5);
        expectedTaskList4_sortingMode2.add(T4);
        expectedTaskList4_sortingMode2.add(T2);

        expectedTaskList5_sortingMode0.add(T1);
        expectedTaskList5_sortingMode0.add(T4);
        expectedTaskList5_sortingMode0.add(T3);
        expectedTaskList5_sortingMode0.add(T7);
        expectedTaskList5_sortingMode0.add(T9);
        expectedTaskList5_sortingMode0.add(T12);
        expectedTaskList5_sortingMode0.add(T10);
        expectedTaskList5_sortingMode0.add(T6);

        expectedTaskList5_sortingMode1.add(T4);
        expectedTaskList5_sortingMode1.add(T7);
        expectedTaskList5_sortingMode1.add(T12);
        expectedTaskList5_sortingMode1.add(T1);
        expectedTaskList5_sortingMode1.add(T3);
        expectedTaskList5_sortingMode1.add(T9);
        expectedTaskList5_sortingMode1.add(T10);
        expectedTaskList5_sortingMode1.add(T6);

        expectedTaskList5_sortingMode2.add(T1);
        expectedTaskList5_sortingMode2.add(T12);
        expectedTaskList5_sortingMode2.add(T3);
        expectedTaskList5_sortingMode2.add(T10);
        expectedTaskList5_sortingMode2.add(T9);
        expectedTaskList5_sortingMode2.add(T4);
        expectedTaskList5_sortingMode2.add(T7);
        expectedTaskList5_sortingMode2.add(T6);

        expectedTaskList6_sortingMode0.add(T0);
        expectedTaskList6_sortingMode0.add(T1);
        expectedTaskList6_sortingMode0.add(T4);
        expectedTaskList6_sortingMode0.add(T5);
        expectedTaskList6_sortingMode0.add(T3);
        expectedTaskList6_sortingMode0.add(T7);
        expectedTaskList6_sortingMode0.add(T8);
        expectedTaskList6_sortingMode0.add(T9);
        expectedTaskList6_sortingMode0.add(T12);
        expectedTaskList6_sortingMode0.add(T10);
        expectedTaskList6_sortingMode0.add(T2);
        expectedTaskList6_sortingMode0.add(T6);
        expectedTaskList6_sortingMode0.add(T13);
        expectedTaskList6_sortingMode0.add(T11);

        expectedTaskList6_sortingMode1.add(T4);
        expectedTaskList6_sortingMode1.add(T5);
        expectedTaskList6_sortingMode1.add(T7);
        expectedTaskList6_sortingMode1.add(T12);
        expectedTaskList6_sortingMode1.add(T0);
        expectedTaskList6_sortingMode1.add(T1);
        expectedTaskList6_sortingMode1.add(T3);
        expectedTaskList6_sortingMode1.add(T8);
        expectedTaskList6_sortingMode1.add(T9);
        expectedTaskList6_sortingMode1.add(T10);
        expectedTaskList6_sortingMode1.add(T2);
        expectedTaskList6_sortingMode1.add(T13);
        expectedTaskList6_sortingMode1.add(T6);
        expectedTaskList6_sortingMode1.add(T11);

        expectedTaskList6_sortingMode2.add(T12);
        expectedTaskList6_sortingMode2.add(T10);
        expectedTaskList6_sortingMode2.add(T9);
        expectedTaskList6_sortingMode2.add(T8);
        expectedTaskList6_sortingMode2.add(T7);
        expectedTaskList6_sortingMode2.add(T5);
        expectedTaskList6_sortingMode2.add(T4);
        expectedTaskList6_sortingMode2.add(T3);
        expectedTaskList6_sortingMode2.add(T1);
        expectedTaskList6_sortingMode2.add(T0);
        expectedTaskList6_sortingMode2.add(T13);
        expectedTaskList6_sortingMode2.add(T11);
        expectedTaskList6_sortingMode2.add(T6);
        expectedTaskList6_sortingMode2.add(T2);
    }
}