package com.example.therapybuddy;

import android.util.Pair;
import java.util.LinkedList;
public class ThoughtRecord {
    private String upsettingEvent;
    private LinkedList<Pair<String,Integer>> negativeFeelingsList;
    private String automaticThoughts;
    private LinkedList<String> distortions;
    private String rationalResponses;
    private LinkedList<Pair<String,Integer>> updatedFeelingsList;
    private int outcomeValue;

    //to use for debugging
    public ThoughtRecord(){
        upsettingEvent = "empty";
        negativeFeelingsList = new LinkedList<Pair<String,Integer>>();
        negativeFeelingsList.add(new Pair<String,Integer>("empty",0));
        automaticThoughts = "empty";
        distortions = new LinkedList<String>();
        distortions.add("distortion");
        rationalResponses = "empty";
        updatedFeelingsList = new LinkedList<Pair<String,Integer>>();
        updatedFeelingsList.add(new Pair<String,Integer>("empty",10));
        outcomeValue = 20;
    }


    public ThoughtRecord(String upsettingEvent, LinkedList<Pair<String,Integer>> negativeFeelingsList, String automaticThoughts,
                         LinkedList<String> distortions, String rationalResponses, LinkedList<Pair<String,Integer>> updatedFeelingsList, int outcomeValue){
        this.upsettingEvent = upsettingEvent;
        this.negativeFeelingsList = negativeFeelingsList;
        this.distortions = distortions;
        this.automaticThoughts = automaticThoughts;
        this.outcomeValue = outcomeValue;
        this.rationalResponses = rationalResponses;
        this.updatedFeelingsList = updatedFeelingsList;
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

    public LinkedList<String> getDistortions() {
        return distortions;
    }

    public void setDistortions(LinkedList<String> distortions) {
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
