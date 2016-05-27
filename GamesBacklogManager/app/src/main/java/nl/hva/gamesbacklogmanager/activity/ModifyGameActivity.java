package nl.hva.gamesbacklogmanager.activity;

import android.R.layout;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.R.array;
import nl.hva.gamesbacklogmanager.R.id;
import nl.hva.gamesbacklogmanager.R.string;
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

    private Game game;

    private static void setErrorText(EditText editText, String message) {
        // Get the color white in integer form
        int RGB = Color.argb(255, 255, 0, 0);

        // Object that contains the color white
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(RGB);

        // Object that will hold the message, and makes it possible to change the color of the text
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);

        // Give the message from the first till the last character a white color.
        // The last '0' means that the message should not display additional behaviour
        ssbuilder.setSpan(fgcspan, 0, message.length(), 0);

        // Make the EditText display the error message
        editText.setError(ssbuilder);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_input);

        setTitle("Modify Game");

        titleInput = (EditText) findViewById(id.gameTitle);
        platformInput = (EditText) findViewById(id.gamePlatform);
        statusSpinner = (Spinner) findViewById(id.spinner);
        notesInput = (EditText) findViewById(id.notes);
        FloatingActionButton saveButton = (FloatingActionButton) findViewById(id.action_save);

        // Get the selected game that we've sent from GameDetailsActivity
        Intent intent = getIntent();
        game = (Game) intent.getSerializableExtra("selectedGame");

        titleInput.setText(game.getTitle());
        platformInput.setText(game.getPlatform());
        notesInput.setText(game.getNotes());

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter statusAdapter = ArrayAdapter.createFromResource(this,
                array.game_status, layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        statusAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        statusSpinner.setAdapter(statusAdapter);

        setSpinnerPosition(statusAdapter);

        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyGame();
            }
        });
    }

    private void modifyGame() {
        // Get the input from the Views
        String title = titleInput.getText().toString();
        String platform = platformInput.getText().toString();
        String gameStatus = statusSpinner.getSelectedItem().toString();
        String notes = notesInput.getText().toString();

        if (title != null && title.isEmpty()) {
            // Make EditText titleInput display an error message, and display a toast
            // That the title field is empty
            ModifyGameActivity.setErrorText(titleInput, getString(string.title_is_required));
            showToast(getString(string.title_field_is_empty));
        } else if (platform != null && platform.isEmpty()) {
            // Make EditText platformInput display an error message, and display a toast
            // That the platform field is empty
            ModifyGameActivity.setErrorText(platformInput, getString(string.platform_is_required));
            showToast(getString(string.platform_field_is_empty));
        } else {
            // Update the game with the new data
            game.setTitle(title);
            game.setPlatform(platform);
            game.setGameStatus(gameStatus);
            game.setNotes(notes);

            // Create a DBCRUD object, and pass it the context of this activity
            DBCRUD dbcrud = new DBCRUD(this);
            dbcrud.modifyGame(game);

            //Notify the user of the success
            showToast(getString(string.game_has_been_modified));

            //Go back to ModifyGameActivity, and pass the updated game with it
            Intent intent = new Intent(this, GameDetailsActivity.class);
            intent.putExtra("selectedGame", game);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        }
    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private void setSpinnerPosition(ArrayAdapter adapter) {
        if (!game.getGameStatus().equals(null)) {
            // Gets the position of the correct spinner item by comparing
            // which item of the Spinner matches with the gameStatus
            int spinnerPosition = adapter.getPosition(game.getGameStatus());
            // Display the correct gameStatus in the Spinner based on the found position
            statusSpinner.setSelection(spinnerPosition);
        }
    }
}
