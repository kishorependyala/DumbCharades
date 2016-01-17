package com.teabreaktechnology.dumcharades.bean;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by kishorekpendyala on 1/17/16.
 */
public class PlayerTest {

    @Test
    public void testEquals() throws Exception {

        //Postive test case
        assertEquals(true, new Player.Builder().playerId(100).build().equals(
                new Player.Builder().playerId(100).build()));
        assertEquals(true, new Player.Builder().playerId(100).playerName("Raheel").build().equals(
                new Player.Builder().playerId(100).playerName("Karthik").build()));

        //Negative test case
        assertEquals(false, new Player.Builder().playerId(200).build().equals(
                new Player.Builder().playerId(100).build()));

    }

    @Test
    public void testHashCode() throws Exception {
        //Postive test case
        Player player = new Player.Builder().playerId(100).build();
        assertEquals(player.hashCode(), player.hashCode());
        Player player2 = new Player.Builder().playerId(100).build();
        assertEquals(player.hashCode(), player2.hashCode());

        //Negative test case
        Player player3 = new Player.Builder().playerId(200).build();
        assertNotEquals(player.hashCode(), player3.hashCode());

    }

    @Test
    public void testToString() throws Exception {

        Player player = new Player.Builder().playerName("Sridhar").playerId(1).build();

        assertEquals("{1,Sridhar}", player.toString());

    }
}