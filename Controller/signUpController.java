package com.projectf.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class signUpController {
     private static final String API_KEY = "AIzaSyCUvcZxXcvVGnCaZjKMnbiN0YR2GS1YDUM";


    public boolean signUp(String email, String password) {
        try {
          // URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key="+API_KEY);
              URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key="+API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String payload = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
                email, password);
                System.out.println(payload);
            //OutputStream os = null;
 
            try (OutputStream os = conn.getOutputStream()){
                 os.write(payload.getBytes(StandardCharsets.UTF_8));
            }


           // os = conn.getOutputStream();
           // os.write(payload.getBytes());
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // Login successful
                return true;
            } else {
                // Login failed
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                }
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;

          
        }
    }
}