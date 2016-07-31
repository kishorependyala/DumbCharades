package com.teabreaktechnology.dumcharades.cache;

import com.teabreaktechnology.dumcharades.bean.GameInfo;
import com.teabreaktechnology.dumcharades.bean.GamePlay;
import com.teabreaktechnology.dumcharades.bean.Movie;
import com.teabreaktechnology.dumcharades.bean.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by kishorekpendyala on 1/31/16.
 */
public class GameCache implements Serializable {

    int gameCounter = 100;
    GameInfo currentGame;
    Map<Integer,Game> games = new HashMap<>();

    public List<String> getExistingGames(){
        List<String> gameNames = new ArrayList<>();

        for(Game game : games.values()){
            gameNames.add(game.getGameName());
        }

        if(gameNames.isEmpty()){
            createNewGame();
            return getExistingGames();
        }
        return gameNames;
    }


    public int addTeam(String teamName) {
        Game game = getGame();
        return game.addTeam(teamName);
    }

    private Game getGame() {
        Game game = games.get(getCurrentGameId());

        if(game == null){
            createNewGame();
        }
        return game;
    }


    public int getCurrentGameId(){
        if(this.currentGame == null) {
            createNewGame();
        }
        return currentGame.getGameId();

    }

    public void addMovies(List<Movie> movies) {
        Game game = getGame();
        game.addMovies(movies);

    }

    public String getPlayerName(int playerId) {
        Game game = getGame();
        return game.getPlayerName(playerId);
    }

    public String getMovieName(int movieId) {

            Game game = getGame();
            return game.getMovieName(movieId);
    }

    public void addGamePlay(GamePlay gamePlay) {
        Game game = getGame();
        game.addGamePlay(gamePlay);

    }

    public String getGameStatus() {
        Game game = getGame();
        return game.getGameStatus();
    }

    public int getNextMovie() {
        Game game = getGame();
        return game.getNextMovie();
    }

    public int getRoundNumber() {
        Game game = getGame();
        return game.getRoundNumber();
    }

    public List<Integer> getTeams(int gameId) {
        Game game = getGame();
        return game.getTeams(gameId);
    }

    public int getNextTeamToPlay(int gameId) {
        Game game = getGame();
        return game.getNextTeamToPlay(gameId);

    }

    public int getTeamId(int nextPlayerId) {
        Game game = getGame();
        return game.getTeamId(nextPlayerId);
    }

    public String getTeamName(int teamId) {
        Game game = getGame();
        return game.getTeamName(teamId);
    }

    public int addPlayer(String playerName) {
        Game game = getGame();
        return game.addPlayer(playerName);
    }

    public int getNextPlayer(int gameId) {
        Game game = getGame();
        return game.getNextPlayer(gameId);
    }

    public void addPlayer(int gameId, int team1Id, int playerId) {
        Game game = getGame();
        game.addPlayer(gameId, team1Id, playerId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Entry<Integer, Game> entry : games.entrySet()){
            sb.append(entry.getKey()).append("=>");
            sb.append(entry.getValue()).append("\n");
        }

        return sb.toString();
    }

    public GameInfo createNewGame() {
        GameInfo gameInfo = new GameInfo(gameCounter, "Game "+gameCounter);
        Game game = new Game(gameInfo);
        this.games.put(gameInfo.getGameId(), game);
        this.currentGame = gameInfo;
        gameCounter++;
        return gameInfo;
    }

    public void setCurrentGame(int selectedGame) {
        GameInfo gameInfo = this.games.get(selectedGame).getGameInfo();
        this.currentGame = gameInfo;
    }


    public int getGameId(String gameName){
        for(Entry<Integer, Game> entry : games.entrySet()){
           if(entry.getValue().getGameName().equalsIgnoreCase(gameName)){
               return entry.getKey();
           }

        }
        throw new IllegalStateException("Code should never reach this");
    }

    public Set<Player> getPlayers(int teamId) {
        Game game = getGame();
        return game.getPlayers(teamId);
    }
}
