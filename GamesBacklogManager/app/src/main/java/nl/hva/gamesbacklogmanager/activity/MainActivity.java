package nl.hva.gamesbacklogmanager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        ListView listView = (ListView) findViewById(R.id.gameList);
        Game game1 = new Game(1, "Monster Hunter 4", "3DS", new Date(07/06/2015), "Playing", "None");

        ArrayList gameList = new ArrayList();
        gameList.add(game1);

        GameListItemAdapter gameListItemAdapter = new GameListItemAdapter(gameList, this);

        listView.setAdapter(gameListItemAdapter);
    }
}
