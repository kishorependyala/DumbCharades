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
    private String director;
    private int level;
    private String genre;

    public Movie(Builder builder) {
        this.movieId = builder.movieId;
        this.movieName = builder.movieName;
        this.language = builder.language;
        this.year = builder.year;
        this.cast = builder.cast;
        this.director = builder.director;
        this.level = builder.level;
        this.genre = builder.genre;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append(movieId).append(",")
                .append(movieName).append(",")
                .append(language).append(",")
                .append(year).append(",")
                .append(genre).append(",")
                .append(director).append(",")
                .append(level).append(",")
                .append(cast).append("}");

        return sb.toString();
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public GameCache.Language getLanguage() {
        return language;
    }

    public int getYear() {
        return year;
    }

    public String getCast() {
        return cast;
    }

    public String getDirector() {
        return director;
    }

    public int getLevel() {
        return level;
    }

    public String getGenre() {
        return genre;
    }

    public static class Builder {

        private int movieId;
        private String movieName;
        private GameCache.Language language;
        private int year;
        private String cast;
        private String director;
        private int level;
        private String genre;


        public Builder movieId(int movieId) {
            this.movieId = movieId;
            return this;
        }

        public Builder language(GameCache.Language language) {
            this.language = language;
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

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder director(String director) {
            this.director = director;
            return this;
        }

        public Builder level(int level) {
            this.level = level;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}
