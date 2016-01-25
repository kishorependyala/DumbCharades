package com.teabreaktechnology.dumcharades.bean;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Created by nikil on 25-Jan-16.
 */
public class MovieTest extends TestCase {

    @Test
    public void testEquals() throws Exception {

        //Postive test case
        assertEquals(true, new Movie.Builder().movieId(10).build().equals(
                new Movie.Builder().movieId(10).build()));
        //negative test case
        assertEquals(false, new Movie.Builder().movieId(10).build().equals(
                new Movie.Builder().movieId(100).build()));


    }

    @Test
    public void testHashCode() throws Exception {
        //Postive test case
        Movie movie = new Movie.Builder().movieId(10).build();
        assertEquals(movie.hashCode(), movie.hashCode());
        Movie movie2 = new Movie.Builder().movieId(10).build();
        assertEquals(movie2.hashCode(), movie2.hashCode());

        //Negative test case
        Movie movie3 = new Movie.Builder().movieId(10).build();
        assertEquals(movie3.hashCode(), movie3.hashCode());

    }
}
