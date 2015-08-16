package com.teabreaktechnology.dumcharades.cache;

import com.teabreaktechnology.dumcharades.bean.Game;
import com.teabreaktechnology.dumcharades.bean.GamePlay;
import com.teabreaktechnology.dumcharades.bean.Movie;
import com.teabreaktechnology.dumcharades.bean.Player;
import com.teabreaktechnology.dumcharades.bean.Team;
import com.teabreaktechnology.util.StringUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kishorekpendyala on 1/18/15.
 */
public class GameCache {

    private static GameCache gameCache = null;
    private static AtomicInteger movieCounter = new AtomicInteger(1);
    private Map<Integer, Player> playerMap = new HashMap<Integer, Player>();
    private Map<Integer, Team> teamMap = new HashMap<Integer, Team>();
    private Map<Integer, Movie> movieMap = new HashMap<Integer, Movie>();
    private Map<Integer, Game> gameMap = new HashMap<Integer, Game>();
    private Map<Integer, List<GamePlay>> gamePlayMap = new HashMap<Integer, List<GamePlay>>();
    private Map<Integer, Set<Team>> gameTeamsMap = new HashMap<Integer, Set<Team>>();
    private Map<Integer, Set<Player>> teamPlayersMap = new HashMap<Integer, Set<Player>>();
    private AtomicInteger playerIdCounter = new AtomicInteger(1);
    private AtomicInteger teamIdCounter = new AtomicInteger(1);
    private int lastPlayedTeamId = 0;
    private Map<Player, Integer> playCount = new HashMap<Player, Integer>();
    private int roundNumber = 0;
    private List<Integer> moviesPlayList = new ArrayList<Integer>();

    private GameCache() {

    }

    public static final GameCache getInstance(boolean initialize) {
        if (gameCache == null || initialize) {
            gameCache = new GameCache();
        }
        return gameCache;
    }


    public static void test(List<Integer> moviesPlayList) {
        while (true) {


            if (moviesPlayList.isEmpty()) {
                Integer[] arr = {4, 2, 7, 1, 6};
                List<Integer> playList = Arrays.asList(arr);
                moviesPlayList.addAll(playList);
            }

            int remainingMovies = moviesPlayList.size();
            int nextMovieToPlay = new Random().nextInt(remainingMovies);
            moviesPlayList.remove(nextMovieToPlay);
            System.out.println("moviesPlayList = " + moviesPlayList);
            System.out.println(nextMovieToPlay);
        }
    }

