package com.william.resigncountdown.model;

/**
 * Created by willi on 21-Apr-16.
 */
public class DateDiff {

    private long weeks;
    private long days;
    private long hours;
    private long minutes;
    private long seconds;

    private long secondsInMilli = 1000;
    private long minutesInMilli = secondsInMilli * 60;
    private long hoursInMilli = minutesInMilli * 60;
    private long daysInMilli = hoursInMilli * 24;
    private long weeksInMilli = daysInMilli * 7;

    public DateDiff(long duration) {
        this.weeks = duration / weeksInMilli;
        duration %= weeksInMilli;

        this.days = duration / daysInMilli;
        duration %= daysInMilli;

        this.hours = duration / hoursInMilli;
        duration %= hoursInMilli;

        this.minutes = duration / minutesInMilli;
        duration %= minutesInMilli;

        this.seconds = duration / secondsInMilli;
        duration %= secondsInMilli;
    }

    public long getDays() {
        return days;
    }

    public long getHours() {
        return hours;
    }

    public long getMinutes() {
        return minutes;
    }

    public long getSeconds() {
        return seconds;
    }

    public long getWeeks() {
        return weeks;
    }
}
