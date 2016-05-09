package nl.hva.gamesbacklogmanager.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.model.Game;
import nl.hva.gamesbacklogmanager.utility.DBCRUD;

/**
 * Created by Raoul on 16-4-2016.
 */
public class AddGameActivity extends Activity {

    EditText titleInput;
    EditText platformInput;
    Spinner statusSpinner;
    EditText notesInput;
    FloatingActionButton saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_input);

        titleInput = (EditText) findViewById(R.id.gameTitle);
        platformInput = (EditText) findViewById(R.id.gamePlatform);
        statusSpinner = (Spinner) findViewById(R.id.spinner);
        notesInput = (EditText) findViewById(R.id.notes);
        saveButton = (FloatingActionButton) findViewById(R.id.action_save);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.game_status, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//Set the adapter to the spinner
        statusSpinner.setAdapter(statusAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGame();
            }
        });
    }

    private String getSimpleCurrentDate() {
        String curDateString = null;
        //formatter that will convert dates into the day-month-year format
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        //Today's date, but with time included, which we don't want
        Date today = new Date();
        //format.format returns a string
        curDateString = format.format(today);
        return curDateString;
    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private void setErrorText(EditText editText, String message) {
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

    public void saveGame() {
        //Get the current date in numbered day-month-year format
        String curDate = getSimpleCurrentDate();
        //Retrieve the input from the user
        String title = titleInput.getText().toString();
        String platform = platformInput.getText().toString();
        String gameStatus = statusSpinner.getSelectedItem().toString();
        String notes = notesInput.getText().toString();

        if (title.equals("")) {
            //Make EditText titleInput display an error message, and display a toast
            //that the title field is empty
            setErrorText(titleInput, getString(R.string.title_is_required));
            showToast(getString(R.string.title_field_is_empty));
        } else if (platform.equals("")) {
            //Make EditText platformInput display an error message, and display a toast
            //that the platform field is empty
            setErrorText(platformInput, getString(R.string.platform_is_required));
            showToast(getString(R.string.plaftorm_field_is_empty));
        } else {
            //Create a DBCRUD object, and pass it the context of this activity
            DBCRUD dbcrud = new DBCRUD(this);
            //Make a game object based on the input. The correct id will be set in DBCRUD.saveGame()
            Game game = new Game(-1, title, platform, curDate, gameStatus, notes);
            //Save the game to sharedPreferences
            dbcrud.saveGame(game);

            //Notify the user with a toast that the game has been added
            showToast(getString(R.string.game_has_been_added));
            //Go back to MainActivity
            Intent intent = new Intent(AddGameActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

}