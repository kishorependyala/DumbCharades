package com.teabreaktechnology.dumcharades.bean;

import com.teabreaktechnology.dumcharades.cache.GameCache;

/**
 * Created by kishorekpendyala on 1/18/15.
 */
public class Movie {

    private int movieId;
    private String movieName;
    private GameCache.Language language;
    private int year;
    private String cast;

    public Movie(Builder builder) {
        this.movieId = builder.movieId;
        this.movieName = builder.movieName;
        this.language = builder.language;
        this.year = builder.year;
        this.cast = builder.cast;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append(movieId).append(",")
                .append(movieName).append(",")
                .append(language).append(",")
                .append(year).append(",")
                .append(cast).append("}");

        return sb.toString();
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public static class Builder {

        private int movieId;
        private String movieName;
        private GameCache.Language language;
        private int year;
        private String cast;


        public Builder movieId(int movieId) {
            this.movieId = movieId;
            return this;
        }

        public Builder language(GameCache.Language language) {
            this.movieId = movieId;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder cast(String cast) {
            this.cast = cast;
            return this;
        }


        public Builder movieName(String movieName) {
            this.movieName = movieName;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}
