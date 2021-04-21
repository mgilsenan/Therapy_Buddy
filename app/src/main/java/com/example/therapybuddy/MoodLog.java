package com.example.therapybuddy;

public class MoodLog {
    int mood;
    String moodDetails;
    /*
    public MoodLog(){

    }
     */
    public MoodLog(int mood, String moodDetails) {
        this.mood = mood;
        this.moodDetails = moodDetails;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public String getMoodDetails() {
        return moodDetails;
    }

    public void setMoodDetails(String moodDetails) {
        this.moodDetails = moodDetails;
    }
}
