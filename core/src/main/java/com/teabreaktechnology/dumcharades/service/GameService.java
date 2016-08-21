package com.teabreaktechnology.dumcharades.service;

import com.teabreaktechnology.dumcharades.bean.GameInfo;
import com.teabreaktechnology.dumcharades.bean.GamePlay;
import com.teabreaktechnology.dumcharades.bean.Player;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by kishorekpendyala on 1/18/15.
 */
public interface GameService extends Serializable {

    int addTeam(String team1Name);

    int addPlayer(String playerName);

    void addPlayer(int gameId, int team1Id, int playerId);

    void prepareMovieData(InputStream in, int difficultyLevel);

    void addGamePlay(GamePlay gamePlay);

    String getGameStatus();

    int getNextPlayer(int gameId);

    int skipToNextPlayer(int gameId);

    String getPlayerName(int nextPlayerId);

    int getTeamId(int nextPlayerId);

    int getRoundNumber();

    String getTeamName(int teamId);

    List<Integer> getTeams(int gameId);

    /**
     * Movie 1 4 2 5 7
     * <p/>
     * Step 1. Pick a random number equal to size of remaining Movie list (this case its 5)
     * Step 2. Assuming random number is 1 (Is location in arrayList)
     * Step 3 .We pick movie at id 1 in the list = We pick movie 4
     * Step 4.Remove 4 and replay the steps again
     * <p/>
     * 1 2 5 7
     * <p/>
     * Next round if random number is 2 : we pick value at location 2 = 5 then remaining play list becomes 1 2 7
     *
     * @return next Movie
     */
    int getNextMovie();

    String getMovieName(int nextMovieId);

    int getCurrentGameId();

    List<String> getExistingGames();

    String toString();

    GameInfo createNewGame();

    void setCurrentGame(int selectedGame);

    int getGameId(String gameName);

    Set<Player> getPlayers(int team1Id);
}

