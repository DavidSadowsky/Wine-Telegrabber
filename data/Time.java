package org.davidsadowsky.zammywinegrabber.data;

import java.time.LocalTime;

public enum Time {
    ONEHOUR("1 hour", 3600),
    TWOHOURS("2 hours", 7200),
    THREEHOURS("3 hours", 10800),
    FOURHOURS("4 hours", 14400),
    FIVEHOURS("5 hours", 18000),
    SIXHOURS("6 hours", 21600),
    SEVENHOURS("7 hours", 25200),
    EIGHTHOURS("8 hours", 28800),
    NOLIMIT("Infinite", -1);

    private final String time;
    private final long seconds;
    private final LocalTime startTime;

    private Time(String time, long seconds) {
        this.time = time;
        this.seconds = seconds;
        this.startTime = LocalTime.now();
    }

    public String getTime() { return time; }
    public long getSeconds() { return seconds; }

    public LocalTime getStartTime() { return startTime; }
}
