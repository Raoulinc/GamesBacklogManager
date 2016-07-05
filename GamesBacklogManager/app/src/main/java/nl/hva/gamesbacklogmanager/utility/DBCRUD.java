package nl.hva.gamesbacklogmanager.utility;

/**
 * Created by Raoul on 3-2-2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nl.hva.gamesbacklogmanager.model.Game;

public class DBCRUD {
    private final DBHelper dbHelper;

    public DBCRUD(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void saveGame(Game game) {

        // Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Game.KEY_TITLE, game.getTitle());
        values.put(Game.KEY_PLATFORM, game.getPlatform());
        values.put(Game.KEY_DATE, game.getDateAdded());
        values.put(Game.KEY_STATUS, game.getGameStatus());
        values.put(Game.KEY_NOTES, game.getNotes());

        // Inserting Row
        db.insert(Game.TABLE, null, values);
        db.close(); // Closing database connection
    }

    public void deleteGame(long user_Id) {
        Log.d("Database", "Deleted: " + user_Id);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Game.TABLE, Game.KEY_ID + "= ?", new String[]{String.valueOf(user_Id)});
        db.close(); // Closing database connection
    }

    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Game.TABLE, null, null);
        db.close(); // Closing database connection
    }

    public void modifyGame(Game game) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Game.KEY_TITLE, game.getTitle());
        values.put(Game.KEY_PLATFORM, game.getPlatform());
        values.put(Game.KEY_DATE, game.getDateAdded());
        values.put(Game.KEY_STATUS, game.getGameStatus());
        values.put(Game.KEY_NOTES, game.getNotes());

        db.update(Game.TABLE, values, Game.KEY_ID + "= ?", new String[]{String.valueOf(game.getId())});
        db.close(); // Closing database connection
    }

    public List<Game> getGames() // Get all games
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
}
