package com.example.therapybuddy;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

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

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;


public class ThoughtRecordStatisticsActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
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
        lineChart = findViewById(R.id.lineChart);
        final String phone = LoginActivity.getUser().getPhone();
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(phone).child(databaseModule);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //creating the graphs
                ArrayList<Entry> dataVals = new ArrayList<Entry>();
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot myDataSnapshot: dataSnapshot.getChildren()){
                        MoodLog moodLog = myDataSnapshot.getValue(MoodLog.class);
                        String[] dataArray = myDataSnapshot.getKey().split("-");
                        dataVals.add(new Entry(Integer.parseInt(dataArray[2]), moodLog.getMood()));
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