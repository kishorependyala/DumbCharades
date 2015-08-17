package com.teabreaktechnology.dumcharades;

import android.app.Activity;
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

    TextView gameNameTextView;
    TextView teamNameTextView;
    TextView playerNameTextView;
    TextView roundNumberTextView;
    TextView movieNameTextView;
    TextView timerTextView;
    TextView scoreBoardVIew;
    Integer nextPlayerId = 0;
    Integer nextMovieId = 0;
    Button pauseButton;
    Button restartButton;
    CountDownTimer countDownTimer;
    Button nextPlayButton;
    Button correctButton;
    int showMovie = 0;
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
            } else {
                fileToLoad = "movies-telugu.csv";
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

        pauseButton = (Button) findViewById(R.id.pauseButton);
        restartButton = (Button) findViewById(R.id.restartButton);
        nextPlayButton = (Button) findViewById(R.id.nextPlayButton);
        correctButton = (Button) findViewById(R.id.correctButton);


        startTimer(gameCache, gameId, currentTimeValue);
        setNextPlayReadyState(gameCache, gameId);
        nextPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GamePlay gamePlay = new GamePlay.Builder().gameId(gameId).movieId(nextMovieId).playerId(nextPlayerId).score(0).build();
                gameCache.addGamePlay(gamePlay);
                scoreBoardVIew.setText(gameCache.getGameStatus());
                if (showMovie == 1) {
                    setNextPlay(gameCache, gameId);
                } else {
                    setNextPlayReadyState(gameCache, gameId);
                    countDownTimer.cancel();
                }


            }
        });


        correctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GamePlay gamePlay = new GamePlay.Builder().gameId(gameId).movieId(nextMovieId).playerId(nextPlayerId).score(1).build();
                gameCache.addGamePlay(gamePlay);
                scoreBoardVIew.setText(gameCache.getGameStatus());
                setNextPlayReadyState(gameCache, gameId);
                countDownTimer.cancel();

            }
        });


        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPlayButton.setEnabled(false);
                restartButton.setEnabled(true);
                correctButton.setEnabled(false);
                pauseButton.setEnabled(false);
                countDownTimer.cancel();
            }
        });


        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPlayButton.setEnabled(true);
                restartButton.setEnabled(false);
                correctButton.setEnabled(true);
                pauseButton.setEnabled(true);
                startTimer(gameCache, gameId, currentTimeValue);
                countDownTimer.start();
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

                if(timeLeft == 15)
                {
                    playAlertSound(R.raw.beep01);
                }
                if (timeLeft <= 5 && timeLeft >= 2)
                {
                    playAlertSound(R.raw.beep01);
                }
                if(timeLeft == 1){
                    playAlertSound(R.raw.beep02);
                }


            }

            public void onFinish() {
                timerTextView.setText("Done!");

                GamePlay gamePlay = new GamePlay.Builder().gameId(gameId).movieId(nextMovieId).playerId(nextPlayerId).score(0).build();
                gameCache.addGamePlay(gamePlay);
                setNextPlayReadyState(gameCache, gameId);
            }
        };
    }

    private void playAlertSound(int sound)
    {
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
        this.showMovie = 1;
        this.nextPlayerId = gameCache.getNextPlayer(gameId);
        String playerName = gameCache.getPlayerName(nextPlayerId);
        final int teamId = gameCache.getTeamId(nextPlayerId);
        String teamName = gameCache.getTeamName(teamId);
        int roundNumber = gameCache.getRoundNumber();


        teamNameTextView.setText(teamName);
        playerNameTextView.setText(playerName);
        roundNumberTextView.setText("Round " + roundNumber + "");
        scoreBoardVIew.setText(gameCache.getGameStatus());
        restartButton.setEnabled(false);
        pauseButton.setEnabled(false);
        correctButton.setEnabled(false);
        nextPlayButton.setEnabled(true);
        movieNameTextView.setText("");
    }


    private void setNextPlay(GameCache gameCache, int gameId) {
        this.showMovie = 0;
        this.nextMovieId = gameCache.getNextMovie();
        final String nextMovieName = gameCache.getMovieName(nextMovieId);
        movieNameTextView.setText(nextMovieName);
        restartButton.setEnabled(false);
        pauseButton.setEnabled(true);
        correctButton.setEnabled(true);
        nextPlayButton.setEnabled(true);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
