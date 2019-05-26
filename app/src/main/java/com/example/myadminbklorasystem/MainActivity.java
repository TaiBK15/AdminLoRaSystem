package com.example.myadminbklorasystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText edt_email, edt_password, edt_verify_password;
    private Button btn_signup;
    private ProgressBar progressBar;

    private String email, password, verify_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password_login);
        edt_verify_password = findViewById(R.id.edt_verify_password_login);
        btn_signup = findViewById(R.id.btn_signup);
        progressBar = findViewById(R.id.progressBar);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser(){
        progressBar.setVisibility(View.VISIBLE);

        email = edt_email.getText().toString();
        password = edt_password.getText().toString();
        verify_password = edt_verify_password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(verify_password)) {
            Toast.makeText(getApplicationContext(), "Please type password again!", Toast.LENGTH_LONG).show();
            return;
        }

        if(verify_password.equals(password)){
            if(password.length()<6) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Password must be at least 6 characters!", Toast.LENGTH_LONG).show();
            }
            else{
                mAuth.createUserWithEmailAndPassword(email + "@gmail.com", password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        }else{
            Toast.makeText(getApplicationContext(), "Set up password fail! Please try again", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);}

    }
}
