package com.example.therapybuddy.dataClasses;

import android.util.Pair;
import java.util.LinkedList;
import java.util.List;

public class ThoughtRecord {
    private String upsettingEvent;
    private List<EmotionRatingPair> negativeFeelingsList;
    private String automaticThoughts;
    private List<String> distortions;
    private String rationalResponses;
    private List<EmotionRatingPair> updatedFeelingsList;
    private int outcomeValue;

    //to use for debugging
    public ThoughtRecord(){
        upsettingEvent = "empty";
        negativeFeelingsList = new LinkedList<EmotionRatingPair>();
        negativeFeelingsList.add(new EmotionRatingPair("empty",0));
        automaticThoughts = "empty";
        distortions = new LinkedList<String>();
        distortions.add("distortion");
        rationalResponses = "empty";
        updatedFeelingsList = new LinkedList<EmotionRatingPair>();
        updatedFeelingsList.add(new EmotionRatingPair("empty",10));
        outcomeValue = 20;
    }


    public ThoughtRecord(String upsettingEvent, List<EmotionRatingPair> negativeFeelingsList, String automaticThoughts,
                         List<String> distortions, String rationalResponses, List<EmotionRatingPair> updatedFeelingsList, int outcomeValue){
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

    public List<EmotionRatingPair> getNegativeFeelingsList() {
        return negativeFeelingsList;
    }

    public void setNegativeFeelingsList(List<EmotionRatingPair> negativeFeelingsList) {
        this.negativeFeelingsList = negativeFeelingsList;
    }

    public String getAutomaticThoughts() {
        return automaticThoughts;
    }

    public void setAutomaticThoughts(String automaticThoughts) {
        this.automaticThoughts = automaticThoughts;
    }

    public List<String> getDistortions() {
        return distortions;
    }

    public void setDistortions(List<String> distortions) {
        this.distortions = distortions;
    }

    public String getRationalResponses() {
        return rationalResponses;
    }

    public void setRationalResponses(String rationalResponses) {
        this.rationalResponses = rationalResponses;
    }

    public List<EmotionRatingPair> getUpdatedFeelingsList() {
        return updatedFeelingsList;
    }

    public void setUpdatedFeelingsList(List<EmotionRatingPair> updatedFeelingsList) {
        this.updatedFeelingsList = updatedFeelingsList;
    }

    public int getOutcomeValue() {
        return outcomeValue;
    }

    public void setOutcomeValue(int outcomeValue) {
        this.outcomeValue = outcomeValue;
    }
}
