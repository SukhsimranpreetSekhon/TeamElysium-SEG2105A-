package com.example.vekshan.myapplication;

public class Availability {
    private String day;
    private String timeslot;

    public Availability() {

    }

    public Availability(String day, String timeslot) {
        this.day = day;
        this.timeslot = timeslot;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }
}
