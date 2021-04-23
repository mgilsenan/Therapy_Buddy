package com.example.therapybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp();
        setContentView(R.layout.activity_statistics);
    }

    private static String TAG = "StatisticsActivity";

    // local user
    public static User user ;

    // UI
    CardView thoughtRecordCardView;
    CardView gradedExposureCardView;
    CardView fearDiaryCardView;
    CardView situationalAnalysisCardView;
    TextView hotStreakCounter;


    // firebase
    DatabaseReference reference;
    FirebaseAuth fAuth;

    protected void setUp(){
        //Hooks
        thoughtRecordCardView = findViewById(R.id.thoughtRecordCardView);
        gradedExposureCardView = findViewById(R.id.gradedExposureCardView);
        fearDiaryCardView = findViewById(R.id.fearDiaryCardView);
        situationalAnalysisCardView = findViewById(R.id.fearDiaryCardView);
        hotStreakCounter = findViewById(R.id.hotStreakCounter);
        user = new User();
        fAuth = FirebaseAuth.getInstance();
    }

    public void thoughtRecordStatisticsButtonAction(View view){
        startActivity(new Intent(getApplicationContext(),ThoughtRecordStatisticsActivity.class));
    }
}