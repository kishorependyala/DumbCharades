package com.teabreaktechnology.dumcharades.bean;

/**
 * Created by kishorekpendyala on 7/24/16.
 */
public class GameInfo implements DCDomainObject{

    int gameId;
    String gameName;

    public GameInfo(int gameId, String gameName){
        this.gameId = gameId;
        this.gameName = gameName;
    }

    public int getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameInfo gameInfo = (GameInfo) o;

        return gameId == gameInfo.gameId;

    }

    @Override
    public int hashCode() {
        return gameId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(gameId).append(",").append(gameName).append("}");
        return sb.toString();

    }
}
