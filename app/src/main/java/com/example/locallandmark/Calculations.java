package com.example.locallandmark;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Calculations {

    //In main class: Check whether km/mi in firestore database based on userID
    public Double convertToMiles(Double rKm){
        double rmi = rKm/1.609;
        return rmi;
    }

}
