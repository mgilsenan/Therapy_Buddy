package com.example.therapybuddy.dataClasses;

public class EmotionRatingPair {
    private String emotion;
    private int rating;

    public EmotionRatingPair() {
        this.emotion = "emotion";
        this.rating = 0;
    }

    public EmotionRatingPair(String emotion, int rating) {
        this.emotion = emotion;
        this.rating = rating;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
