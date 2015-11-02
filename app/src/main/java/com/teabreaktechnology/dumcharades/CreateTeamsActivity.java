package com.teabreaktechnology.dumcharades;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teabreaktechnology.dumcharades.cache.GameCache;


public class CreateTeamsActivity extends Activity {


    public int playerCount = 0;
    private LinearLayout team1LinearLayout;
    private LinearLayout team2LinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_teams);

        Bundle extras = getIntent().getExtras();

        final String team1Name = extras.getString("team1Name");
        final String team2Name = extras.getString("team2Name");
        final String language = extras.getString("language");
        final String timeIntervalForEachPlay = extras.getString("timeIntervalForEachPlay");
        final Integer difficultyLevel = extras.getInt("difficultyLevel");

        TextView team1TextView = (TextView) findViewById(R.id.team1NameTextView);
        TextView team2TextView = (TextView) findViewById(R.id.team2NameTextView);

        Button startGameButton = (Button) findViewById(R.id.startGameButton);

        team1TextView.setText(team1Name);
        team2TextView.setText(team2Name);

        team1LinearLayout = (LinearLayout) findViewById(R.id.team1LinearLayout);
        team2LinearLayout = (LinearLayout) findViewById(R.id.team2LinearLayout);

        inflateEditRow("player " + nextPlayerId(), team1LinearLayout);
        inflateEditRow("player " + nextPlayerId(), team1LinearLayout);
        inflateEditRow("player " + nextPlayerId(), team2LinearLayout);
        inflateEditRow("player " + nextPlayerId(), team2LinearLayout);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button3);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                Intent startGameIntent = new Intent(CreateTeamsActivity.this, GamePlayActivity.class);

                GameCache gameCache = GameCache.getInstance(true);

                StringBuilder errorString = new StringBuilder();

                int team1Id = gameCache.addTeam(team1Name);
                int team2Id = gameCache.addTeam(team2Name);
                int gameId = 1;


                for (int i = 0; i < team1LinearLayout.getChildCount() - 1; i++) {
                    View childAt = team1LinearLayout.getChildAt(i);
                    EditText editText = (EditText) childAt.findViewById(R.id.editText);
                    String playerName = editText.getText().toString();
                    System.out.println("player name " + playerName + " " + playerName.length());
                    int playerId = gameCache.addPlayer(playerName);

                    if (playerId == -1) {
                        errorString.append("Team 1, " + playerName + "\n");
                    } else {
                        gameCache.addPlayer(gameId, team1Id, playerId);
                    }
                }

                for (int i = 0; i < team2LinearLayout.getChildCount() - 1; i++) {
                    View childAt = team2LinearLayout.getChildAt(i);
                    EditText editText = (EditText) childAt.findViewById(R.id.editText);
                    String playerName = editText.getText().toString();
                    System.out.println("player name " + playerName + " " + playerName.length());
                    int playerId = gameCache.addPlayer(playerName);
                    gameCache.addPlayer(gameId, team2Id, playerId);

                    if (playerId == -1) {
                        errorString.append("Team 2, " + playerName + "\n");
                    } else {
                        gameCache.addPlayer(gameId, team2Id, playerId);
                    }
                }

                startGameIntent.putExtra("gameId", gameId + "");
                startGameIntent.putExtra("timeIntervalForEachPlay", timeIntervalForEachPlay);
                startGameIntent.putExtra("language", language);
                startGameIntent.putExtra("difficultyLevel", difficultyLevel);

                startGameIntent.putExtra("team1Name", team1Name);
                startGameIntent.putExtra("team2Name", team2Name);

                if (errorString.length() > 0) {

                    showAlertPopup(errorString.toString());
                } else {
                    startActivity(startGameIntent);
                }

            }
        });
    }

    private void showAlertPopup(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(CreateTeamsActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Duplicates in playerNames - please pick unique names across the teams \n" + message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",

                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private int nextTeam2PlayerId() {
        return (++playerCount);
    }

    private int nextPlayerId() {
        return (++playerCount);
    }


    public void onAddForTeam1Clicked(View v) {
        inflateEditRow("player " + nextPlayerId(), team1LinearLayout);
    }

    public void onAddForTeam2Clicked(View v) {
        inflateEditRow("player " + nextPlayerId(), team2LinearLayout);

    }

    public void onDeleteForTeam1Clicked(View v) {
        team1LinearLayout.removeView((View) v.getParent());
    }

    public void onDeleteForTeam2Clicked(View v) {
        team2LinearLayout.removeView((View) v.getParent());
    }

    private void inflateEditRow(String name, LinearLayout linearLayout) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = null;
        if (linearLayout == team1LinearLayout) {
            rowView = inflater.inflate(R.layout.team1row, null);
        } else {
            rowView = inflater.inflate(R.layout.team2row, null);
        }
        final EditText editText = (EditText) rowView.findViewById(R.id.editText);

        editText.setText(name);
        linearLayout.addView(rowView, linearLayout.getChildCount() - 1);
    }
}