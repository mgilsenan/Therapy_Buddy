package com.example.therapybuddy;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;


public class ThoughtRecordStatisticsActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    Button generateRecordBtn;
    LineChart lineChart;
    LineDataSet lineDataSet = new LineDataSet(null , null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    String databaseModule = "thoughtRecord";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_record_statistics);
        setUp();
    }
    protected void setUp(){
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

                    // Biggest streak
                    int biggest_streak = 0;
                    int streak = 0;
                    for (int i = 0; i<records.size()-1; i++){
                        //todo properly compare dates (I only check days)
                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1= new SimpleDateFormat("yyyy/MM/dd").parse(records.get(i).first);
                            date2= new SimpleDateFormat("yyyy/MM/dd").parse(records.get(i+1).first);
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
                            streak = 0;
                        }
                    }


                    showChart(dataVals);

                }
                else{
                    lineChart.clear();
                    lineChart.invalidate();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(ThoughtRecordStatisticsActivity.this, "Something went wrong while contacting the database!", Toast.LENGTH_LONG).show();
            }

        });

        // generating records
        
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