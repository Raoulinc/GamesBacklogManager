package org.mobile_development.marcellis.gamesbacklogmanager.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.mobile_development.marcellis.gamesbacklogmanager.R;
import org.mobile_development.marcellis.gamesbacklogmanager.model.Game;

import java.text.SimpleDateFormat;

public class DetailsActivity extends AppCompatActivity {

    private TextView title, platform, status, date, notes;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (TextView) findViewById(R.id.details_label_title);

        game = (Game) getIntent().getSerializableExtra("selectedGame");



//Get the Date object from the game, and convert it to a day/month/year String, by formatting it with
//a SimpleDateFormat object
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(game.getDateAdded());


        title = (TextView) findViewById(R.id.details_value_title);
        platform = (TextView) findViewById(R.id.details_value_platform);
        status = (TextView) findViewById(R.id.details_value_status);
        date = (TextView) findViewById(R.id.details_value_date);
        notes = (TextView) findViewById(R.id.details_value_notes);

//set the game data into the Views
        title.setText(game.getTitle());
        platform.setText(game.getPlatform());
        status.setText(game.getGameStatus());
        date.setText(dateString);
        notes.setText(game.getNotes());


        title.setText(game.getTitle());
        platform.setText(game.getPlatform());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
