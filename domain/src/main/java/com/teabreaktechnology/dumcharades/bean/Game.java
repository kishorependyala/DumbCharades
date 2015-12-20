package com.teabreaktechnology.dumcharades.bean;

import java.io.Serializable;

/**
 * Created by kishorekpendyala on 1/18/15.
 */
public class Game implements Serializable {

    private static final long serialVersionUID = 1;

    int gameId;
    String gameName;

    public Game(Builder builder) {
        this.gameId = builder.gameId;
        this.gameName = builder.gameName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(gameId).append(",").append(gameName).append("}");
        return sb.toString();
    }

    public static class Builder {

        private int gameId;
        private String gameName;

        public Builder gameId(int gameId) {
            this.gameId = gameId;
            return this;
        }

        public Builder gameName(String gameName) {
            this.gameName = gameName;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
}
