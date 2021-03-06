package nl.hva.gamesbacklogmanager.activity;

import android.R.layout;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.R.array;
import nl.hva.gamesbacklogmanager.R.id;
import nl.hva.gamesbacklogmanager.R.string;
import nl.hva.gamesbacklogmanager.model.Game;
import nl.hva.gamesbacklogmanager.utility.DBCRUD;

/**
 * Created by Raoul on 16-4-2016.
 */
public class AddGameActivity extends AppCompatActivity {

    private EditText titleInput;
    private EditText platformInput;
    private Spinner statusSpinner;
    private EditText notesInput;

    private static String getSimpleCurrentDate() {
        // Formatter that will convert dates into the day-month-year format
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        //Today's date, but with time included, which we don't want
        Date today = new Date();
        // Format.format returns a string
        return format.format(today);
    }

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

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(string.title_screen_add));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleInput = (EditText) findViewById(id.gameTitle);
        platformInput = (EditText) findViewById(id.gamePlatform);
        statusSpinner = (Spinner) findViewById(id.spinner);
        notesInput = (EditText) findViewById(id.notes);
        FloatingActionButton saveButton = (FloatingActionButton) findViewById(id.action_save);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter statusAdapter = ArrayAdapter.createFromResource(this,
                array.game_status, layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        statusAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        statusSpinner.setAdapter(statusAdapter);

        // Set clickListiner on Fab button
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGame();
            }
        });
    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    void saveGame() {
        // Get the current date in numbered day-month-year format
        String curDate = AddGameActivity.getSimpleCurrentDate();

        // Retrieve the input from the user
        String title = titleInput.getText().toString();
        String platform = platformInput.getText().toString();
        String gameStatus = statusSpinner.getSelectedItem().toString();
        String notes = notesInput.getText().toString();

        if ((title != null) && title.isEmpty()) {
            // Make EditText titleInput display an error message, and display a toast
            // That the title field is empty
            AddGameActivity.setErrorText(titleInput, getString(string.title_is_required));
            showToast(getString(string.title_field_is_empty));
        } else if ((platform != null) && platform.isEmpty()) {
            // Make EditText platformInput display an error message, and display a toast
            // That the platform field is empty
            AddGameActivity.setErrorText(platformInput, getString(string.platform_is_required));
            showToast(getString(string.platform_field_is_empty));
        } else {
            // Create a DBCRUD object, and pass it the context of this activity
            DBCRUD dbcrud = new DBCRUD(this);
            // Make a game object based on the input. The correct id will be set in DBCRUD.saveGame()
            Game game = new Game(-1, title, platform, curDate, gameStatus, notes);
            // Save the game to the Database
            dbcrud.saveGame(game);
            // Notify the user with a toast that the game has been added
            showToast(getString(string.game_has_been_added));
            // Go back to MainActivity
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                cancelModifyGame();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cancelModifyGame() {
        // Notify the user with a toast that the game has not been saved
        showToast(getString(string.game_has_been_modified_negative));
        // Go back to MainActivity
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onResume();  // Always call the superclass method first
        cancelModifyGame();
    }
}
