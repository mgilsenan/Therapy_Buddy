package com.example.therapybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Pair;
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
    // todo change best for great in the frontend, send the word "good,great,etc" instead of a number

    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    List<Spinner> negativeWordSpinners = new LinkedList<>();
    List<TextInputLayout> negativeWordSpinnerValues = new LinkedList<>();
    List<Spinner> newNegativeWordSpinners = new LinkedList<>();
    List<TextInputLayout> newNegativeWordSpinnerValues = new LinkedList<>();
    List<SwitchMaterial> distortionSwitches = new LinkedList<>();

    TextInputLayout upsetting_event_description, automatic_thoughts_description,
    rational_responses_description;


    RadioGroup outcome_radioGroup;
    Button thoughtRecordSubmitBtn;
    String databaseChild = "thoughtRecord";
    String[] distortion_names = {"ALL-OR-NOTHING THINKING", "OVERGENERALIZATION", "MENTAL FILTER",
            "DISCOUNTING THE POSITIVES", "JUMPING TO CONCLUSIONS", "MAGNIFICATION / MINIMIZATION",
            "EMOTIONAL REASONING", "SHOULD STATEMENTS","LABELING", "PERSONALIZING THE BLAME"};


    protected void setUp(){
        // Hooking the front-end assets to the back-end
        upsetting_event_description = findViewById(R.id.upsetting_event_description);
        automatic_thoughts_description = findViewById(R.id.automatic_thoughts_description);
        rational_responses_description = findViewById(R.id.rational_responses_description);

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

    protected void thoughtRecordSubmitBanAction(){
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
                                toast1 = Toast.makeText(ThoughtRecordActivity.this, "Thought Record Completed Successfully!", Toast.LENGTH_LONG);
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
        Toast msg;
        // description not empty, list of negative feelings not empty, updated list not empty, etc
        if(upsetting_event_description.getEditText().getText().toString().isEmpty()){
            msg = Toast.makeText(ThoughtRecordActivity.this, "You need to describe the event", Toast.LENGTH_LONG);
            msg.show();
            return false;
        }

        //negative spinners (for both the original negative words and their reevaluation)
        boolean isOriginalSpinnersNonEmpty = false;
        boolean isNewSpinnersNonEmpty = false;
        for (int i=0; i < negativeWordSpinners.size(); i++){
            if (!(negativeWordSpinners.get(i).getSelectedItem().toString().equals("None")) && !(negativeWordSpinnerValues.get(i).getEditText().getText().toString().isEmpty())){
                isOriginalSpinnersNonEmpty = true;
            }
            if (!(newNegativeWordSpinners.get(i).getSelectedItem().toString().equals("None")) && !(newNegativeWordSpinnerValues.get(i).getEditText().getText().toString().isEmpty())){
                isNewSpinnersNonEmpty = true;
            }
            if (!negativeWordSpinners.get(i).getSelectedItem().toString().equals(newNegativeWordSpinners.get(i).getSelectedItem().toString())){
                //selected different emotions for the second set of spinners
                // todo remove the burden on the user, make the app synchronise the spinners
                msg = Toast.makeText(ThoughtRecordActivity.this, "You must re-evaluate the same emotions", Toast.LENGTH_LONG);
                msg.show();
                return false;
            }

        }
        if (!isOriginalSpinnersNonEmpty){
            msg = Toast.makeText(ThoughtRecordActivity.this, "You need to list and rate at least one negative emotion", Toast.LENGTH_LONG);
            msg.show();
            return false;
        }
        if (!isNewSpinnersNonEmpty){
            msg = Toast.makeText(ThoughtRecordActivity.this, "You need to list and reevaluate at least one negative emotion", Toast.LENGTH_LONG);
            msg.show();
            return false;
        }
        if(automatic_thoughts_description.getEditText().getText().toString().isEmpty()){
            msg = Toast.makeText(ThoughtRecordActivity.this, "You need to describe your automatic thoughts", Toast.LENGTH_LONG);
            msg.show();
            return false;
        }
        if(rational_responses_description.getEditText().getText().toString().isEmpty()){
            msg = Toast.makeText(ThoughtRecordActivity.this, "You need to re-evaluate your thoughts", Toast.LENGTH_LONG);
            msg.show();
            return false;
        }

        if(outcome_radioGroup.getCheckedRadioButtonId() == -1){
            msg = Toast.makeText(ThoughtRecordActivity.this, "You must rate how you feel", Toast.LENGTH_LONG);
            msg.show();
            return false;
        }

        return true;
    }

    protected ThoughtRecord extractThoughtRecord(){
        // text fields
        String event_description = upsetting_event_description.getEditText().getText().toString();
        String automaticThoughts = automatic_thoughts_description.getEditText().getText().toString();
        String rationalResponses = rational_responses_description.getEditText().getText().toString();

        // getting the outcome value from radio group
        int radioButtonID = outcome_radioGroup.getCheckedRadioButtonId();
        View radioButton = outcome_radioGroup.findViewById(radioButtonID);
        int outcomeValue = outcome_radioGroup.indexOfChild(radioButton);

        // Getting emotions and their ratings
        LinkedList<Pair<String,Integer>> negativeFeelingsList = new LinkedList<>();
        LinkedList<Pair<String,Integer>> updatedFeelingsList = new LinkedList<>();
        for (int i=0; i < negativeWordSpinners.size(); i++){
            String emotion = negativeWordSpinners.get(i).getSelectedItem().toString();
            String emotionUpdated = newNegativeWordSpinners.get(i).getSelectedItem().toString();

            if (!emotion.equals("None")) {
                String test = negativeWordSpinnerValues.get(i).getEditText().getText().toString();
                negativeFeelingsList.add(new Pair<String,Integer>(emotion,
                        Integer.parseInt(negativeWordSpinnerValues.get(i).getEditText().getText().toString())));
            }
            if (!emotionUpdated.equals("None")) {
                updatedFeelingsList.add(new Pair<String,Integer>(emotionUpdated,
                        Integer.parseInt(newNegativeWordSpinnerValues.get(i).getEditText().getText().toString())));
            }
        }

        LinkedList<String> distortions = new LinkedList<>();
        for (int i=0; i < distortionSwitches.size(); i++){
            if (distortionSwitches.get(i).isChecked()){
                distortions.add(distortion_names[i]);
            }
        }

        // extract data from the frontend fields
        return new ThoughtRecord(event_description, negativeFeelingsList, automaticThoughts,
                distortions, rationalResponses, updatedFeelingsList, outcomeValue);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_record);
        setUp();
        thoughtRecordSubmitBanAction();
    }

}