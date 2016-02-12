package org.mobile_development.marcellis.gamesbacklogmanager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        game = new Game(1,"test","test",new Date(),"test", "test");
        games = new ArrayList<>();
        games.add(game);
        GameListItemAdapter adapter = new GameListItemAdapter(games, this);
        gameList.setAdapter(adapter);


    }
}
