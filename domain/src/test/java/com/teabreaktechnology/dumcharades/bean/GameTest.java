package com.teabreaktechnology.dumcharades.bean;

import junit.framework.TestCase;

/**
 * Created by kishorekpendyala on 1/24/16.
 */
public class GameTest extends TestCase {

    public void testToString() throws Exception {

        Game game = new Game.Builder().gameId(1).gameName("firstGame").build();

        assertEquals("{1,firstGame}", game.toString());
    }

    public void testEquals() throws Exception {

        assertEquals(true, new Game.Builder().gameId(1).gameName("firstGame").build().equals(
                new Game.Builder().gameId(1).gameName("dupGame").build()));

        assertEquals(false, new Game.Builder().gameId(2).gameName("firstGame").build().equals(
                new Game.Builder().gameId(1).gameName("dupGame").build()));
    }

    public void testHashCode() throws Exception {

        assertEquals(true, new Game.Builder().gameId(1).gameName("firstGame").build().hashCode()
                == new Game.Builder().gameId(1).gameName("firstGame").build().hashCode());

        assertEquals(false, new Game.Builder().gameId(2).gameName("firstGame").build().hashCode()
                == new Game.Builder().gameId(1).gameName("firstGame").build().hashCode());
    }

}