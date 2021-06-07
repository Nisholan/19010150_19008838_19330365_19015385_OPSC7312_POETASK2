package com.example.locallandmark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText etEmail , etPassword ;
    Button btnRegister, showHideBtn;
    TextView txtViewLogin;
    ToggleButton tBtnSystem;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.edtEmail);
        etPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnReg);
        txtViewLogin = findViewById(R.id.txtViewLogin);
        tBtnSystem = findViewById(R.id.tBtnSystem);
        showHideBtn = findViewById(R.id.showHideBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        
       if(fAuth.getCurrentUser() != null){
           startActivity(new Intent(getApplicationContext(),MainActivity.class));
       }

       //Text View Click for Sign in
       txtViewLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),Login.class));
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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    etEmail.setError("Email Address Required");
                    return ;
                }

                if(TextUtils.isEmpty(password)){
                    etPassword.setError("Password Required");
                    return ;
                }

                if(password.length() < 6){
                    etPassword.setError("Password must be 6 characters long");
                }

                //Add code for more complex password. check for alphanumeric characters. Uppercase and Lowercase.

                //register user

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Registered", Toast.LENGTH_SHORT).show();

                            //create collections and documents for Firestore db.
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("email",email);

                            //set pref measurement system
                            if (tBtnSystem.isChecked() == false) {

                                user.put("system", "km");
                            }else{
                                user.put("system", "mi");
                            }


                            user.put("landmarkType" , "Popular");

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: user profile created for: "+userID);

                                }
                            });

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else{
                            Toast.makeText(Register.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });


    }
}