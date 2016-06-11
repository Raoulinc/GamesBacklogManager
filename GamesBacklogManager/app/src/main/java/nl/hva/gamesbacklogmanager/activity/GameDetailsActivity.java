package nl.hva.gamesbacklogmanager.activity;

import android.app.ActivityOptions;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.R.id;
import nl.hva.gamesbacklogmanager.R.layout;
import nl.hva.gamesbacklogmanager.R.string;
import nl.hva.gamesbacklogmanager.model.Game;
import nl.hva.gamesbacklogmanager.utility.ConfirmDeleteDialog;
import nl.hva.gamesbacklogmanager.utility.ConfirmDeleteDialog.ConfirmDeleteDialogListener;
import nl.hva.gamesbacklogmanager.utility.DBCRUD;

/**
 * Created by Raoul on 16-4-2016.
 */
public class GameDetailsActivity extends AppCompatActivity implements ConfirmDeleteDialogListener {

    Game game;

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User clicked on the confirm button of the Dialog, delete the game from Database
        DBCRUD dbcrud = new DBCRUD(this);
        // We only need the id of the game to delete it
        dbcrud.deleteGame(game.getId());
        // Game has been deleted, go back to MainActivity
        showGameDeletedToast();
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // Do nothing, Dialog will disappear
    }

    private void showGameDeletedToast() {
        Context context = getApplicationContext();
        String text = String.format("%s %s", game.getTitle(), getString(string.game_deleted));
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_game_detail);

        setTitle("Details");

        // Get the game from the intent, which was passed as parameter
        game = (Game) getIntent().getSerializableExtra("selectedGame");

        setGameView();
    }

    private void setGameView() {
        TextView title = (TextView) findViewById(id.gameTitle);
        TextView platform = (TextView) findViewById(id.gamePlatform);
        TextView status = (TextView) findViewById(id.gameStatus);
        TextView date = (TextView) findViewById(id.gameDate);
        TextView notes = (TextView) findViewById(id.notes);

        title.setText(game.getTitle());
        platform.setText(game.getPlatform());
        status.setText(game.getGameStatus());
        date.setText(game.getDateAdded());
        notes.setText(game.getNotes());

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(id.action_modify_game);
        floatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameDetailsActivity.this, ModifyGameActivity.class);
                intent.putExtra("selectedGame", game);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivityForResult(intent, 1000,
                            ActivityOptions.makeSceneTransitionAnimation(GameDetailsActivity.this).toBundle());
                } else {
                    startActivityForResult(intent, 1000);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Set the Game Card with the updated game
        game = (Game) data.getSerializableExtra("selectedGame");
        setGameView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_game) {
            // Show the ConfirmDeleteDialog
            DialogFragment dialog = new ConfirmDeleteDialog();
            Bundle bundle = new Bundle();
            bundle.putString("message", getString(string.dialog_game_deletion_single));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "ConfirmDeleteDialog");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_details, menu);
        return true;
    }
}
