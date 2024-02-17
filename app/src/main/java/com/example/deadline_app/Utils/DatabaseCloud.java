//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app.Utils;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.deadline_app.Model.ToDoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseCloud {
    FirebaseDatabase root;
    DatabaseReference reference;
    private String phoneID;
    private DatabaseHandler db;
    private Activity activity;
    //private List<ToDoModel> cloudList;

    public DatabaseCloud(String phone, DatabaseHandler db, Activity activity){
        this.phoneID=phone;
        this.db = db;
        this.activity = activity;
        root = FirebaseDatabase.getInstance();
        reference = root.getReference("users").child(phoneID).child("data");
        //cloudList = new ArrayList<>();
    }

    public void checkUser(List<ToDoModel> taskList){
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref2 = ref1.child("users").child(phoneID);
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("data").getValue() instanceof String){        //New account
                    if(taskList.isEmpty()){           //New user
                        //Welcome message
                        Log.d("login", "onDataChange: new ac, new user");
                    }else{                            //Old user
                        taskList.clear();
                        getCloud();
                        Log.d("login", "onDataChange: new ac, old user");
                    }
                }else{                                                            //Old User //Switch account
                    getCloud();
                    Log.d("login", "onDataChange: old ac, old user");
                    Log.d("dataSnapshot", "false");
                }
                //Map<String, Object> map = (Map<String, Object>) dataSnapshot.child("data").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void updateCloud(List<ToDoModel> taskList){
        reference.setValue("0");                                                                                     //Drop table
        for(int i=0;i<taskList.size();++i){
            reference.child(taskList.get(i).getStringId()).child("TASK").setValue(taskList.get(i).getTask());
            reference.child(taskList.get(i).getStringId()).child("ID").setValue(taskList.get(i).getId());
            reference.child(taskList.get(i).getStringId()).child("DEADLINE").setValue(taskList.get(i).getDeadline());
            reference.child(taskList.get(i).getStringId()).child("STATUS").setValue(taskList.get(i).getStatus());
            reference.child(taskList.get(i).getStringId()).child("IMPORTANCE").setValue(taskList.get(i).getImportance());
        }
    }
    public void getCloud(){
        List<ToDoModel> list = new ArrayList<>();
        Query query = reference;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               //
                db.replaceDatabase();
                for(DataSnapshot child : dataSnapshot.getChildren() ){                                                //Get data from cloud
                    ToDoModel tempModel = new ToDoModel(child.child("ID").getValue(Integer.class),child.child("STATUS").getValue(Integer.class),
                            child.child("IMPORTANCE").getValue(Integer.class),child.child("TASK").getValue(String.class),
                            child.child("DEADLINE").getValue(String.class));
                    list.add(tempModel);
                    db.insertTask(tempModel);
                    Log.d("getCloud: ", list.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
