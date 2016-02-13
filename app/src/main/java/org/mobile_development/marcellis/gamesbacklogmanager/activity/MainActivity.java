package org.mobile_development.marcellis.gamesbacklogmanager.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.mobile_development.marcellis.gamesbacklogmanager.Adapter.GameListItemAdapter;
import org.mobile_development.marcellis.gamesbacklogmanager.R;
import org.mobile_development.marcellis.gamesbacklogmanager.Utility.SharedPreferencesHelper;
import org.mobile_development.marcellis.gamesbacklogmanager.model.Game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ListView gameList;
    private List<Game> games;
    private Game game;
    private GameListItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Create a SharedPreferencesHelper object, and pass it the context of this activity
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(this);
//get the list of games from SharedPreferences

        gameList = (ListView) findViewById(R.id.gameList);

 //       game = new Game(1, "test", "test", new Date(), "test", "test");

        games = new ArrayList<>();


        games = sharedPreferencesHelper.getGames();

//        games.add(game);
        GameListItemAdapter adapter = new GameListItemAdapter(games, this);
        gameList.setAdapter(adapter);

        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Will trigger when the user clicks on a game
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
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

        if (id == R.id.action_add_game){
            Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