    /*
        Reads input csv movie file and creates a map of movie names
        Language,Year,Title,Genre,Director,Cast,Level
     */
    public void run(InputStream in, int gameLevel) {

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {


            br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
                if (line == null) {
                    break;
                }
                if (line.startsWith("#")) {
                    System.out.println("line comment");
                    continue;
                }
                // use comma as separator
                String[] tokens = StringUtil.split(line);
                //line.split(cvsSplitBy);
                if (tokens.length < 4) {
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

                //Language language = Language.getLanguage(tokens[0]);
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
                movieMap.put(movieId, movie);
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

        System.out.println("Done");
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{playerMap:").append(playerMap).append("}");
        sb.append("{teamMap:").append(teamMap).append("}");
        sb.append("{movieMap:").append(movieMap).append("}");
        sb.append("{gameMap:").append(gameMap).append("}");
        sb.append("{gameTeamsMap:").append(gameTeamsMap).append("}");
        sb.append("{teamPlayersMap:").append(teamPlayersMap).append("}");
        sb.append("{gamePlayMap:").append(gamePlayMap).append("}");
        return sb.toString();
    }

    public void addPlayer(int gameId, int teamId, int playerId) {
        Player player = playerMap.get(playerId);
        Set<Player> players = teamPlayersMap.get(teamId);
        if (players == null) {
            players = new HashSet<Player>();
            teamPlayersMap.put(teamId, players);

        }
        players.add(player);
        Team team = teamMap.get(teamId);
        Set<Team> teams = gameTeamsMap.get(gameId);
        if (teams == null) {
            teams = new HashSet<Team>();
            gameTeamsMap.put(gameId, teams);
        }
        teams.add(team);
    }

    public int addTeam(String teamName) {
        if (teamName == null || teamName.isEmpty()) {
            throw new IllegalArgumentException("Invalid team name");
        }
        for (Map.Entry<Integer, Team> entry : teamMap.entrySet()) {
            Integer existingTeamId = entry.getKey();
            String existingTeamName = entry.getValue().getTeamName();
            if (teamName.equalsIgnoreCase(existingTeamName)) {
                return existingTeamId;
            }
        }

        int teamId = teamIdCounter.getAndIncrement();
        Team team = new Team.Builder().teamId(teamId).teamName(teamName).build();
        teamMap.put(teamId, team);
        return teamId;
    }

    public int addPlayer(String playerName) {
        if (playerName == null || playerName.isEmpty()) {
            throw new IllegalArgumentException("Invalid player name");
        }
        for (Map.Entry<Integer, Player> entry : playerMap.entrySet()) {
            Integer existingPlayerId = entry.getKey();
            String existingPlayerName = entry.getValue().getPlayerName();
            if (playerName.equalsIgnoreCase(existingPlayerName)) {
                return existingPlayerId;
            }
        }
        int playerId = playerIdCounter.getAndIncrement();
        Player player = new Player.Builder().playerId(playerId).playerName(playerName).build();
        playerMap.put(playerId, player);
        return playerId;
    }

    /**
     * 1. First pick next team to play
     * 2. Loop through eac
     *
     * @param gameId
     * @return
     */

    public int getNextPlayer(int gameId) {
        int nextTeamToPlay = getNextTeamToPlay(gameId);

        Set<Player> players = teamPlayersMap.get(nextTeamToPlay);

        int numberOfRounds = 0;
        int minNumberOfRounds = 0;
        for (Player player : players) {
            Integer plays = playCount.get(player);
            if (plays == null) {
                playCount.put(player, 0);
                plays = 0;
            }
            if (plays > numberOfRounds || numberOfRounds == 0) {
                numberOfRounds = plays;
            }
            if (plays < minNumberOfRounds || minNumberOfRounds == 0) {
                minNumberOfRounds = plays;
            }
        }

        System.out.println("For game " + gameId + " nextTeamToPlay " + nextTeamToPlay + " numberOfRounds " + numberOfRounds + " minNumberOfRounds " + minNumberOfRounds);

        for (Player player : players) {
            Integer plays = playCount.get(player);
            plays = plays == null ? 0 : plays;
            if (plays < numberOfRounds && minNumberOfRounds != numberOfRounds) {
                plays = plays + 1;
                roundNumber = plays;
                playCount.put(player, plays);
                System.out.println("For game " + gameId + " nextTeamToPlay " + nextTeamToPlay + " numberOfRounds " + numberOfRounds + " playCount " + playCount);
                return player.getPlayerId();
            }
            if (minNumberOfRounds == numberOfRounds) {
                plays = plays + 1;
                playCount.put(player, plays);
                roundNumber = plays;
                System.out.println("For game " + gameId + " nextTeamToPlay " + nextTeamToPlay + " numberOfRounds " + numberOfRounds + " playCount " + playCount);
                return player.getPlayerId();
            }
        }
        throw new IllegalStateException("Code should never come here");
    }

    /**
     * @param gameId
     * @return next team to play
     */
    public int getNextTeamToPlay(int gameId) {

        Set<Team> teams = gameTeamsMap.get(gameId);
        for (Team team : teams) {
            if (team.getTeamId() != lastPlayedTeamId) {
                lastPlayedTeamId = team.getTeamId();
                return lastPlayedTeamId;
            }
        }
        throw new IllegalStateException("Cannot reach this block");
    }

    public String getPlayerName(int playerId) {
        Player player = playerMap.get(playerId);
        return player.getPlayerName();
    }

    public int getTeamId(int nextPlayerId) {

        for (Map.Entry<Integer, Set<Player>> entry : teamPlayersMap.entrySet()) {
            Integer teamId = entry.getKey();
            for (Player playerId : entry.getValue()) {
                if (playerId.getPlayerId() == nextPlayerId) {
                    return teamId;
                }
            }

        }
        throw new IllegalStateException("Cannot reach this block");
    }

    public String getTeamName(int teamId) {
        Team team = teamMap.get(teamId);
        return team.getTeamName();
    }

    public int getRoundNumber() {
        return roundNumber;
    }

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
    public Integer getNextMovie() {

        if (moviesPlayList.isEmpty()) {
            moviesPlayList.addAll(movieMap.keySet());
        }

        int remainingMovies = moviesPlayList.size();
        int nextMovieToPlayId = new Random().nextInt(remainingMovies);
        int nextMovieIndex = moviesPlayList.get(nextMovieToPlayId);
        moviesPlayList.remove(nextMovieToPlayId);

        System.out.println("nextMovieToPlayId " + nextMovieToPlayId + " nextMovieIndex " + nextMovieIndex + " movie " + movieMap.get(nextMovieIndex) + " list " + moviesPlayList);

        return nextMovieIndex;

    }

    public String getMovieName(int movieId) {
        Movie movie = movieMap.get(movieId);
        return movie.getMovieName();
    }

    /**
     * Team1 - GamePlay1, GamePlay2
     * Team2 - GamePlay1, GamePlay2 so on
     */
    public void addGamePlay(GamePlay gamePlay) {
        int playerId = gamePlay.getPlayerId();
        int teamId = getTeamId(playerId);

        List<GamePlay> gamePlays = gamePlayMap.get(teamId);
        if (gamePlays == null) {
            gamePlays = new ArrayList<GamePlay>();
            gamePlayMap.put(teamId, gamePlays);
        }
        gamePlays.add(gamePlay);
    }

    public String getGameStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("ScoreBoard").append("\n");
        for (Map.Entry<Integer, List<GamePlay>> entry : gamePlayMap.entrySet()) {
            Integer teamId = entry.getKey();
            String teamName = getTeamName(teamId);
            Integer score = getScore(entry.getValue());
            sb.append("Team ").append(teamName).append(" Score ").append(score).append("\n");
        }
        return sb.toString();
    }

    public Integer getScore(List<GamePlay> gamePlays) {
        int score = 0;
        for (GamePlay gamePlay : gamePlays) {
            score += gamePlay.getScore();
        }
        return score;
    }

    public enum Language {
        ENGLISH, HINDI, TELUGU;

        public static Language getLanguage(String str) {
            if ("HINDI".equalsIgnoreCase(str)) {
                return HINDI;
            } else return TELUGU;
        }
    }
}

