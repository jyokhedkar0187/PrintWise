package com.projectf.Controller;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseConfig {
    public static void initializeFirebase() {
        try {
            // Only initialize if NOT already initialized
            if (FirebaseApp.getApps().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream(
    "final/fir-b5640-firebase-adminsdk-fbsvc-ebad184a1a.json"
);

              //  FileInputStream serviceAccount = new FileInputStream("final/fir-b5640-firebase-adminsdk-fbsvc-ebad184a1a.json");

                FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://fir-b5640-default-rtdb.firebaseio.com/")
                    .build();

                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase Initialized.");
            } else {
                System.out.println("ℹ Firebase already initialized, skipping re-initialization.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}