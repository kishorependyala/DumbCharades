package com.teabreaktechnology.dumcharades;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.teabreaktechnology.util.AlertUtil;
import com.teabreaktechnology.util.StringUtil;


public class HomeScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        final EditText team1EditText = (EditText) findViewById(R.id.team1Name);
        final EditText team2EditText = (EditText) findViewById(R.id.team2Name);
        team1EditText.setText("Team 1");
        team2EditText.setText("Team 2");

        Button createGameButton = (Button) findViewById(R.id.createGameButton);

        String[] timeIntervals = new String[]{"10 seconds", "1 minute", "2 minutes", "3 minutes", "4 minutes"};
        final String[] timeIntervalInSeconds = new String[]{"10", "60", "120", "180", "240"};

        String[] languages = new String[]{"Hindi", "Telugu", "English"};

        final Spinner timeIntervalSpinner = (Spinner) findViewById(R.id.timeIntervalForEachPlay);
        ArrayAdapter<String> timeIntervalAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timeIntervals);
        timeIntervalSpinner.setAdapter(timeIntervalAdapter);
        timeIntervalSpinner.setSelection(1);

        final Spinner languageSpinner = (Spinner) findViewById(R.id.language);
        ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, languages);
        languageSpinner.setAdapter(languageAdapter);

        String[] difficultyLevelForDropDown = new String[]{"Easy", "Hard"};
        final int[] difficultyLevel = new int[]{2, 1};

        final Spinner difficultyLevelSpinner = (Spinner) findViewById(R.id.difficultyLevel);
        ArrayAdapter<String> difficultyLevelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, difficultyLevelForDropDown);
        difficultyLevelSpinner.setAdapter(difficultyLevelAdapter);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button3);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();

                String team2Name = team2EditText.getText().toString();
                String team1Name = team1EditText.getText().toString();
                if (!StringUtil.isValidString(team1Name)) {
                    AlertUtil.showAlertPopup("Enter valid name for first team", HomeScreenActivity.this);
                    return;
                }
                if (!StringUtil.isValidString(team2Name)) {
                    AlertUtil.showAlertPopup("Enter valid name for second team", HomeScreenActivity.this);
                    return;
                }
                if (team1Name.equalsIgnoreCase(team2Name)) {
                    AlertUtil.showAlertPopup("Both team names cannot be the same", HomeScreenActivity.this);
                    return;
                }

                int selectedId = timeIntervalSpinner.getSelectedItemPosition();
                String timeIntervalForEachPlay = timeIntervalInSeconds[selectedId];
                String language = (String) languageSpinner.getSelectedItem();

                int selectedDifficultyLevelId = difficultyLevelSpinner.getSelectedItemPosition();
                int selectedDifficultyLevel = difficultyLevel[selectedDifficultyLevelId];

                Intent createTeamsIntent = new Intent(HomeScreenActivity.this, CreateTeamsActivity.class);
                createTeamsIntent.putExtra("team1Name", team1Name);
                createTeamsIntent.putExtra("team2Name", team2Name);

                createTeamsIntent.putExtra("timeIntervalForEachPlay", timeIntervalForEachPlay);
                createTeamsIntent.putExtra("language", language);
                createTeamsIntent.putExtra("difficultyLevel", selectedDifficultyLevel);
                startActivity(createTeamsIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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
