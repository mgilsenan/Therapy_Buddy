package com.example.therapybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class ThoughtRecordActivity extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    List<Spinner> negativeWordSpinners = new LinkedList<>();
    List<TextInputLayout> negativeWordSpinnerValues = new LinkedList<>();
    List<Spinner> newNegativeWordSpinners = new LinkedList<>();
    List<TextInputLayout> newNegativeWordSpinnerValues = new LinkedList<>();
    List<SwitchMaterial> distortionSwitches = new LinkedList<SwitchMaterial>();

    TextInputLayout upsetting_event_description,

    rational_responses_description;

    RadioButton outcome_radioButton_1, outcome_radioButton_2, outcome_radioButton_3, outcome_radioButton_4;
    RadioGroup outcome_radioGroup;
    Button thoughtRecordSubmitBtn;
    String databaseChild = "thoughtRecord";

    //TO KEEP IN CASE THE LISTS DON'T WORK FOR SOME REASON

    //    Spinner negativeSpinnerWord1, negativeSpinnerWord2, negativeSpinnerWord3,
//            negativeSpinnerWord4, negativeSpinnerWord5, negativeSpinnerWord6,
//            newNegativeSpinnerWord1, newNegativeSpinnerWord2, newNegativeSpinnerWord3,
//            newNegativeSpinnerWord4, newNegativeSpinnerWord5, newNegativeSpinnerWord6;
//    negativeSpinnerValue1, negativeSpinnerValue2, negativeSpinnerValue3,
//    negativeSpinnerValue4, negativeSpinnerValue5, negativeSpinnerValue6,
//    newNegativeSpinnerValue1, newNegativeSpinnerValue2, newNegativeSpinnerValue3,
//    newNegativeSpinnerValue4, newNegativeSpinnerValue5, newNegativeSpinnerValue6,
    //    SwitchMaterial distortion1_switch, distortion2_switch, distortion3_switch,
