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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

         Button btn_register;
        TextInputEditText username,email,password;
        FirebaseAuth auth;
        TextView tvlogin;
        DatabaseReference refereance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        btn_register=findViewById(R.id.btn_register);
        tvlogin=findViewById(R.id.tvlogin);
        auth =FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username=username.getText().toString().trim();
                String txt_email=email.getText().toString().trim();
                String txt_password=password.getText().toString().trim();
                if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)){
                    Toast.makeText(SignUp.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else if(txt_password.length()<8){
                    Toast.makeText(SignUp.this, "Password must contain at least 8 letters", Toast.LENGTH_SHORT).show();
                }
                else{
                    register(txt_username,txt_email,txt_password);
                }
            }
        });

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this,LogIn.class);
                startActivity(intent);
            }
        });

    }
    private void register(final String username, final String email, final String password){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser firebaseuser = auth.getCurrentUser();
                   UserInfo info=new UserInfo(username,email,password);
                    String userid = firebaseuser.getUid();
                    refereance = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    refereance.setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                Toast.makeText(SignUp.this, "Success", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(SignUp.this, "Failed to SIGN UP", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
