package com.example.therapybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MoodLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_log);

        // This is how you get the active user's phone number. Use this to
        String userPhoneNumber = LoginActivity.getUser().phone;
    }
}