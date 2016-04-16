package nl.hva.gamesbacklogmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.model.Game;

/**
 * Created by Raoul on 16-4-2016.
 */
public class GameDetailsActivity extends AppCompatActivity {
    Game game = new Game();

    TextView title;
    TextView platform;
    TextView status;
    TextView date;
    TextView notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detail);

        title = (TextView) findViewById(R.id.gameTitle);
        platform = (TextView) findViewById(R.id.gamePlatform);
        status = (TextView) findViewById(R.id.gameStatus);
        date = (TextView) findViewById(R.id.gameDate);
        notes = (TextView) findViewById(R.id.notes);

        //Get the game from the intent, which was passed as parameter
        game = (Game) getIntent().getSerializableExtra("selectedGame");

        //Get the Date object from the game, and convert it to a day/month/year String, by formatting it with a SimpleDateFormat object
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(game.getDateAdded());

        title.setText(game.getTitle());
        platform.setText(game.getPlatform());
        status.setText(game.getGameStatus());
        date.setText(dateString);
        notes.setText(game.getNotes());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_modify_game) {
            //Go to ModifyGameActivity, and pass the current game with it to modify
            Intent intent = new Intent(GameDetailsActivity.this, ModifyGameActivity.class);
            intent.putExtra("currentGame", game);
            startActivity(intent);
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
