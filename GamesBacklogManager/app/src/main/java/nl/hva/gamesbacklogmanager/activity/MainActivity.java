package nl.hva.gamesbacklogmanager.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.adapter.GameListItemAdapter;
import nl.hva.gamesbacklogmanager.model.Game;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListView();
    }

    private void setListView()
    {
        ListView gameList = (ListView) findViewById(R.id.gameList);
        Game game1 = new Game(1, "Monster Hunter 4", "3DS", new Date(07/06/2015), "Playing", "");

        ArrayList gameArrayList = new ArrayList();
        gameArrayList.add(game1);

        GameListItemAdapter gameListItemAdapter = new GameListItemAdapter(gameArrayList, this);

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


}
