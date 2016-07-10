package com.teabreaktechnology.util;

import com.teabreaktechnology.dumcharades.bean.Movie;
import com.teabreaktechnology.dumcharades.bean.Player;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by kishorekpendyala on 2/21/16.
 */
public class JavaSerializerTest {

    @Test
    public void testSerialize() throws Exception {

        Player player = new Player.Builder().playerId(1).playerName("Avinash").build();
        SerializerInterface javaSerializer = new JavaSerializer();
        String filePath = javaSerializer.serialize(player);

        System.out.println("Before " + player);
        Player deserializedPlayer = javaSerializer.deserialize(filePath);
        System.out.println("After " + deserializedPlayer);

        assertTrue(player != deserializedPlayer); // checks the de-serialized object has different reference
        assertEquals(player, deserializedPlayer);
        assertEquals(player.getPlayerId(), deserializedPlayer.getPlayerId());
        assertEquals(player.getPlayerName(), deserializedPlayer.getPlayerName());

    }

    @Test
    public void testSerializeMovieTest() throws Exception {
        Movie movie = new Movie.Builder().movieId(1).movieName("Dhoom 3").build();

        SerializerInterface javaSerializer = new JavaSerializer();
        String filePath = javaSerializer.serialize(movie);

        System.out.println("Before " + movie);
        Movie deserializedMovie = javaSerializer.deserialize(filePath);
        System.out.println("After " + deserializedMovie);

        assertTrue(movie != deserializedMovie);
        assertEquals(movie, deserializedMovie);
        assertEquals(movie.getMovieId(), deserializedMovie.getMovieId());
        assertEquals(movie.getMovieName(), deserializedMovie.getMovieName());
    }


}