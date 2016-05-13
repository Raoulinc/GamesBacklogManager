package nl.hva.gamesbacklogmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.model.Game;
import nl.hva.gamesbacklogmanager.utility.DBCRUD;


/**
 * Created by Raoul on 16-4-2016.
 */
public class ModifyGameActivity extends AppCompatActivity {

    private EditText titleInput;
    private EditText platformInput;
    private Spinner statusSpinner;
    private EditText notesInput;
    private FloatingActionButton saveButton;

    private Game game;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_input);

        setTitle("Modify Game");

        titleInput = (EditText) findViewById(R.id.gameTitle);
        platformInput = (EditText) findViewById(R.id.gamePlatform);
        statusSpinner = (Spinner) findViewById(R.id.spinner);
        notesInput = (EditText) findViewById(R.id.notes);
        saveButton = (FloatingActionButton) findViewById(R.id.action_save);

        //Get the selected game that we've sent from GameDetailsActivity
        Intent intent = getIntent();
        game = (Game) intent.getSerializableExtra("currentGame");

        titleInput.setText(game.getTitle());
        platformInput.setText(game.getPlatform());
        notesInput.setText(game.getNotes());

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.game_status, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//Set the adapter to the spinner
        statusSpinner.setAdapter(statusAdapter);

        setSpinnerPosition(statusAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyGame();
            }
        });
    }

    private final void modifyGame() {
        //Get the input from the Views
        String title = titleInput.getText().toString();
        String platform = platformInput.getText().toString();
        String gameStatus = statusSpinner.getSelectedItem().toString();
        String notes = notesInput.getText().toString();

        if ("".equals(title)) {
            //Make EditText titleInput display an error message, and display a toast
            //that the title field is empty
            ModifyGameActivity.setErrorText(titleInput, getString(R.string.title_is_required));
            showToast(getString(R.string.title_field_is_empty));
        } else if ("".equals(platform)) {
            //Make EditText platformInput display an error message, and display a toast
            //that the platform field is empty
            ModifyGameActivity.setErrorText(platformInput, getString(R.string.platform_is_required));
            showToast(getString(R.string.plaftorm_field_is_empty));
        } else {
            //update the game with the new data
            game.setTitle(title);
            game.setPlatform(platform);
            game.setGameStatus(gameStatus);
            game.setNotes(notes);

            //Create a DBCRUD object, and pass it the context of this activity
            DBCRUD dbcrud = new DBCRUD(this);
            dbcrud.modifyGame(game);

            //Notify the user of the success
            showToast(getString(R.string.game_has_been_modified));

            //Go back to ModifyGameActivity, and pass the updated game with is
            Intent intent = new Intent(this, GameDetailsActivity.class);
            intent.putExtra("selectedGame", game);
            startActivity(intent);
        }
    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private static void setErrorText(EditText editText, String message) {
        //get the color white in integer form
        int RGB = Color.argb(255, 255, 0, 0);

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


    private void setSpinnerPosition(ArrayAdapter adapter) {
        if (!game.getGameStatus().equals(null)) {
            //Gets the position of the correct spinner item by comparing
            //which item of the Spinner matches with the gameStatus
            int spinnerPosition = adapter.getPosition(game.getGameStatus());
            //Display the correct gameStatus in the Spinner based on the found position
            statusSpinner.setSelection(spinnerPosition);
        }
    }
}
