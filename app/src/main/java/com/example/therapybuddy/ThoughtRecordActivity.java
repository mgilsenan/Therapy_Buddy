package com.example.therapybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThoughtRecordActivity extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    Spinner negativeSpinnerWord1, negativeSpinnerWord2, negativeSpinnerWord3,
            negativeSpinnerWord4, negativeSpinnerWord5, negativeSpinnerWord6,
            newNegativeSpinnerWord1, newNegativeSpinnerWord2, newNegativeSpinnerWord3,
            newNegativeSpinnerWord4, newNegativeSpinnerWord5, newNegativeSpinnerWord6;

    TextInputLayout upsetting_event_description,
    negativeSpinnerValue1, negativeSpinnerValue2, negativeSpinnerValue3,
    negativeSpinnerValue4, negativeSpinnerValue5, negativeSpinnerValue6,
    newNegativeSpinnerValue1, newNegativeSpinnerValue2, newNegativeSpinnerValue3,
    newNegativeSpinnerValue4, newNegativeSpinnerValue5, newNegativeSpinnerValue6,
    rational_responses_description;

    SwitchMaterial distortion1_switch, distortion2_switch, distortion3_switch,
            distortion4_switch, distortion5_switch, distortion6_switch,
            distortion7_switch, distortion8_switch, distortion9_switch,
            distortion10_switch;

    RadioButton outcome_radioButton_1, outcome_radioButton_2, outcome_radioButton_3, outcome_radioButton_4;
    RadioGroup outcome_radioGroup;

    protected void setUp(){
        // Hooking the front-end assets to the back-end
        negativeSpinnerWord1 = (Spinner) findViewById(R.id.negative_feelings_word_1);
        negativeSpinnerWord2 = (Spinner) findViewById(R.id.negative_feelings_word_2);
        negativeSpinnerWord3 = (Spinner) findViewById(R.id.negative_feelings_word_3);
        negativeSpinnerWord4 = (Spinner) findViewById(R.id.negative_feelings_word_4);
        negativeSpinnerWord5 = (Spinner) findViewById(R.id.negative_feelings_word_5);
        negativeSpinnerWord6 = (Spinner) findViewById(R.id.negative_feelings_word_6);
        newNegativeSpinnerWord1 = (Spinner) findViewById(R.id.new_negative_feelings_word_1);
        newNegativeSpinnerWord2 = (Spinner) findViewById(R.id.new_negative_feelings_word_2);
        newNegativeSpinnerWord3 = (Spinner) findViewById(R.id.new_negative_feelings_word_3);
        newNegativeSpinnerWord4 = (Spinner) findViewById(R.id.new_negative_feelings_word_4);
        newNegativeSpinnerWord5 = (Spinner) findViewById(R.id.new_negative_feelings_word_5);
        newNegativeSpinnerWord6 = (Spinner) findViewById(R.id.new_negative_feelings_word_6);
        negativeSpinnerValue1 = findViewById(R.id.negative_feelings_rating_1);
        negativeSpinnerValue2 = findViewById(R.id.negative_feelings_rating_2);
        negativeSpinnerValue3 = findViewById(R.id.negative_feelings_rating_3);
        negativeSpinnerValue4 = findViewById(R.id.negative_feelings_rating_4);
        negativeSpinnerValue5 = findViewById(R.id.negative_feelings_rating_5);
        negativeSpinnerValue6 = findViewById(R.id.negative_feelings_rating_6);
        newNegativeSpinnerValue1 = findViewById(R.id.new_negative_feelings_rating_1);
        newNegativeSpinnerValue2 = findViewById(R.id.new_negative_feelings_rating_2);
        newNegativeSpinnerValue3 = findViewById(R.id.new_negative_feelings_rating_3);
        newNegativeSpinnerValue4 = findViewById(R.id.new_negative_feelings_rating_4);
        newNegativeSpinnerValue5 = findViewById(R.id.new_negative_feelings_rating_5);
        newNegativeSpinnerValue6 = findViewById(R.id.new_negative_feelings_rating_6);
        upsetting_event_description = findViewById(R.id.upsetting_event_description);
        rational_responses_description = findViewById(R.id.rational_responses_description);
        distortion1_switch = findViewById(R.id.distortion1_switch);
        distortion2_switch = findViewById(R.id.distortion2_switch);
        distortion3_switch = findViewById(R.id.distortion3_switch);
        distortion4_switch = findViewById(R.id.distortion4_switch);
        distortion5_switch = findViewById(R.id.distortion5_switch);
        distortion6_switch = findViewById(R.id.distortion6_switch);
        distortion7_switch = findViewById(R.id.distortion7_switch);
        distortion8_switch = findViewById(R.id.distortion8_switch);
        distortion9_switch = findViewById(R.id.distortion9_switch);
        distortion10_switch = findViewById(R.id.distortion10_switch);
        outcome_radioButton_1 = findViewById(R.id.outcome_radioButton_1);
        outcome_radioButton_2 = findViewById(R.id.outcome_radioButton_2);
        outcome_radioButton_3 = findViewById(R.id.outcome_radioButton_3);
        outcome_radioButton_4 = findViewById(R.id.outcome_radioButton_4);
        outcome_radioGroup = findViewById(R.id.outcome_radioGroup);

        // Populating the spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.negativeFeelingsArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        negativeSpinnerWord1.setAdapter(adapter);
        negativeSpinnerWord2.setAdapter(adapter);
        negativeSpinnerWord3.setAdapter(adapter);
        negativeSpinnerWord4.setAdapter(adapter);
        negativeSpinnerWord5.setAdapter(adapter);
        negativeSpinnerWord6.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_record);
        setUp();
    }

}