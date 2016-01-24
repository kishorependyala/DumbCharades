package com.teabreaktechnology.dumcharades.bean;

import junit.framework.TestCase;

/**
 * Created by kishorekpendyala on 1/24/16.
 */
public class GamePlayTest extends TestCase {

    public void testToString() throws Exception {

        GamePlay gamePlay = new GamePlay.Builder()
                .gameId(1)
                .roundId(1)
                .playerId(10)
                .movieId(20)
                .teamId(30)
                .score(1)
                .build();

        assertEquals("{{ gameId :1}{ roundId :1}{ teamId :30}{ playerId :10}{ movieId :30}{ score :1}}", gamePlay.toString());
    }

    public void testEquals() throws Exception {

        assertEquals(true, new GamePlay.Builder().gameId(1).roundId(1).build().equals(
                new GamePlay.Builder().gameId(1).roundId(1).build()));

        assertEquals(false, new GamePlay.Builder().gameId(2).roundId(1).build().equals(
                new GamePlay.Builder().gameId(1).roundId(1).build()));

        assertEquals(false, new GamePlay.Builder().gameId(1).roundId(1).build().equals(
                new GamePlay.Builder().gameId(1).roundId(2).build()));
    }

    public void testHashCode() throws Exception {

        GamePlay gameplay1 = new GamePlay.Builder().gameId(1).roundId(1).build();
        GamePlay gameplay1Clone = new GamePlay.Builder().gameId(1).roundId(1).build();

        assertEquals(gameplay1.hashCode(), gameplay1Clone.hashCode());

        assertEquals(false, new GamePlay.Builder().gameId(1).roundId(2).build().hashCode() ==
                new GamePlay.Builder().gameId(1).roundId(1).hashCode());

        assertEquals(false, new GamePlay.Builder().gameId(2).roundId(1).build().hashCode() ==
                new GamePlay.Builder().gameId(1).roundId(1).hashCode());
    }
}