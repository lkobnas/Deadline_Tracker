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

import com.google.android.material.textfield.TextInputLayout;
import com.google.common.hash.Hashing;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;

public class Login extends AppCompatActivity {
    TextInputLayout phone, password;
    Button loginButton;
    TextView toCreateAc, noLogin;
    FirebaseDatabase root;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_login);
        getSupportActionBar().hide();

        root = FirebaseDatabase.getInstance();
        phone = findViewById(R.id.login_phone);
        password = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.button_login);
        toCreateAc = findViewById(R.id.toCreateAc);
        noLogin = findViewById(R.id.noLogin);
        linearLayout = findViewById(R.id.layout_login);

        Intent i = getIntent();
        if(i.getExtras()!=null) {
            password.getEditText().setText(i.getExtras().getString("password"));
            phone.getEditText().setText(i.getExtras().getString("phone"));
        }

        toCreateAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, CreateAc.class);
                startActivity(intent);
                finish();
            }
        });

        noLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allowLogin(view);
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

    public void allowLogin(View view) {
        if (!validateFields()) {
            return;
        }
        String _phone = phone.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();
        String _hashedPw = Hashing.sha256().hashString(_password, StandardCharsets.UTF_8).toString();

        Query checkUser = FirebaseDatabase.getInstance().getReference("users").orderByChild("phone").equalTo(_phone);
        Log.d("Query checkUser:", checkUser.toString());
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phone.setError(null);
                    phone.setErrorEnabled(false);

                    String systemPassword = dataSnapshot.child(_phone).child("password").getValue(String.class);
                    if (systemPassword.equals(_hashedPw)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        //Successful login
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("phone",_phone);
                        intent.putExtra("username",dataSnapshot.child(_phone).child("username").getValue(String.class));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                                finish();
                            }
                        },500);


                    }else{
                        Toast.makeText(Login.this, "Wrong phone number or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Wrong phone number or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Login.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validateFields() {
        String _phone = phone.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();
        if (_phone.isEmpty() && _password.isEmpty()) {
            phone.setError("Phone number cannot be empty");
            phone.requestFocus();
            password.setError("Password cannot be empty");
            return false;
        }else if ( _password.isEmpty()) {
            phone.setError(null);
            phone.clearFocus();
            password.setError("Password cannot be empty");
            password.requestFocus();
            return false;
        } else if(_phone.isEmpty()) {
            password.setError(null);
            password.clearFocus();
            phone.setError("Phone number cannot be empty");
            phone.requestFocus();
            return false;
        }else{
                return true;

        }

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
