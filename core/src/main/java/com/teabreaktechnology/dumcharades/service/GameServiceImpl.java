package com.teabreaktechnology.dumcharades.service;

import com.teabreaktechnology.dumcharades.bean.GamePlay;
import com.teabreaktechnology.dumcharades.bean.Movie;
import com.teabreaktechnology.dumcharades.cache.GameCache;
import com.teabreaktechnology.dumcharades.util.CommonConstants;
import com.teabreaktechnology.dumcharades.util.StringUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kishorekpendyala on 1/31/16.
 */
public class GameServiceImpl implements GameService {

    private static final long serialVersionUID = 1;

    private static GameService gameService = null;
    private GameCache gameCache = new GameCache();

    public static GameService getInstance(boolean initialize) {
        if (gameService == null || initialize) {
            gameService = new GameServiceImpl();
        }
        return gameService;
    }


    /**
     * 1. Extract movie names from the file
     * 2. Set int {@link GameCache}
     *
     * @param in
     * @param difficultyLevel
     */

    @Override
    public void prepareMovieData(InputStream in, int difficultyLevel) {
        List<Movie> movies = readMovieList(in, difficultyLevel);
        gameCache.addMovies(movies);
    }

    /*
        Reads input csv movie file and creates a map of movie names
        Language,Year,Title,Genre,Director,Cast,Level
     */
    public List<Movie> readMovieList(InputStream in, int gameLevel) {
        List<Movie> movies = new ArrayList<>();

        AtomicInteger movieCounter = new AtomicInteger(1);
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {


            br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
                if (line == null) {
                    break;
                }
                if (line.startsWith("#") || line.startsWith("Y") || line.startsWith("y")) {
                    System.out.println("line comment");
                    continue;
                }
                // use comma as separator
                String[] tokens = StringUtil.split(line);
                //line.split(cvsSplitBy);
                if (tokens.length <= 4) {
                    System.out.println("Breaking at line " + line);
                    break;
                }
                String levelString = tokens[5];
                int level = 1; //Setting default level to easy
                if (levelString != null && levelString.length() != 0) {
                    level = new Integer(levelString);
                }

                String movieName = tokens[1];
                if (level != gameLevel) {
                    System.out.println("Requested game level " + gameLevel + " level " + level + " ignored movie " + movieName);
                    continue;
                }

                int year = new Integer(tokens[0]).intValue();

                String genre = tokens[2];
                String director = tokens[3];
                String cast = tokens[4];

                Integer movieId = movieCounter.getAndIncrement();

                Movie movie = new Movie.Builder()
                        .movieId(movieId)
                        .year(year)
                        .movieName(movieName)
                                //.language(language)
                        .genre(genre)
                        .director(director)
                        .cast(cast)
                        .level(level)
                        .build();

                movies.add(movie);
                System.out.println(" " + movie);

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Loaded " + movies.size() + " in ");
        return movies;
    }


    public String toString() {
        return gameCache.toString();
    }


    public int addTeam(String teamName) {
        return gameCache.addTeam(teamName);
    }


    public int addPlayer(String playerName) {
        if (playerName == null || playerName.isEmpty()) {
            return CommonConstants.EMPTY_PLAYERNAME;
        }
        return gameCache.addPlayer(playerName);
    }

    @Override
    public void addPlayer(int gameId, int team1Id, int playerId) {
        gameCache.addPlayer(gameId, team1Id, playerId);
    }

    /**
     * 1. First pick next team to play
     * 2. Loop through eac
     *
     * @param gameId
     * @return
     */
    public int getNextPlayer(int gameId) {
        return gameCache.getNextPlayer(gameId);
    }


    /**
     * @param gameId
     * @return next team to play
     */
    public int getNextTeamToPlay(int gameId) {

        return gameCache.getNextTeamToPlay(gameId);
    }

    public String getPlayerName(int playerId) {
        return gameCache.getPlayerName(playerId);
    }

    public int getTeamId(int nextPlayerId) {
        return gameCache.getTeamId(nextPlayerId);
    }

    public String getTeamName(int teamId) {
        return gameCache.getTeamName(teamId);
    }

    @Override
    public List<Integer> getTeams(int gameId) {
        return gameCache.getTeams(gameId);
    }

    public int getRoundNumber() {
        return gameCache.getRoundNumber();
    }


    public int getNextMovie() {
        return gameCache.getNextMovie();

    }

    public String getMovieName(int movieId) {
        return gameCache.getMovieName(movieId);
    }

    /**
     * Team1 - GamePlay1, GamePlay2
     * Team2 - GamePlay1, GamePlay2 so on
     */
    public void addGamePlay(GamePlay gamePlay) {
        gameCache.addGamePlay(gamePlay);
    }

    public String getGameStatus() {
        return gameCache.getGameStatus();
    }

}
