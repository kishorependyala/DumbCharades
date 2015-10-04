package com.teabreaktechnology.dumcharades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teabreaktechnology.dumcharades.cache.GameCache;


public class CreateTeamsActivity extends Activity {


    public int team1Count = 0;
    public int team2Count = 0;
    private LinearLayout team1LinearLayout;
    private LinearLayout team2LinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_teams);

        getActionBar().setDisplayHomeAsUpEnabled(true);

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

        inflateEditRow("player " + nextTeam1PlayerId(), team1LinearLayout);
        inflateEditRow("player " + nextTeam1PlayerId(), team1LinearLayout);
        inflateEditRow("player " + nextTeam2PlayerId(), team2LinearLayout);
        inflateEditRow("player " + nextTeam2PlayerId(), team2LinearLayout);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button3);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                Intent startGameIntent = new Intent(CreateTeamsActivity.this, GamePlayActivity.class);

                GameCache gameCache = GameCache.getInstance(true);

                int team1Id = gameCache.addTeam(team1Name);
                int team2Id = gameCache.addTeam(team2Name);
                int gameId = 1;


                for (int i = 0; i < team1LinearLayout.getChildCount() - 1; i++) {
                    View childAt = team1LinearLayout.getChildAt(i);
                    EditText editText = (EditText) childAt.findViewById(R.id.editText);
                    String s = editText.getText().toString();
                    System.out.println("player name " + s + " " + s.length());
                    int player1Id = gameCache.addPlayer(s);
                    gameCache.addPlayer(gameId, team1Id, player1Id);
                }

                for (int i = 0; i < team2LinearLayout.getChildCount() - 1; i++) {
                    View childAt = team2LinearLayout.getChildAt(i);
                    EditText editText = (EditText) childAt.findViewById(R.id.editText);
                    String s = editText.getText().toString();
                    System.out.println("player name " + s + " " + s.length());
                    int player1Id = gameCache.addPlayer(s);
                    gameCache.addPlayer(gameId, team2Id, player1Id);
                }

                startGameIntent.putExtra("gameId", gameId + "");
                startGameIntent.putExtra("timeIntervalForEachPlay", timeIntervalForEachPlay);
                startGameIntent.putExtra("language", language);
                startGameIntent.putExtra("difficultyLevel", difficultyLevel);
                startActivity(startGameIntent);

            }
        });
    }

    private int nextTeam2PlayerId() {
        return (++team2Count);
    }

    private int nextTeam1PlayerId() {
        return (++team1Count);
    }


    public void onAddForTeam1Clicked(View v) {
        inflateEditRow("player " + nextTeam1PlayerId(), team1LinearLayout);
    }

    public void onAddForTeam2Clicked(View v) {
        team2Count++;
        inflateEditRow("player " + nextTeam2PlayerId(), team2LinearLayout);

    }

    public void onDeleteForTeam1Clicked(View v) {
        team1Count--;
        team1LinearLayout.removeView((View) v.getParent());
    }

    public void onDeleteForTeam2Clicked(View v) {
        team2Count--;
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