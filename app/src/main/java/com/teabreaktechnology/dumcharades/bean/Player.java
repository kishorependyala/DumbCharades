package com.teabreaktechnology.dumcharades.bean;

/**
 * Created by kishorekpendyala on 1/18/15.
 */
public class Player {
    private int playerId;
    private String playerName;


    public Player(Builder builder) {
        this.playerId = builder.playerId;
        this.playerName = builder.playerName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(playerId).append(",").append(playerName).append("}");
        return sb.toString();
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public static class Builder {

        private int playerId;
        private String playerName;

        public Builder playerId(int playerId) {
            this.playerId = playerId;
            return this;
        }

        public Builder playerName(String playerName) {
            this.playerName = playerName;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }
}
