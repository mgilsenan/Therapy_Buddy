package com.example.therapybuddy;

public class MoodLog {
    int mood;
    String moodDetails;
    long timeToComplete;
    public MoodLog(){

    }
    public MoodLog(int mood, String moodDetails, long timeToComplete) {
        this.mood = mood;
        this.moodDetails = moodDetails;
        this.timeToComplete = timeToComplete;
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

    public long getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(long timeToComplete) {
        this.timeToComplete = timeToComplete;
    }
}
