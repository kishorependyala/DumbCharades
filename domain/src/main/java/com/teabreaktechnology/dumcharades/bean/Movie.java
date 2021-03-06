package com.teabreaktechnology.dumcharades.bean;


import java.io.Serializable;

/**
 * Created by kishorekpendyala on 1/18/15.
 */
public class Movie implements Serializable {

    private static final long serialVersionUID = 1;

    private int movieId;
    private String movieName;
    private String language;
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

    public String getLanguage() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return movieId == movie.movieId;

    }

    @Override
    public int hashCode() {
        return movieId;
    }

    public static class Builder {

        private int movieId;
        private String movieName;
        private String language;
        private int year;
        private String cast;
        private String director;
        private int level;
        private String genre;


        public Builder movieId(int movieId) {
            this.movieId = movieId;
            return this;
        }

        public Builder language(String language) {
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
