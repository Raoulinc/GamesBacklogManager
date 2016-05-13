package nl.hva.gamesbacklogmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.R.menu;
import nl.hva.gamesbacklogmanager.adapter.GameListItemAdapter;
import nl.hva.gamesbacklogmanager.model.Game;
import nl.hva.gamesbacklogmanager.utility.DBCRUD;


public class MainActivity extends AppCompatActivity {
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_main);
        setTitle(getString(R.string.title_screen_main));

        setListView();

        setFloatingActionButton();
    }

    private void setFloatingActionButton() {
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setListView() {
        RecyclerView gameList = (RecyclerView) findViewById(R.id.gameList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        gameList.setLayoutManager(mLayoutManager);

        List<Game> games;

        // Create a DBCRUD object, and pass it the context of this activity
        DBCRUD dbcrud = new DBCRUD(this);
        // Get the list of games from Database
        games = dbcrud.getGames();

        // Game game1 = new Game(1, "Monster Hunter 4", "3DS", new Date(07 / 06 / 2015), "Playing", "");
        // games.add(game1);

        final GameListItemAdapter gameListItemAdapter = new GameListItemAdapter(games, this);

        if (gameList != null) {
            gameList.setAdapter(gameListItemAdapter);
        }


        gameList.setOnClickListener(new RecyclerView.OnClickListener() {
            @Override
            public void onClick(View view) {

                View parentRow = (View) view.getParent();
                ListView listView = (ListView) parentRow.getParent();
                int position = listView.getPositionForView(parentRow);

                Intent intent = new Intent(MainActivity.this, GameDetailsActivity.class);
                // Get the correct game based on which list item got clicked, and put it as parameter in the intent
                Game selectedGame = (Game) gameListItemAdapter.getItem(position);
                intent.putExtra("selectedGame", selectedGame);
                //Open GameDetailsActivity
                startActivity(intent);
            }


        });


    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AddGameActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}

