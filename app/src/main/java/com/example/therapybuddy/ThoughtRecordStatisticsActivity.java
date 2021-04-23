package com.example.therapybuddy;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.therapybuddy.dataClasses.EmotionRatingPair;
import com.example.therapybuddy.dataClasses.ThoughtRecord;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;


public class ThoughtRecordStatisticsActivity extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    Button generateRecordBtn;
    LineChart lineChart;
    LineDataSet lineDataSet = new LineDataSet(null , null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    String databaseModule = "thoughtRecord";

    // text fields
    TextView totalEntriesView, biggestStreakView, mostOftenEmotionView, mostOftenDistortionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_record_statistics);
        setUp();
    }
    protected void setUp(){
        totalEntriesView = findViewById(R.id.total_entries_value);
        biggestStreakView = findViewById(R.id.biggest_streak_value);
        mostOftenEmotionView = findViewById(R.id.most_often_emotion_value);
        mostOftenDistortionView = findViewById(R.id.most_often_distortion_value);
        generateRecordBtn = findViewById(R.id.generate_records_btn);
        lineChart = findViewById(R.id.lineChart);
        final String phone = LoginActivity.getUser().getPhone();
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(phone).child(databaseModule);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Getting data from the db
                ArrayList<Pair<String, ThoughtRecord>> records = new ArrayList<>();
                ArrayList<Entry> dataVals = new ArrayList<>();
                if(dataSnapshot.hasChildren()){
                    // get all records
                    for(DataSnapshot myDataSnapshot: dataSnapshot.getChildren()){
//                        ThoughtRecord thoughtRecord = myDataSnapshot.getValue(ThoughtRecord.class);
                        records.add(new Pair<>(myDataSnapshot.getKey(), myDataSnapshot.getValue(ThoughtRecord.class)));
//                        dataVals.add(new Entry(Integer.parseInt(dataArray[2]), moodLog.getMood()));
                    }
                    // computing analytics data
                    // total entries
                    int total_entries = records.size();
                    totalEntriesView.setText(Integer.toString(total_entries));

                    // Biggest streak
                    int biggest_streak = 1;
                    int streak = 1;
                    for (int i = 0; i<records.size()-1; i++){
                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1= new SimpleDateFormat("yyyy-MM-dd").parse(records.get(i).first);
                            date2= new SimpleDateFormat("yyyy-MM-dd").parse(records.get(i+1).first);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
                        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                        if (diff == 1){
                            streak++;
                            if (streak > biggest_streak){
                                biggest_streak = streak;
                            }
                        } else {
                            streak = 1;
                        }
                    }
                    biggestStreakView.setText(Integer.toString(biggest_streak));
                    // getting all emotions of all records and grouping them
                    LinkedList<EmotionRatingPair> emotions = new LinkedList<>();
                    boolean absent;
                    for (Pair<String,ThoughtRecord> record: records){
                        for (EmotionRatingPair emotionRatingPair: record.second.getNegativeFeelingsList()){
                            String emotion = emotionRatingPair.getEmotion();
                            absent = true;
                            for (EmotionRatingPair emotionRatingPair1: emotions){
                                if (emotion.equals(emotionRatingPair1.getEmotion())){
                                    absent = false;
                                    emotionRatingPair1.setRating(emotionRatingPair1.getRating()+1);
                                }
                            }
                            if (absent){
                                emotions.add(new EmotionRatingPair(emotion,1));
                            }

                        }
                    }
                    String mostCommonEmotion = "";
                    int highestRank = 0;
                    for (EmotionRatingPair emotionRatingPair: emotions){
                        if (emotionRatingPair.getRating() > highestRank){
                            mostCommonEmotion = emotionRatingPair.getEmotion();
                            highestRank = emotionRatingPair.getRating();
                        }
                    }
                    mostOftenEmotionView.setText(mostCommonEmotion);

                    // getting all distortions of all records and grouping them
                    LinkedList<EmotionRatingPair> distortions = new LinkedList<>();
                    boolean distortionAbsent;
                    for (Pair<String,ThoughtRecord> record: records){
                        for (String distortion: record.second.getDistortions()){
                            distortionAbsent = true;
                            for (EmotionRatingPair emotionRatingPair1: distortions){
                                if (distortion.equals(emotionRatingPair1.getEmotion())){
                                    distortionAbsent = false;
                                    emotionRatingPair1.setRating(emotionRatingPair1.getRating()+1);
                                }
                            }
                            if (distortionAbsent){
                                distortions.add(new EmotionRatingPair(distortion,1));
                            }

                        }
                    }
                    String mostCommonDistortion = "";
                    int highestDistortionRank = 0;
                    for (EmotionRatingPair emotionRatingPair: distortions){
                        if (emotionRatingPair.getRating() > highestDistortionRank){
                            mostCommonDistortion = emotionRatingPair.getEmotion();
                            highestDistortionRank = emotionRatingPair.getRating();
                        }
                    }
                    mostOftenDistortionView.setText(mostCommonDistortion.replace(" ","\n"));

                    showChart(dataVals);

                }
                else{
                    lineChart.clear();
                    lineChart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(ThoughtRecordStatisticsActivity.this, "Something went wrong while contacting the database!", Toast.LENGTH_LONG).show();
            }

        });

        // generating records
        generateRecordsButnAction();
    }

    protected void generateRecordsButnAction(){
        generateRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        long day_milliseconds = 86400000;
                        DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar cal = Calendar.getInstance();
                        String date = d.format(cal.getTime());
                        final String phone = LoginActivity.getUser().getPhone();
                        // creating the reports to be added to the db
                        ArrayList<Pair<String, ThoughtRecord>> recordsToAdd = new ArrayList<>();

                        cal.setTime(new Date(cal.getTimeInMillis() - day_milliseconds));
                        date = d.format(cal.getTime());
                        String upsettingEvent = "Marriage";
                        List<EmotionRatingPair> negativeFeelingsList = new LinkedList<>();
                        negativeFeelingsList.add(new EmotionRatingPair("Sad",44));
                        String automaticThoughts = "I don't want to be here";
                        List<String> distortions = new LinkedList<>();
                        distortions.add("SHOULD STATEMENTS");
                        String rationalResponses = "I should be somewhere else";
                        List<EmotionRatingPair> updatedFeelingsList = new LinkedList<>();
                        updatedFeelingsList.add(new EmotionRatingPair("Sad",28));
                        int outcomeValue = 2;
                        recordsToAdd.add(new Pair<>(date, new ThoughtRecord(upsettingEvent, negativeFeelingsList, automaticThoughts,
                                distortions, rationalResponses, updatedFeelingsList, outcomeValue)));

                        cal.setTime(new Date(cal.getTimeInMillis() - day_milliseconds));
                        date = d.format(cal.getTime());
                        upsettingEvent = "Equipment not cooperating";
                        negativeFeelingsList = new LinkedList<>();
                        negativeFeelingsList.add(new EmotionRatingPair("Angry",90));
                        negativeFeelingsList.add(new EmotionRatingPair("Annoyed",85));
                        automaticThoughts = "I'm never gonna get this done";
                        distortions = new LinkedList<>();
                        distortions.add("SHOULD STATEMENTS");
                        distortions.add("ALL-OR-NOTHING THINKING");
                        rationalResponses = "There's probably more than one solution to this.";
                        updatedFeelingsList = new LinkedList<>();
                        updatedFeelingsList.add(new EmotionRatingPair("Angry",75));
                        updatedFeelingsList.add(new EmotionRatingPair("Annoyed",80));
                        outcomeValue = 1;
                        recordsToAdd.add(new Pair<>(date, new ThoughtRecord(upsettingEvent, negativeFeelingsList, automaticThoughts,
                                distortions, rationalResponses, updatedFeelingsList, outcomeValue)));

                        //iterating over the reports, adding those that don't overwrite existing records
                        for (Pair<String, ThoughtRecord> record: recordsToAdd){
                            if (!snapshot.child(phone).child(databaseModule).child(record.first).exists()) {
                                databaseReference.child(record.first).setValue(record.second);  //Inserting record in db
                            }
                        }
                        Toast.makeText(ThoughtRecordStatisticsActivity.this, "Thought Records Added Successfully!", Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ThoughtRecordStatisticsActivity.this, "Something went wrong when generating records!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void showChart(ArrayList<Entry> dataVals) {
        lineChart.setTouchEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        lineChart.getDescription().setEnabled(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setDrawGridLines(false);
        //Displaying mood details
        //lineChart.setOnChartValueSelectedListener(this);

        lineDataSet.setValues(dataVals);
        lineDataSet.setColor(Color.rgb(67, 91, 153));
        lineDataSet.setCircleColor(Color.rgb(67, 91, 153));
        lineDataSet.setLabel("Mood");
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setDrawCircles(true);

        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setData(lineData);

        Legend l = lineChart.getLegend();
        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(15f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);


        lineChart.invalidate();
    }
}