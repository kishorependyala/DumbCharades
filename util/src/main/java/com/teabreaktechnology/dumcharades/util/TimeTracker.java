package com.teabreaktechnology.dumcharades.util;

/**
 * Created by kishorekpendyala on 2/21/16.
 */
public class TimeTracker {

    /* Adding private construtor as this is a utility class */
    private TimeTracker() {

    }

    public static final long getTimeStamp() {
        return System.currentTimeMillis();
    }
}
