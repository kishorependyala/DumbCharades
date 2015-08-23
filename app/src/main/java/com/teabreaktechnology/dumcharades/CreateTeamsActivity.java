package com.teabreaktechnology.dumcharades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.teabreaktechnology.dumcharades.cache.GameCache;


public class CreateTeamsActivity extends Activity {

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

        final EditText team1Player1EditText = (EditText) findViewById(R.id.team1Player1editText);
        final EditText team1Player2EditText = (EditText) findViewById(R.id.team1Player2editText);
        final EditText team2Player1EditText = (EditText) findViewById(R.id.team2Player1editText);
        final EditText team2Player2EditText = (EditText) findViewById(R.id.team2Player2editText);

        team1Player1EditText.setText("Player 1");
        team1Player2EditText.setText("Player 2");
        team2Player1EditText.setText("Player 3");
        team2Player2EditText.setText("Player 4");

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGameIntent = new Intent(CreateTeamsActivity.this, GamePlayActivity.class);
                String team1Player1Name = team1Player1EditText.getText().toString();
                String team1Player2Name = team1Player2EditText.getText().toString();
                String team2Player1Name = team2Player1EditText.getText().toString();
                String team2Player2Name = team2Player2EditText.getText().toString();

                GameCache gameCache = GameCache.getInstance(true);
                int team1Id = gameCache.addTeam(team1Name);
                int team2Id = gameCache.addTeam(team2Name);
                int player1Id = gameCache.addPlayer(team1Player1Name);
                int player2Id = gameCache.addPlayer(team1Player2Name);
                int player3Id = gameCache.addPlayer(team2Player1Name);
                int player4Id = gameCache.addPlayer(team2Player2Name);
                int gameId = 1;

                gameCache.addPlayer(gameId, team1Id, player1Id);
                gameCache.addPlayer(gameId, team1Id, player2Id);
                gameCache.addPlayer(gameId, team2Id, player3Id);
                gameCache.addPlayer(gameId, team2Id, player4Id);

                startGameIntent.putExtra("gameId", gameId + "");
                startGameIntent.putExtra("timeIntervalForEachPlay", timeIntervalForEachPlay);
                startGameIntent.putExtra("language", language);
                startGameIntent.putExtra("difficultyLevel", difficultyLevel);
                startActivity(startGameIntent);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_teams, menu);
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
