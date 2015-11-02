package com.teabreaktechnology.dumcharades;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.teabreaktechnology.dumcharades.bean.GamePlay;
import com.teabreaktechnology.dumcharades.cache.GameCache;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;


public class GamePlayActivity extends Activity {

    final int PLAY = 1;
    final int PAUSE = 2;
    final int RESUME = 3;
    final int NEXTPLAY = 4;
    int showMovie = NEXTPLAY;
    TextView gameNameTextView;
    TextView teamNameTextView;
    TextView playerNameTextView;
    TextView roundNumberTextView;
    TextView movieNameTextView;
    TextView timerTextView;
    TextView scoreBoardVIew;
    Integer nextPlayerId = 0;
    Integer nextMovieId = 0;
    CountDownTimer countDownTimer;
    Button nextPlayButton;
    Button correctButton;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);


        final GameCache gameCache = GameCache.getInstance(false);
        Bundle extras = getIntent().getExtras();
        final String language = extras.getString("language");
        final Integer difficultyLevel = extras.getInt("difficultyLevel");
        try {
            String fileToLoad = null;
            if ("hindi".equalsIgnoreCase(language)) {
                fileToLoad = "movies-hindi.csv";
            } else if("telugu".equalsIgnoreCase(language)){
                fileToLoad="movies-telugu.csv";
            }

            else {
                fileToLoad = "movies-english.csv";
            }
            InputStream in = this.getAssets().open(fileToLoad);
            gameCache.run(in, difficultyLevel);
        } catch (Exception e) {

        }
        teamNameTextView = (TextView) findViewById(R.id.teamName);
        playerNameTextView = (TextView) findViewById(R.id.playerName);
        roundNumberTextView = (TextView) findViewById(R.id.roundNumber);
        movieNameTextView = (TextView) findViewById(R.id.movieName);
        timerTextView = (TextView) findViewById(R.id.timer);
        scoreBoardVIew = (TextView) findViewById(R.id.scoreBoardView);
        System.out.println(gameCache.toString());


        String gameIdStr = extras == null ? "" : extras.getString("gameId");

        String timeIntervalForEachPlay = extras == null ? "10" : extras.getString("timeIntervalForEachPlay");
        System.out.println("GameIdStr " + gameIdStr);
        final int gameId = Integer.parseInt(gameIdStr.trim());
        final int eachPlayTime = new Integer(timeIntervalForEachPlay) * 1000;
        final AtomicLong currentTimeValue = new AtomicLong(eachPlayTime);


        nextPlayButton = (Button) findViewById(R.id.nextPlayButton);
        correctButton = (Button) findViewById(R.id.correctButton);


        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button3);

        startTimer(gameCache, gameId, currentTimeValue);

        setNextPlayReadyState(gameCache, gameId);
        correctButton.setVisibility(View.INVISIBLE);

        nextPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();

                if (showMovie == NEXTPLAY || showMovie == PLAY) {
                    GamePlay gamePlay = new GamePlay.Builder().gameId(gameId).movieId(nextMovieId).playerId(nextPlayerId).score(0).build();
                    gameCache.addGamePlay(gamePlay);
                    scoreBoardVIew.setText(gameCache.getGameStatus());
                    nextPlayButton.setBackgroundResource(R.drawable.pause);
                    if (showMovie == PLAY) {
                        correctButton.setVisibility(View.VISIBLE);
                        setNextPlay(gameCache, gameId);
                    } else {
                        correctButton.setVisibility(View.INVISIBLE);
                        setNextPlayReadyState(gameCache, gameId);
                        countDownTimer.cancel();
                    }

                } else if (showMovie == PAUSE) {
                    showMovie = RESUME;
                    nextPlayButton.setBackgroundResource(R.drawable.play);
                    correctButton.setVisibility(View.INVISIBLE);
                    countDownTimer.cancel();
                } else if (showMovie == RESUME) {
                    showMovie = PLAY;
                    nextPlayButton.setBackgroundResource(R.drawable.pause);
                    correctButton.setVisibility(View.VISIBLE);
                    startTimer(gameCache, gameId, currentTimeValue);
                    countDownTimer.start();
                }

            }
        });


        correctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                GamePlay gamePlay = new GamePlay.Builder().gameId(gameId).movieId(nextMovieId).playerId(nextPlayerId).score(1).build();
                gameCache.addGamePlay(gamePlay);
                scoreBoardVIew.setText(gameCache.getGameStatus());
                setNextPlayReadyState(gameCache, gameId);
                countDownTimer.cancel();

            }
        });


    }

    private void startTimer(final GameCache gameCache, final int gameId, final AtomicLong currentTimeValue) {
        countDownTimer = new CountDownTimer(currentTimeValue.get(), 1000) {
            public void onTick(long millisUntilFinished) {
                currentTimeValue.set(millisUntilFinished);
                timerTextView.setText("Seconds remaining: " + millisUntilFinished / 1000);
                currentTimeValue.set(millisUntilFinished);
                long timeLeft = millisUntilFinished / 1000;

                if (timeLeft == 15) {
                    playAlertSound(R.raw.beep01);
                }
                if (timeLeft <= 5 && timeLeft >= 2) {
                    playAlertSound(R.raw.beep01);
                }
                if (timeLeft == 1) {
                    playAlertSound(R.raw.beep02);
                }


            }

            public void onFinish() {
                timerTextView.setText("Done!");

                GamePlay gamePlay = new GamePlay.Builder().gameId(gameId).movieId(nextMovieId).playerId(nextPlayerId).score(0).build();
                gameCache.addGamePlay(gamePlay);
                showMovie = NEXTPLAY;
                setNextPlayReadyState(gameCache, gameId);
            }
        };
    }

    private void playAlertSound(int sound) {
        MediaPlayer mp = MediaPlayer.create(getBaseContext(), sound);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }

        });

    }


    private void setNextPlayReadyState(GameCache gameCache, int gameId) {
        this.showMovie = PLAY;
        this.nextPlayerId = gameCache.getNextPlayer(gameId);
        String playerName = gameCache.getPlayerName(nextPlayerId);
        final int teamId = gameCache.getTeamId(nextPlayerId);
        String teamName = gameCache.getTeamName(teamId);
        int roundNumber = gameCache.getRoundNumber();
        teamNameTextView.setText(teamName);
        playerNameTextView.setText(playerName);
        roundNumberTextView.setText("Round " + roundNumber + "");
        scoreBoardVIew.setText(gameCache.getGameStatus());
        nextPlayButton.setBackgroundResource(R.drawable.play);
        movieNameTextView.setText("");
    }


    private void setNextPlay(GameCache gameCache, int gameId) {
        this.showMovie = NEXTPLAY;
        this.nextMovieId = gameCache.getNextMovie();
        final String nextMovieName = gameCache.getMovieName(nextMovieId);
        movieNameTextView.setText(nextMovieName);
        countDownTimer.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            onBackPressed();
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    @TargetApi(16)
    public void onBackPressed() {
        System.out.println("Back button pressed");

        Intent createTeamsIntent = getParentActivityIntent();


        Bundle extras = getIntent().getExtras();
        final String language = extras.getString("language");
        final Integer difficultyLevel = extras.getInt("difficultyLevel");
        final String timeIntervalForEachPlay = extras.getString("timeIntervalForEachPlay");
        final String team1Name = extras.getString("team1Name");
        final String team2Name = extras.getString("team2Name");

        createTeamsIntent.putExtra("timeIntervalForEachPlay", timeIntervalForEachPlay);
        createTeamsIntent.putExtra("language", language);
        createTeamsIntent.putExtra("difficultyLevel", difficultyLevel);

        createTeamsIntent.putExtra("team1Name", team1Name);
        createTeamsIntent.putExtra("team2Name", team2Name);

        super.onBackPressed();
    }
}
