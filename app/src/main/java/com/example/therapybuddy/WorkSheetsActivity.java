package com.example.therapybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WorkSheetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp();
        setContentView(R.layout.activity_work_sheets);
    }

    private static String TAG = "WorksheetsActivity";

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

    public void thoughtRecordButtonAction(View view){
        startActivity(new Intent(getApplicationContext(),ThoughtRecordActivity.class));
    }
}