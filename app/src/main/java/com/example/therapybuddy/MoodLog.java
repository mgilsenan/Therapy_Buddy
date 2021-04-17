package com.example.therapybuddy;

import java.util.Date;

public class MoodLog {
    String date;
    String mood;
    String moodDetails;
    /*
    public MoodLog(){

    }
     */
    public MoodLog(String date,String mood, String moodDetails) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getMoodDetails() {
        return moodDetails;
    }

    public void setMoodDetails(String moodDetails) {
        this.moodDetails = moodDetails;
    }
}
