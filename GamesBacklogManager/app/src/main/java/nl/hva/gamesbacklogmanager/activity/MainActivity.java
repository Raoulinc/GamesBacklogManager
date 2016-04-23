package nl.hva.gamesbacklogmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.adapter.GameListItemAdapter;
import nl.hva.gamesbacklogmanager.model.Game;
import nl.hva.gamesbacklogmanager.utility.DBCRUD;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListView();
    }

    private void setListView() {

        ListView gameList = (ListView) findViewById(R.id.gameList);

        List<Game> games;

        //Create a DBCRUD object, and pass it the context of this activity
        DBCRUD dbcrud = new DBCRUD(this);
//get the list of games from Database
        games = dbcrud.getGames();

        //Game game1 = new Game(1, "Monster Hunter 4", "3DS", new Date(07 / 06 / 2015), "Playing", "");
        // games.add(game1);

        GameListItemAdapter gameListItemAdapter = new GameListItemAdapter(games, this);

        gameList.setAdapter(gameListItemAdapter);

        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Will trigger when the user clicks on a game
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, GameDetailsActivity.class);
                //Get the correct game based on which listitem got clicked, and put it as parameter in the intent
                Game selectedGame = (Game) parent.getAdapter().getItem(position);
                intent.putExtra("selectedGame", selectedGame);
                //Open GameDetailsActivity
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_add_game) {
            Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}