//            distortion4_switch, distortion5_switch, distortion6_switch,
//            distortion7_switch, distortion8_switch, distortion9_switch,
//            distortion10_switch;

    protected void setUp(){
        // Hooking the front-end assets to the back-end
        negativeWordSpinners.add((Spinner) findViewById(R.id.negative_feelings_word_1));
        negativeWordSpinners.add((Spinner) findViewById(R.id.negative_feelings_word_2));
        negativeWordSpinners.add((Spinner) findViewById(R.id.negative_feelings_word_3));
        negativeWordSpinners.add((Spinner) findViewById(R.id.negative_feelings_word_4));
        negativeWordSpinners.add((Spinner) findViewById(R.id.negative_feelings_word_5));
        negativeWordSpinners.add((Spinner) findViewById(R.id.negative_feelings_word_6));
        newNegativeWordSpinners.add((Spinner) findViewById(R.id.new_negative_feelings_word_1));
        newNegativeWordSpinners.add((Spinner) findViewById(R.id.new_negative_feelings_word_2));
        newNegativeWordSpinners.add((Spinner) findViewById(R.id.new_negative_feelings_word_3));
        newNegativeWordSpinners.add((Spinner) findViewById(R.id.new_negative_feelings_word_4));
        newNegativeWordSpinners.add((Spinner) findViewById(R.id.new_negative_feelings_word_5));
        newNegativeWordSpinners.add((Spinner) findViewById(R.id.new_negative_feelings_word_6));
        negativeWordSpinnerValues.add(findViewById(R.id.negative_feelings_rating_1));
        negativeWordSpinnerValues.add(findViewById(R.id.negative_feelings_rating_2));
        negativeWordSpinnerValues.add(findViewById(R.id.negative_feelings_rating_3));
        negativeWordSpinnerValues.add(findViewById(R.id.negative_feelings_rating_4));
        negativeWordSpinnerValues.add(findViewById(R.id.negative_feelings_rating_5));
        negativeWordSpinnerValues.add(findViewById(R.id.negative_feelings_rating_6));
        newNegativeWordSpinnerValues.add(findViewById(R.id.new_negative_feelings_rating_1));
        newNegativeWordSpinnerValues.add(findViewById(R.id.new_negative_feelings_rating_2));
        newNegativeWordSpinnerValues.add(findViewById(R.id.new_negative_feelings_rating_3));
        newNegativeWordSpinnerValues.add(findViewById(R.id.new_negative_feelings_rating_4));
        newNegativeWordSpinnerValues.add(findViewById(R.id.new_negative_feelings_rating_5));
        newNegativeWordSpinnerValues.add(findViewById(R.id.new_negative_feelings_rating_6));
        upsetting_event_description = findViewById(R.id.upsetting_event_description);
        rational_responses_description = findViewById(R.id.rational_responses_description);
        distortionSwitches.add(findViewById(R.id.distortion1_switch));
        distortionSwitches.add(findViewById(R.id.distortion2_switch));
        distortionSwitches.add(findViewById(R.id.distortion3_switch));
        distortionSwitches.add(findViewById(R.id.distortion4_switch));
        distortionSwitches.add(findViewById(R.id.distortion5_switch));
        distortionSwitches.add(findViewById(R.id.distortion6_switch));
        distortionSwitches.add(findViewById(R.id.distortion7_switch));
        distortionSwitches.add(findViewById(R.id.distortion8_switch));
        distortionSwitches.add(findViewById(R.id.distortion9_switch));
        distortionSwitches.add(findViewById(R.id.distortion10_switch));
//        outcome_radioButton_1 = findViewById(R.id.outcome_radioButton_1);
//        outcome_radioButton_2 = findViewById(R.id.outcome_radioButton_2);
//        outcome_radioButton_3 = findViewById(R.id.outcome_radioButton_3);
//        outcome_radioButton_4 = findViewById(R.id.outcome_radioButton_4);
//        outcome_radioButton = findViewById(R.id.outcome_radioButton_4);
        outcome_radioGroup = findViewById(R.id.outcome_radioGroup);

        // Populating the spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.negativeFeelingsArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for(Spinner spinner: negativeWordSpinners){
            spinner.setAdapter(adapter);
        }
        for(Spinner spinner: newNegativeWordSpinners){
            spinner.setAdapter(adapter);
        }

        //Getting the submit button
        thoughtRecordSubmitBtn = findViewById(R.id.complete_worksheet_btn);
    }

    protected void thoughtRecrodSubmitBtnAction(){
        thoughtRecordSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                databaseReference = rootNode.getReference("user");
                if (isInputValid()) {
                    DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    String date = d.format(cal.getTime());
                    final String phone = LoginActivity.getUser().getPhone();
                    FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            Toast toast1;
                            if (snapshot.child(phone).child(databaseChild).child(date).exists()) {
                                FirebaseDatabase.getInstance().getReference("user").child(phone).child(databaseChild).child(date);
                                toast1 = Toast.makeText(ThoughtRecordActivity.this, "Today's thought record has been completed already!", Toast.LENGTH_LONG);
                                toast1.show();
                            }
                            else {
                                ThoughtRecord thoughtRecord = extractThoughtRecord();
                                databaseReference.child(LoginActivity.getUser().phone).child(databaseChild).child(date).setValue(thoughtRecord);  //Get the array
                                toast1 = Toast.makeText(ThoughtRecordActivity.this, "Successful", Toast.LENGTH_LONG);
                                toast1.show();
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

    protected boolean isInputValid(){
        // description not empty, list of negative feelings not empty, updated list not empty, etc
        if(upsetting_event_description.getEditText().getText().toString().isEmpty()){
            Toast msg = Toast.makeText(ThoughtRecordActivity.this, "You need to describe the event", Toast.LENGTH_LONG);
            msg.show();
            return false;
        }



        return true;
    }

    protected ThoughtRecord extractThoughtRecord(){
        // extract data from the frontend fields
        return new ThoughtRecord();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_record);
        setUp();
        thoughtRecrodSubmitBtnAction();
    }

}