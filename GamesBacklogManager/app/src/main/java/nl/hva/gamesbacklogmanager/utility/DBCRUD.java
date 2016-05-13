package nl.hva.gamesbacklogmanager.utility;

/**
 * Created by Raoul on 3-2-2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import nl.hva.gamesbacklogmanager.model.Game;

public class DBCRUD {
    private final DBHelper dbHelper;

    public DBCRUD(Context context) {
        dbHelper = new DBHelper(context);
    }

    public final void saveGame(Game game) {

        // Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Get the Date object from the game, and convert it to a day/month/year String, by formatting it with a SimpleDateFormat object
        //String dateString = new SimpleDateFormat("dd/MM/yyyy").format(game.dateAdded);

        ContentValues values = new ContentValues();
        values.put(Game.KEY_TITLE, game.title);
        values.put(Game.KEY_PLATFORM, game.platform);
        values.put(Game.KEY_DATE, game.dateAdded);
        values.put(Game.KEY_STATUS, game.gameStatus);
        values.put(Game.KEY_NOTES, game.notes);

        // Inserting Row
        db.insert(Game.TABLE, null, values);
        db.close(); // Closing database connection
    }

    public void deleteGame(long user_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Game.TABLE, Game.KEY_ID + "= ?", new String[]{String.valueOf(user_Id)});
        db.close(); // Closing database connection
    }

    public void modifyGame(Game game) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Get the Date object from the game, and convert it to a day/month/year String, by formatting it with a SimpleDateFormat object
        //String dateString = new SimpleDateFormat("dd/MM/yyyy").format(game.dateAdded);

        ContentValues values = new ContentValues();
        values.put(Game.KEY_TITLE, game.title);
        values.put(Game.KEY_PLATFORM, game.platform);
        values.put(Game.KEY_DATE, game.dateAdded);
        values.put(Game.KEY_STATUS, game.gameStatus);
        values.put(Game.KEY_NOTES, game.notes);

        db.update(Game.TABLE, values, Game.KEY_ID + "= ?", new String[]{String.valueOf(game.id)});
        db.close(); // Closing database connection
    }

    public List<Integer> getGameIDs() // Get list of all Game-IDs in the database
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  " +
                Game.KEY_ID +
                " FROM " + Game.TABLE;

        List<Integer> gameIDs = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                gameIDs.add(Integer.valueOf(cursor.getInt(cursor.getColumnIndex(Game.KEY_ID))));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return gameIDs;
    }

    public final List<Game> getGames() // Get all games
    {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  " +
                Game.KEY_ID + ',' +
                Game.KEY_TITLE + ',' +
                Game.KEY_PLATFORM + ',' +
                Game.KEY_DATE + ',' +
                Game.KEY_STATUS + ',' +
                Game.KEY_NOTES +
                " FROM " + Game.TABLE;

        //User user = new User();
        List<Game> gameList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Game game = new Game();
                game.setId(cursor.getInt(cursor.getColumnIndex(Game.KEY_ID)));
                game.setTitle(cursor.getString(cursor.getColumnIndex(Game.KEY_TITLE)));
                game.setPlatform(cursor.getString(cursor.getColumnIndex(Game.KEY_PLATFORM)));
                game.setDateAdded(cursor.getString(cursor.getColumnIndex(Game.KEY_DATE)));
                game.setGameStatus(cursor.getString(cursor.getColumnIndex(Game.KEY_STATUS)));
                game.setNotes(cursor.getString(cursor.getColumnIndex(Game.KEY_NOTES)));
                gameList.add(game);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return gameList;
    }

    public Game getGameById(int Id) // Get game data by ID
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  " +
                Game.KEY_ID + ',' +
                Game.KEY_TITLE + ',' +
                Game.KEY_PLATFORM + ',' +
                Game.KEY_DATE + ',' +
                Game.KEY_STATUS + ',' +
                Game.KEY_NOTES +
                " FROM " + Game.TABLE +
                " WHERE " + Game.KEY_ID + "=?";

        Game game = new Game();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                game.id = cursor.getInt(cursor.getColumnIndex(Game.KEY_ID));
                game.title = cursor.getString(cursor.getColumnIndex(Game.KEY_TITLE));
                game.platform = cursor.getString(cursor.getColumnIndex(Game.KEY_PLATFORM));
                game.dateAdded = cursor.getString(cursor.getColumnIndex(Game.KEY_DATE));
                game.gameStatus = cursor.getString(cursor.getColumnIndex(Game.KEY_STATUS));
                game.notes = cursor.getString(cursor.getColumnIndex(Game.KEY_NOTES));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return game;
    }

}
