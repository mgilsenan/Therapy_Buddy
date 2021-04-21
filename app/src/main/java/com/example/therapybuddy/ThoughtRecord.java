package com.example.therapybuddy;

import android.util.Pair;
import java.util.LinkedList;
public class ThoughtRecord {
    private String upsettingEvent;
    private LinkedList<Pair<String,Integer>> negativeFeelingsList;
    private String automaticThoughts;
    private LinkedList<Boolean> distortions;
    private String rationalResponses;
    private LinkedList<Pair<String,Integer>> updatedFeelingsList;
    private int outcomeValue;

    //to use for debugging
    public ThoughtRecord(){
        upsettingEvent = "empty";
        negativeFeelingsList = new LinkedList<Pair<String,Integer>>();
        negativeFeelingsList.add(new Pair<String,Integer>("empty",0));
        automaticThoughts = "empty";
        distortions = new LinkedList<Boolean>();
        distortions.add(false);
        rationalResponses = "empty";
        updatedFeelingsList = new LinkedList<Pair<String,Integer>>();
        updatedFeelingsList.add(new Pair<String,Integer>("empty",10));
        outcomeValue = 20;
    }


    public ThoughtRecord(String upsettingEventD, LinkedList<Pair<String,Integer>> negativeFeelingsListD, String automaticThoughtsD,
                         LinkedList<Boolean> distortionsD, String rationalResponsesD, LinkedList<Pair<String,Integer>> updatedFeelingsListD, int outcomeValueD){
        upsettingEvent = upsettingEventD;
        negativeFeelingsList = negativeFeelingsListD;
        distortions = distortionsD;
        automaticThoughts = automaticThoughtsD;
        outcomeValue = outcomeValueD;
    }

    public String getUpsettingEvent() {
        return upsettingEvent;
    }

    public void setUpsettingEvent(String upsettingEvent) {
        this.upsettingEvent = upsettingEvent;
    }

    public LinkedList<Pair<String, Integer>> getNegativeFeelingsList() {
        return negativeFeelingsList;
    }

    public void setNegativeFeelingsList(LinkedList<Pair<String, Integer>> negativeFeelingsList) {
        this.negativeFeelingsList = negativeFeelingsList;
    }

    public LinkedList<Boolean> getDistortions() {
        return distortions;
    }

    public void setDistortions(LinkedList<Boolean> distortions) {
        this.distortions = distortions;
    }

    public int getOutcomeValue() {
        return outcomeValue;
    }

    public String getAutomaticThoughts() {
        return automaticThoughts;
    }

    public void setAutomaticThoughts(String automaticThoughts) {
        this.automaticThoughts = automaticThoughts;
    }

    public void setOutcomeValue(int outcomeValue) {
        this.outcomeValue = outcomeValue;
    }
}
