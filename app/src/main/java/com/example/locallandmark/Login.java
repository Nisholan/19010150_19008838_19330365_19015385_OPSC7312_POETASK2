package com.example.locallandmark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText etEmail , etPassword ;
    Button btnLogin, showHideBtn;
    TextView txtViewReg ;
    FirebaseAuth fAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.edtEmail);
        etPassword = findViewById(R.id.edtPassword);
        txtViewReg = findViewById(R.id.txtViewSignUp) ;
        btnLogin = findViewById(R.id.btnLogin);
        showHideBtn = findViewById(R.id.showHideBtn);

        //Text View Click for Sign up
        txtViewReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        showHideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(showHideBtn.getText().toString().equals("Show")){
                        showHideBtn.setText("Hide");
                        etPassword.setTransformationMethod(null);
                    } else {
                        showHideBtn.setText("Show");
                        etPassword.setTransformationMethod(new PasswordTransformationMethod());
                    }
                }

        });


        //This button logs user in
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                //check if email is entered or blank
                if(TextUtils.isEmpty(email)){
                    etEmail.setError("Email Address Required");
                    return ;
                }


                //check if password is entered or blank
                if(TextUtils.isEmpty(password)){
                    etPassword.setError("Password Required");
                    return ;
                }


                //password length has to be more than 6
                if(password.length() < 6){
                    etPassword.setError("Password must be 6 characters long");
                }

                //check credentials. Authenticate user
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Login.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });




    }
}