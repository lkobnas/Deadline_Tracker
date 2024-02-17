//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app;

import android.content.DialogInterface;

import java.text.ParseException;

public interface DialogCloseListener {

    public void handleDialogClose(DialogInterface dialog) throws ParseException;

}
