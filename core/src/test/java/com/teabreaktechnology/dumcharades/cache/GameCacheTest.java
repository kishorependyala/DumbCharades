package com.teabreaktechnology.dumcharades.cache;

import com.teabreaktechnology.util.JavaSerializer;
import com.teabreaktechnology.util.SerializerInterface;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kishorekpendyala on 2/14/16.
 */
public class GameCacheTest {

    @Test
    public void testAddTeam() throws Exception {

        GameCache gameCache = new GameCache();
        int teamId = gameCache.addTeam("Team 1");

        assertEquals(teamId, gameCache.addTeam("Team 1"));
        assertEquals(2, gameCache.addTeam("Team 2"));
    }


    @Test
    public void testSerialization() throws Exception {
        GameCache gameCache = new GameCache();
        int teamId = gameCache.addTeam("Team 1");

        SerializerInterface javaSerializer = new JavaSerializer();

        String filePath = javaSerializer.serialize(gameCache);
        GameCache deserializedFile = javaSerializer.deserialize(filePath);

        assertEquals(gameCache.getGameStatus(), deserializedFile.getGameStatus());
    }

}