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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teabreaktechnology.dumcharades.cache.GameCache;


public class CreateTeamsActivity extends Activity {
    // Parent view for all rows and the add button.
    private LinearLayout mContainerView;
    public String PlayerNames[];
    public int i = 1;

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

        Button addteam = (Button) findViewById(R.id.add);
        Button startGameButton = (Button) findViewById(R.id.startGameButton);

        team1TextView.setText(team1Name);
        team2TextView.setText(team2Name);

        mContainerView = (LinearLayout) findViewById(R.id.parentView);


        inflateEditRow("player " + i);


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


                startGameIntent.putExtra("gameId", gameId + "");
                startGameIntent.putExtra("timeIntervalForEachPlay", timeIntervalForEachPlay);
                startGameIntent.putExtra("language", language);
                startGameIntent.putExtra("difficultyLevel", difficultyLevel);
                startActivity(startGameIntent);

            }
        });
    }


    public void onAddNewClicked(View v) {
        i++;
        // Inflate a new row and hide the button self.
        inflateEditRow("player " + i);

    }

    // onClick handler for the "-" button of each row
    public void onDeleteClicked(View v) {
        i--;
        // remove the row by calling the getParent on button
        mContainerView.removeView((View) v.getParent());
    }

    // Helper for inflating a row
    private void inflateEditRow(String name) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.row, null);
        final ImageButton deleteButton = (ImageButton) rowView
                .findViewById(R.id.delete);
        final EditText editText = (EditText) rowView
                .findViewById(R.id.editText);

        editText.setText(name);
        // Inflate at the end of all rows but before the "Add new" button
        mContainerView.addView(rowView, mContainerView.getChildCount() - 1);
    }
}