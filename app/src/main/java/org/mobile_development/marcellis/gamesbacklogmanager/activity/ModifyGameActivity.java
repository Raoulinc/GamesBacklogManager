package org.mobile_development.marcellis.gamesbacklogmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.mobile_development.marcellis.gamesbacklogmanager.R;
import org.mobile_development.marcellis.gamesbacklogmanager.model.Game;

public class ModifyGameActivity extends AppCompatActivity {

    private EditText titleInput, platformInput, notesInput;
    private Spinner statusSpinner;
    private Button modifyButton;

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Get the selected game that we've sent from GameDetailsActivity
        Intent intent = getIntent();
        game = (Game) intent.getSerializableExtra("currentGame");

        //Initialize the Views
        titleInput = (EditText) findViewById(R.id.modify_input_title);
        platformInput = (EditText) findViewById(R.id.modify_input_platform);
        statusSpinner = (Spinner) findViewById(R.id.modify_input_status);
        notesInput = (EditText) findViewById(R.id.modify_input_notes);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.game_status, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set the adapter to the spinner
        statusSpinner.setAdapter(statusAdapter);

        titleInput.setText(game.getTitle());
        platformInput.setText(game.getPlatform());
        setSpinnerPosition(statusAdapter);
        notesInput.setText(game.getNotes());

        modifyButton = (Button) findViewById(R.id.modify_button_modify);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
            //        modifyGame();
                }
            });

      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void setSpinnerPosition(ArrayAdapter adapter){
        if (!game.getGameStatus().equals(null)){
            //Gets the position of the correct spinner item by comparing
            //which item of the Spinner matches with the gameStatus
            int spinnerPosition = adapter.getPosition(game.getGameStatus());
            //Display the correct gameStatus in the Spinner based on the found position
            statusSpinner.setSelection(spinnerPosition);
        }
    }
}
