package com.teabreaktechnology.dumcharades.cache;

import junit.framework.TestCase;

/**
 * Created by kishorekpendyala on 1/24/16.
 */
public class GameTest extends TestCase {

    public void testToString() throws Exception {

        Game game = new Game(1);

        assertEquals("{playerMap:{}}{teamMap:{}}{movieMap:{}}{gameTeamsMap:{}}{teamPlayersMap:{}}{gamePlayMap:{}}", game.toString());
    }


    public void testHashCode() throws Exception {

        assertEquals(true, new Game(1).hashCode()
                == new Game(1).hashCode());

    }

}