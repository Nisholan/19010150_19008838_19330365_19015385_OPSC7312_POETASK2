package com.example.locallandmark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnLogOut ;
    Button btnGoToApp;
    TextView txtViewEmail;
    Switch systemSwitch;
    Spinner spnLandmarkType;

    FirebaseAuth fAuth ;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogOut = findViewById(R.id.btnLogOut);
        btnGoToApp = findViewById(R.id.btnGoToApp);
        txtViewEmail = findViewById(R.id.txtViewEmail);
        systemSwitch = findViewById(R.id.systemSwitch);
        spnLandmarkType = findViewById(R.id.spnLandmarkType);

        //Populate Spinner With Array List created in res-strings.xml

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        DocumentReference docref = fStore.collection("users").document(userID);

        //set user profile information
        docref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                txtViewEmail.setText(value.getString("email"));
                systemSwitch.setText(value.getString("system"));


            }
        });

        //update measurement system
        if (systemSwitch.isChecked()) {

            Map<String, Object> map = new HashMap<>();
            map.put("system", "mi");

            docref.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(MainActivity.this, "Changes Saved", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage().trim(), Toast.LENGTH_SHORT).show();
                }
            });
        }


        //Log Out
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut(); //user logs out
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

        btnGoToApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),GPSTracking.class));

            }
        });

    }
}