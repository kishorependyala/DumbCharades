package com.teabreaktechnology.dumcharades.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by kishorekpendyala on 2/21/16.
 */
public class TimeTrackerTest {


    @Test
    public void testGetTimeStamp() throws Exception {
        long timeStamp = TimeTracker.getTimeStamp();
        Thread.sleep(1);
        assertTrue(TimeTracker.getTimeStamp() > timeStamp);
    }
}