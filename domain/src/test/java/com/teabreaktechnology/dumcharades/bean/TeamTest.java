package com.teabreaktechnology.dumcharades.bean;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Created by nikil on 25-Jan-16.
 */
public class TeamTest extends TestCase {

    @Test
    public void testEquals() throws Exception {

        //Postive test case
        assertEquals(true, new Team.Builder().teamId(10).build().equals(
                new Team.Builder().teamId(10).build()));
        //negative test case
        assertEquals(false, new Team.Builder().teamId(10).build().equals(
                new Team.Builder().teamId(100).build()));

    }

    @Test
    public void testHashCode() throws Exception {
        //Postive test case
        Team team = new Team.Builder().teamId(10).build();
        assertEquals(team.hashCode(), team.hashCode());
        Team team2 = new Team.Builder().teamId(10).build();
        assertEquals(team2.hashCode(), team2.hashCode());

        //Negative test case
        Team team3 = new Team.Builder().teamId(20).build();
        assertEquals(team3.hashCode(), team3.hashCode());

    }
}