package com.example.therapybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MoodLogActivity extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private boolean relativeValue[] = new boolean[5];
    Button moodLogSubmitBtn;
    TextInputLayout moodLogFeelings;
    RelativeLayout relativeLayout_rad;
    RelativeLayout relativeLayout_good;
    RelativeLayout relativeLayout_neutral;
    RelativeLayout relativeLayout_bad;
    RelativeLayout relativeLayout_awful;

    ImageView[] selected = new ImageView[5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_log);
        setUp();
        moodLogSubmitBtnAction();
        Utils.init(this);
    }

    protected void setUp(){
        moodLogSubmitBtn = findViewById(R.id.moodLogSubmitBtn);
        moodLogFeelings = findViewById(R.id.moodLogFeelings);

        relativeLayout_rad = findViewById(R.id.relativeLayout_rad);
        relativeLayout_good = findViewById(R.id.relativeLayout_good);
        relativeLayout_neutral = findViewById(R.id.relativeLayout_neutral);
        relativeLayout_bad = findViewById(R.id.relativeLayout_bad);
        relativeLayout_awful = findViewById(R.id.relativeLayout_awful);

        selected[0] = findViewById(R.id.Selected0);
        selected[1] = findViewById(R.id.Selected1);
        selected[2] = findViewById(R.id.Selected2);
        selected[3] = findViewById(R.id.Selected3);
        selected[4] = findViewById(R.id.Selected4);
    }

    protected void moodLogSubmitBtnAction(){
        // @TODO Main display activity
        moodLogSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("user");
                if (!validateInput()) {
                    toast = Toast.makeText(MoodLogActivity.this, "Please select a mood", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    String date = d.format(cal.getTime());
                    final String phone = LoginActivity.getUser().getPhone();
                    FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            Toast toast1;
                            if (snapshot.child(phone).child("moodLog").child(date).exists()) {
                                FirebaseDatabase.getInstance().getReference("user").child(phone).child("moodLog").child(date);
                                toast1 = Toast.makeText(MoodLogActivity.this, "Today's mood has been selected already!", Toast.LENGTH_LONG);
                                toast1.show();
                            }
                            else {
                                if(snapshot.child(phone).child("Therapist").exists()) {
                                    toast1 = Toast.makeText(MoodLogActivity.this, "Therapist aren't allowed to use this!", Toast.LENGTH_LONG);
                                    toast1.show();
                                }
                                else {
                                    int mood = 0;
                                    String moodDetails = moodLogFeelings.getEditText().getText().toString().trim();
                                    for (int i = 0; i < relativeValue.length; i++) {
                                        if (relativeValue[0])
                                            mood = 4;
                                        else if (relativeValue[1])
                                            mood = 3;
                                        else if (relativeValue[2])
                                            mood = 2;
                                        else if (relativeValue[3])
                                            mood = 1;
                                        else if (relativeValue[4])
                                            mood = 0;
                                    }
                                    MoodLog m = new MoodLog(mood, moodDetails);
                                    reference.child(LoginActivity.getUser().phone).child("moodLog").child(date).setValue(m);  //Get the array
                                    toast1 = Toast.makeText(MoodLogActivity.this, "Successful", Toast.LENGTH_LONG);
                                    toast1.show();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    protected boolean validateInput(){
        boolean validation = false;
        for(boolean value : relativeValue){
            if(value && !validation){
                validation = true;
            }
        }
        return validation;
    }

    public void awfulSelected(View view) {
        for (int i = 0; i < relativeValue.length; i++ ) {
            selected[i].setVisibility(View.INVISIBLE);
            relativeValue[i] = false;
        }
        selected[4].setVisibility(View.VISIBLE);
        relativeValue[4] = true;
    }

    public void badSelected(View view) {
        for (int i = 0; i < relativeValue.length; i++ ) {
            selected[i].setVisibility(View.INVISIBLE);
            relativeValue[i] = false;
        }
        selected[3].setVisibility(View.VISIBLE);
        relativeValue[3] = true;
    }

    public void neutralSelected(View view) {
        for (int i = 0; i < relativeValue.length; i++ ) {
            selected[i].setVisibility(View.INVISIBLE);
            relativeValue[i] = false;
        }
        selected[2].setVisibility(View.VISIBLE);
        relativeValue[2] = true;
    }

    public void goodSelected(View view) {
        for (int i = 0; i < relativeValue.length; i++ ) {
            selected[i].setVisibility(View.INVISIBLE);
            relativeValue[i] = false;
        }
        selected[1].setVisibility(View.VISIBLE);
        relativeValue[1] = true;
    }

    public void radSelected(View view) {
        for (int i = 0; i < relativeValue.length; i++ ) {
            selected[i].setVisibility(View.INVISIBLE);
            relativeValue[i] = false;
        }
        selected[0].setVisibility(View.VISIBLE);
        relativeValue[0] = true;
    }

    public void moodLogDisplay(View view) {
        startActivity(new Intent(getApplicationContext(),MoodLogDisplayActivity.class));
    }
}