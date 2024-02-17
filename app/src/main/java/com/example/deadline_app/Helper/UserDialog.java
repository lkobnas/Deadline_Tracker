//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.deadline_app.DialogCloseListener;
import com.example.deadline_app.Login;
import com.example.deadline_app.Model.ToDoModel;
import com.example.deadline_app.R;
import com.example.deadline_app.Utils.DatabaseCloud;
import com.example.deadline_app.Utils.DatabaseHandler;

import java.text.ParseException;
import java.util.List;

public class UserDialog extends DialogFragment {
    private Button recover, update, logout;
    private TextView textView;
    private Activity activity;

    private DatabaseHandler db;
    private List<ToDoModel> taskList;
    private DatabaseCloud cloud;
    private String username;
    //ToDoAdapter adapter;



    public UserDialog(Activity activity , DatabaseHandler db,DatabaseCloud cloud, List<ToDoModel> taskList, String username){
        this.activity = activity;
       this.cloud = cloud;
       this.taskList = taskList;
       this.db = db;
       this.username = username;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View view = getActivity().getLayoutInflater().inflate(R.layout.user_dialog,null);
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_dialog, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recover = getView().findViewById(R.id.dialog_button_recover);
        update = getView().findViewById(R.id.dialog_button_update);
        logout = getView().findViewById(R.id.dialog_button_logout);
        textView = getView().findViewById(R.id.dialog_username);

        textView.setText("Welcome, "+username);
        //root = FirebaseDatabase.getInstance();
        //reference = root.getReference("users").child(phoneID);

        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Are you sure you want to overwrite your current list?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                cloud.getCloud();
                                Toast.makeText(getContext(), "Data retrieved", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                                taskList = db.getALlTasks();
                            }
                        });
                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cloud.updateCloud(taskList);
                Toast.makeText(getContext(), "Database updated", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cloud.updateCloud(taskList);
                Intent i = new Intent(activity, Login.class);
                activity.startActivity(i);
                //activity.finish();
            }
        });
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){

            try {
                ((DialogCloseListener)activity).handleDialogClose(dialog);
                Log.d("dialog", "handleDialogClose called ");
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        dismissAllowingStateLoss();
    }
}
