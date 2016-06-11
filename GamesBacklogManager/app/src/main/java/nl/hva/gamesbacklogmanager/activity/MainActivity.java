package nl.hva.gamesbacklogmanager.activity;

import android.app.ActivityOptions;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.R.id;
import nl.hva.gamesbacklogmanager.R.layout;
import nl.hva.gamesbacklogmanager.R.string;
import nl.hva.gamesbacklogmanager.adapter.GameListItemAdapter;
import nl.hva.gamesbacklogmanager.model.Game;
import nl.hva.gamesbacklogmanager.utility.ConfirmDeleteDialog;
import nl.hva.gamesbacklogmanager.utility.ConfirmDeleteDialog.ConfirmDeleteDialogListener;
import nl.hva.gamesbacklogmanager.utility.DBCRUD;


public class MainActivity extends AppCompatActivity implements ConfirmDeleteDialogListener {

    GameListItemAdapter gameListItemAdapter;
    List<Game> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layout.activity_game_main);
        setTitle(getString(string.title_screen_main));

        setListView();

        setFloatingActionButton();
    }

    private void setFloatingActionButton() {
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(id.fab);
        fab1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
    }

    private void setListView() {
        RecyclerView gameListView = (RecyclerView) findViewById(id.gameList);
        LayoutManager mLayoutManager = new LinearLayoutManager(this);
        gameListView.setLayoutManager(mLayoutManager);
        gameListView.setHasFixedSize(true);

        // Create a DBCRUD object, and pass it the context of this activity
        DBCRUD dbcrud = new DBCRUD(this);
        // Get the list of games from Database
        games = dbcrud.getGames();

        gameListItemAdapter = new GameListItemAdapter(games, this);

        if (gameListView != null) {
            gameListView.setAdapter(gameListItemAdapter);
        }

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000L);
        itemAnimator.setRemoveDuration(1000L);
        gameListView.setItemAnimator(itemAnimator);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // Swap the Cards
                Collections.swap(games, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                // Notify adapter Content has changed
                gameListItemAdapter.updateList(games);
                gameListItemAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                // Create a DBCRUD object, and pass it the context of this activity
                DBCRUD dbcrud = new DBCRUD(MainActivity.this);
                // Delete the list of games from Database
                dbcrud.deleteAll();
                for (Game game : games) {
                    dbcrud.saveGame(game);
                }
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                // Create a DBCRUD object, and pass it the context of this activity
                DBCRUD dbcrud = new DBCRUD(MainActivity.this);
                // Delete the list of games from Database
                dbcrud.deleteGame(games.get(viewHolder.getAdapterPosition()).getId());

                // Remove game from temporary list
                games.remove(viewHolder.getAdapterPosition());

                // Notify adapter Content has changed
                gameListItemAdapter.updateList(games);
                gameListItemAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                gameListItemAdapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), games.size());

                // Display toast with Feedback
                showToast(getString(string.swipe_delete));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(gameListView);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // Create a DBCRUD object, and pass it the context of this activity
        DBCRUD dbcrud = new DBCRUD(this);
        // Delete the list of games from Database
        dbcrud.deleteAll();
        // Remove all games from temporary list
        games.removeAll(games);
        // Display toast with Feedback
        showToast(getString(string.action_database_clear));
        // Notify adapter Content has changed
        gameListItemAdapter.updateList(games);
        gameListItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // Do nothing, Dialog will disappear
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        // Create a DBCRUD object, and pass it the context of this activity
        DBCRUD dbcrud = new DBCRUD(this);
        // Get the list of games from Database
        games = dbcrud.getGames();
        gameListItemAdapter.updateList(games);
        gameListItemAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_delete_all) {
            // Show the ConfirmDeleteDialog
            DialogFragment dialog = new ConfirmDeleteDialog();
            Bundle bundle = new Bundle();
            bundle.putString("message", getString(string.dialog_game_deletion_all));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "ConfirmDeleteDialog");
        }
        return super.onOptionsItemSelected(item);
    }
}

