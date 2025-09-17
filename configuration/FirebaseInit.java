package com.projectf.configuration;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;

import com.google.auth.oauth2.GoogleCredentials;

public class FirebaseInit {
    public static void init() throws Exception {
        FileInputStream serviceAccount = new FileInputStream("final/fir-b5640-firebase-adminsdk-fbsvc-ebad184a1a.json");
        //final\fir-b5640-firebase-adminsdk-fbsvc-ebad184a1a.json


        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://fir-b5640-default-rtdb.firebaseio.com/")
            .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
            
        }
    }
}