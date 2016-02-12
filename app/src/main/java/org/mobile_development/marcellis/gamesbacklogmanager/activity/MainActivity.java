package org.mobile_development.marcellis.gamesbacklogmanager.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.mobile_development.marcellis.gamesbacklogmanager.Adapter.GameListItemAdapter;
import org.mobile_development.marcellis.gamesbacklogmanager.R;
import org.mobile_development.marcellis.gamesbacklogmanager.model.Game;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private ListView gameList;
    private ArrayList<Game> games;
    private Game game;
    private GameListItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameList = (ListView) findViewById(R.id.gameList);

        game = new Game(1, "test", "test", new Date(), "test", "test");
        games = new ArrayList<>();
        games.add(game);
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
}