package com.example.therapybuddy;

import java.util.Date;

public class MoodLog {
    String mood;
    String moodDetails;
    /*
    public MoodLog(){

    }
     */
    public MoodLog(String mood, String moodDetails) {
        this.mood = mood;
        this.moodDetails = moodDetails;
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
