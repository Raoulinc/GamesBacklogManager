package nl.hva.gamesbacklogmanager.activity;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.R.menu;
import nl.hva.gamesbacklogmanager.model.Game;
import nl.hva.gamesbacklogmanager.utility.ConfirmDeleteDialog;
import nl.hva.gamesbacklogmanager.utility.DBCRUD;

/**
 * Created by Raoul on 16-4-2016.
 */
public class GameDetailsActivity extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener {
    private Game game;

    private TextView title;
    private TextView platform;
    private TextView status;
    private TextView date;
    private TextView notes;

    private FloatingActionButton floatingActionButton;
    private FloatingActionButton floatingActionButton1;
    private FloatingActionButton floatingActionButton2;
    FloatingActionButton floatingActionButton3;

    //Animations
    private Animation show_fab_1;
    private Animation hide_fab_1;
    private Animation show_fab_2;
    private Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;

    private boolean shown;

    @Override
    public final void onDialogPositiveClick(DialogFragment dialog) {
        //User clicked on the confirm button of the Dialog, delete the game from Database
        DBCRUD dbcrud = new DBCRUD(this);
        //We only need the id of the game to delete it
        dbcrud.deleteGame(game.getId());

        //Game has been deleted, go back to MainActivity
        showGameDeletedToast();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        //Do nothing, Dialog will disappear
    }

    private final void showGameDeletedToast() {
        Context context = getApplicationContext();
        String text = getString(R.string.game_deleted);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }



    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        setTitle("Details");

        title = (TextView) findViewById(R.id.gameTitle);
        platform = (TextView) findViewById(R.id.gamePlatform);
        status = (TextView) findViewById(R.id.gameStatus);
        date = (TextView) findViewById(R.id.gameDate);
        notes = (TextView) findViewById(R.id.notes);



        //Get the game from the intent, which was passed as parameter
        game = (Game) getIntent().getSerializableExtra("selectedGame");

        //Get the Date object from the game, and convert it to a day/month/year String, by formatting it with a SimpleDateFormat object
        //String dateString = new SimpleDateFormat("dd/MM/yyyy").format(game.getDateAdded());

        title.setText(game.getTitle());
        platform.setText(game.getPlatform());
        status.setText(game.getGameStatus());
        date.setText(game.getDateAdded());
        notes.setText(game.getNotes());

        floatingActionButton = (FloatingActionButton) findViewById(R.id.action_modify_game);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GameDetailsActivity.this, ModifyGameActivity.class);
                intent.putExtra("currentGame", game);
                startActivity(intent);


            }
        });
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_modify_game) {
            //Go to ModifyGameActivity, and pass the current game with it to modify
            Intent intent = new Intent(this, ModifyGameActivity.class);
            intent.putExtra("currentGame", game);
            startActivity(intent);
        } else if (id == R.id.action_delete_game) {
            //Show the ConfirmDeleteDialog
            DialogFragment dialog = new ConfirmDeleteDialog();
            dialog.show(getFragmentManager(), "ConfirmDeleteDialog");
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_details, menu);
        return true;
    }


}
