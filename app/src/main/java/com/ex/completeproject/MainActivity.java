package com.ex.completeproject;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    // Define a TAG for logging, good practice
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main1);


        EditText  username= (EditText) findViewById(R.id.username);
        EditText password =(EditText) findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        // admin and admin

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get text from EditText and trim whitespace
                String enteredUsername = username.getText().toString().trim();
                String enteredPassword = password.getText().toString().trim();

                // For debugging: Log the trimmed values
                Log.d(TAG, "Attempting login with Username: '" + enteredUsername + "'");
                Log.d(TAG, "Attempting login with Password: '" + enteredPassword + "'");

                if (enteredUsername.equals("admin") && enteredPassword.equals("admin")) {
                    // correct
                    Toast.makeText(MainActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Login successful for user: " + enteredUsername);
                    Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                    startActivity(intent);
                } else {
                    // incorrect
                    Toast.makeText(MainActivity.this, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "Login failed. Entered Username: '" + enteredUsername + "', Entered Password: '" + enteredPassword + "'");
                }
            }
        });
    }
}
