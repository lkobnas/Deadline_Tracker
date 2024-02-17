//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app;

import java.text.ParseException;

public interface MyCallback {
    void updateMainText() throws ParseException;
    void refreshTasklist(int sortingMode) throws ParseException;
}
