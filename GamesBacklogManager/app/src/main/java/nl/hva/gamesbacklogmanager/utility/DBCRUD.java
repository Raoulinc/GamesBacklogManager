package nl.hva.gamesbacklogmanager.utility;

/**
 * Created by Raoul on 3-2-2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.hva.gamesbacklogmanager.model.Game;
import nl.hva.gamesbacklogmanager.model.GameTable;

class DBCRUD {
    private final DBHelper dbHelper;

    DBCRUD(Context context) {
        dbHelper = new DBHelper(context);
    }

    public final int insert(GameTable gameTable) {

        // Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Get the Date object from the game, and convert it to a day/month/year String, by formatting it with a SimpleDateFormat object
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(gameTable.dateAdded);

        ContentValues values = new ContentValues();
        values.put(GameTable.KEY_TITLE, gameTable.title);
        values.put(GameTable.KEY_PLATFORM, gameTable.platform);
        values.put(GameTable.KEY_DATE, dateString);
        values.put(GameTable.KEY_STATUS, gameTable.gameStatus);
        values.put(GameTable.KEY_NOTES, gameTable.notes);

        // Inserting Row
        long user_Id = db.insert(GameTable.TABLE, null, values);

        db.close(); // Closing database connection
        return (int) user_Id;
    }

    public final void delete(int user_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(GameTable.TABLE, GameTable.KEY_ID + "= ?", new String[]{String.valueOf(user_Id)});
        db.close(); // Closing database connection
    }

    public final void update(GameTable gameTable) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Get the Date object from the game, and convert it to a day/month/year String, by formatting it with a SimpleDateFormat object
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(gameTable.dateAdded);

        ContentValues values = new ContentValues();
        values.put(GameTable.KEY_TITLE, gameTable.title);
        values.put(GameTable.KEY_PLATFORM, gameTable.platform);
        values.put(GameTable.KEY_DATE, dateString);
        values.put(GameTable.KEY_STATUS, gameTable.gameStatus);
        values.put(GameTable.KEY_NOTES, gameTable.notes);

        db.update(GameTable.TABLE, values, GameTable.KEY_ID + "= ?", new String[]{String.valueOf(gameTable.id)});
        db.close(); // Closing database connection
    }

    public final List<Integer> getGameIDs() // Get list of all Game-IDs in the database
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  " +
                GameTable.KEY_ID +
                " FROM " + GameTable.TABLE;

        List<Integer> gameIDs = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                gameIDs.add(cursor.getInt(cursor.getColumnIndex(GameTable.KEY_ID)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return gameIDs;
    }

    public final ArrayList<HashMap<String, String>> getGames() // Get all games
    {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  " +
                GameTable.KEY_ID + ',' +
                GameTable.KEY_TITLE + ',' +
                GameTable.KEY_PLATFORM + ',' +
                GameTable.KEY_DATE + ',' +
                GameTable.KEY_STATUS + ',' +
                GameTable.KEY_NOTES +
                " FROM " + GameTable.TABLE;

        //User user = new User();
        ArrayList<HashMap<String, String>> gameList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> game = new HashMap<>();
                game.put("id", cursor.getString(cursor.getColumnIndex(GameTable.KEY_ID)));
                game.put("title", cursor.getString(cursor.getColumnIndex(GameTable.KEY_TITLE)));
                game.put("platform", cursor.getString(cursor.getColumnIndex(GameTable.KEY_PLATFORM)));
                game.put("date", cursor.getString(cursor.getColumnIndex(GameTable.KEY_DATE)));
                game.put("status", cursor.getString(cursor.getColumnIndex(GameTable.KEY_STATUS)));
                game.put("notes", cursor.getString(cursor.getColumnIndex(GameTable.KEY_NOTES)));
                gameList.add(game);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return gameList;
    }

    public final GameTable getGameById(int Id) // Get game data by ID
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  " +
                GameTable.KEY_ID + ',' +
                GameTable.KEY_TITLE + ',' +
                GameTable.KEY_PLATFORM + ',' +
                GameTable.KEY_DATE + ',' +
                GameTable.KEY_STATUS + ',' +
                GameTable.KEY_NOTES +
                " FROM " + GameTable.TABLE +
                " WHERE " + GameTable.KEY_ID + "=?";

        GameTable game = new GameTable();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                game.id = (long) cursor.getInt(cursor.getColumnIndex(GameTable.KEY_ID));
                game.title = cursor.getString(cursor.getColumnIndex(GameTable.KEY_TITLE));
                game.platform = cursor.getString(cursor.getColumnIndex(GameTable.KEY_PLATFORM));
                game.dateAdded = cursor.getString(cursor.getColumnIndex(GameTable.KEY_DATE));
                game.gameStatus = cursor.getString(cursor.getColumnIndex(GameTable.KEY_STATUS));
                game.notes = cursor.getString(cursor.getColumnIndex(GameTable.KEY_NOTES));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return game;
    }

}
