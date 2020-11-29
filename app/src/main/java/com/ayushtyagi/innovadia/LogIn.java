package com.ayushtyagi.innovadia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    TextInputEditText email,password;
    Button btn_login;
    TextView tvsignUp;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        auth=FirebaseAuth.getInstance();

        password=findViewById(R.id.username);
        email=findViewById(R.id.email);
        btn_login=findViewById(R.id.btn_register);
        tvsignUp=findViewById(R.id.tvlogin);

        btn_login.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             String txt_email = email.getText().toString();
                                             String txt_password = password.getText().toString();

                                             if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                                                 Toast.makeText(LogIn.this, "All fields required", Toast.LENGTH_SHORT).show();
                                             }

                                             auth.signInWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(LogIn.this,
                                                     new OnCompleteListener<AuthResult>() {
                                                         @Override
                                                         public void onComplete(@NonNull Task<AuthResult> task) {
                                                             if (task.isSuccessful()) {
                                                                 Intent intent = new Intent(LogIn.this, MainActivity.class);
                                                                 startActivity(intent);
                                                                 finish();

                                                             } else {
                                                                 Toast.makeText(LogIn.this, "Login failed", Toast.LENGTH_SHORT).show();
                                                             }
                                                         }


                                                     });
                                         }
                                     });
                tvsignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(LogIn.this,SignUp.class);
                        startActivity(intent);
                    }
                });
    }

    }
