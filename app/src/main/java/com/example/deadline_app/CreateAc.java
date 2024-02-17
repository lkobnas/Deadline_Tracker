package com.example.deadline_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deadline_app.Model.UserModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.hash.Hashing;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;


public class CreateAc extends AppCompatActivity {
    TextInputLayout phone, username, password;
    Button createAC;
    TextView toLogin;
    LinearLayout linearLayout;

    FirebaseDatabase root;
    DatabaseReference reference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_new_account);
        getSupportActionBar().hide();
        root = FirebaseDatabase.getInstance();
        reference = root.getReference("users");

        phone = findViewById(R.id.create_phone);
        username = findViewById(R.id.create_username);
        password = findViewById(R.id.create_password);
        createAC = findViewById(R.id.button_create_ac);
        toLogin = findViewById(R.id.toLogin);
        linearLayout = findViewById(R.id.layout_createAC);

        createAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allowCreate(view);
            }
        });
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAc.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        linearLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideKeyboard(v);
                    Log.d("foucs", "lose focus: ");
                }
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void allowCreate(View view) {
        if (!validateFields()) {
            return;
        }
        String _phone = phone.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();
        String _username = username.getEditText().getText().toString().trim();
        String _hashedPw = Hashing.sha256().hashString(_password, StandardCharsets.UTF_8).toString();

        Query checkUser = FirebaseDatabase.getInstance().getReference("users").orderByChild("phone").equalTo(_phone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    //Create Account successful

                    UserModel userModel = new UserModel(_phone, _username, _hashedPw);
                    reference.child(_phone).setValue(userModel);
                    reference.child(_phone).child("data").setValue("0");

                    Intent intent = new Intent(CreateAc.this, Login.class);
                    intent.putExtra("phone", _phone);
                    intent.putExtra("password", _password);
                    Toast.makeText(CreateAc.this, "Account Created", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);

                } else {
                    Toast.makeText(CreateAc.this, "Phone number already exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CreateAc.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private boolean validateFields() {
        String _phone = phone.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();
        String _username = username.getEditText().getText().toString().trim();
        if (_phone.isEmpty()) {
            phone.setError("Phone number cannot be empty");
            phone.requestFocus();
            return false;
        } else if (_username.isEmpty()) {
            phone.setError(null);
            phone.clearFocus();
            username.setError("Username cannot be empty");
            username.requestFocus();
            return false;
        } else if (_username.length()>=15) {
            phone.setError(null);
            phone.clearFocus();
            username.setError("Username cannot exceeds 15 characters");
            username.requestFocus();
            return false;
        } else if (_password.isEmpty()) {
            phone.setError(null);
            phone.clearFocus();
            username.setError(null);
            username.clearFocus();
            password.setError("Password cannot be empty");
            password.requestFocus();
            return false;
        } else {
            return true;

        }

    }
}
