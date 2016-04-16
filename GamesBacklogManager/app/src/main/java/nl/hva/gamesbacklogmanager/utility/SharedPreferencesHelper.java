package nl.hva.gamesbacklogmanager.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nl.hva.gamesbacklogmanager.model.Game;

/**
 * Created by Raoul on 16-4-2016.
 */
public class SharedPreferencesHelper {

    //name of the SharedPreferences file that we will use
    private final String PREFERENCES_FILE = "gameStorage";
    //name of the location where we've stored our games in SharedPreferences
    private final String GAMES_KEY = "games";
    //name of the location where we've stored the newest id
    private final String ID_KEY = "assignableId";

    //Object where we will store/retrieve games from
    private SharedPreferences sharedPreferences;
    //Object that will convert Objects from and to JSON
    private Gson gson;
    //ArrayList with our stored games
    private List<Game> games = new ArrayList<>();

    public SharedPreferencesHelper(Context context) {
        //get the SharedPreferences from the context. If file gameStorage does not exist,
        //it will be created when we commit our changes.
        //MODE_PRIVATE means that only this application can access file gameStorage
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    private List<Game> getGamesFromPreferences() {
        //Get the games list in JSON form from SharedPreferences on the location of GAMES_KEY
        String gamesListJSON = sharedPreferences.getString(GAMES_KEY, "null");
        if (!gamesListJSON.equals("null")) {
            //JSON was found in SharedPreferences

            //Create a Type object that tells how we want our Games converted from JSON,
            //we want an ArrayList with games in it
            //Note: when importing, use 'java.lang.reflect.Type' and 'com.google.gson.reflect.TypeToken';
            Type type = new TypeToken<ArrayList<Game>>() {
            }.getType();
            //Get the ArrayList of games from the JSON String.
            List<Game> retrievedGames = gson.fromJson(gamesListJSON, type);
            return retrievedGames;
        } else {
            //No JSON was found in sharedPreferences, return an empty arrayList
            return new ArrayList<Game>();
        }
    }

    private void setGamesInPreferences(List<Game> gamesToSave) {
        //Get an editor to edit SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //Convert the list of games to a JSON string
        String gameListJSON = gson.toJson(gamesToSave);
        //Put the JSON inside SharedPreferences, assigned to a key (which is the value of GAMES_KEY)
        editor.putString(GAMES_KEY, gameListJSON);
        //Commit the changes
        editor.commit();
    }

    public List<Game> getGames(){
        //return the ArrayList that we get from method getGamesFromPreferences
        return getGamesFromPreferences();
    }


    private long getAssignableId() {
        //Get the id that we can assign to a game
        long newId = sharedPreferences.getLong(ID_KEY, 0);

        //Get an editor and update SharedPreferences with an ID that we can assign to a future game
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(ID_KEY, newId + 1);
        editor.commit();

        return newId;
    }

    public void saveGame(Game game) {
        //Get the most recent list of games from SharedPreferences
        games = getGamesFromPreferences();
        //Give the game the correct id, which we will get from SharedPreferences
        game.setId(getAssignableId());
        games.add(game);

        //Save the updated gamelist in SharedPreferences
        setGamesInPreferences(games);
    }


}


