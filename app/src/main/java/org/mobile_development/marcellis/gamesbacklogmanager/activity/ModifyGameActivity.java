package org.mobile_development.marcellis.gamesbacklogmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.mobile_development.marcellis.gamesbacklogmanager.R;
import org.mobile_development.marcellis.gamesbacklogmanager.Utility.SharedPreferencesHelper;
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
                    modifyGame();
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

    public void modifyGame(){
        //Get the input from the Views
        String title = titleInput.getText().toString();
        String platform = platformInput.getText().toString();
        String gameStatus = statusSpinner.getSelectedItem().toString();
        String notes = notesInput.getText().toString();

        if(title.equals("")){
            //Make EditText titleInput display an error message, and display a toast
            //that the title field is empty
            setErrorText(titleInput, getString(R.string.title_is_required));
            showToast(getString(R.string.title_field_is_empty));
        } else if (platform.equals("")){
            //Make EditText platformInput display an error message, and display a toast
            //that the platform field is empty
            setErrorText(platformInput, getString(R.string.platform_is_required));
            showToast(getString(R.string.plaftorm_field_is_empty));
        } else {
            //update the game with the new data
            game.setTitle(title);
            game.setPlatform(platform);
            game.setGameStatus(gameStatus);
            game.setNotes(notes);

            //Create a SharedPreferencesHelper object, and pass it the context of this activity
            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(this);
            sharedPreferencesHelper.modifyGame(game);

            //Notify the user of the success
            showToast(getString(R.string.game_has_been_modified));

            //Go back to ModifyGameActivity, and pass the updated game with is
            Intent intent = new Intent(ModifyGameActivity.this, DetailsActivity.class);
            intent.putExtra("selectedGame", game);
            startActivity(intent);
        }
    }

    private void showToast(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private void setErrorText(EditText editText, String message){
        //get the color white in integer form
        int RGB = android.graphics.Color.argb(255, 255, 255, 255);

        //Object that contains the color white
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(RGB);

        //object that will hold the message, and makes it possible to change the color of the text
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);

        //give the message from the first till the last character a white color.
        //The last '0' means that the message should not display additional behaviour
        ssbuilder.setSpan(fgcspan, 0, message.length(), 0);

        //Make the EditText display the error message
        editText.setError(ssbuilder);
    }


}
