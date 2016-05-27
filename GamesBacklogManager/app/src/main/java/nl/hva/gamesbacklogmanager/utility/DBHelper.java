package nl.hva.gamesbacklogmanager.utility;

/**
 * Created by Raoul on 3-2-2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.model.Game;


class DBHelper extends SQLiteOpenHelper {
    // Version number to upgrade database version
    // each time if you Add, Edit table, you need to change the
    // version number.

    private static final int DATABASE_VERSION = 10;

    // Database Name
    private static final String DATABASE_NAME = "Game.db";

    private final Context context;

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private static String getSimpleCurrentDate() {
        //formatter that will convert dates into the day-month-year format
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        //Today's date, but with time included, which we don't want
        Date today = new Date();
        //format.format returns a string
        return format.format(today);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_GAME = "CREATE TABLE " + Game.TABLE + '('
                + Game.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Game.KEY_TITLE + " TEXT, "
                + Game.KEY_PLATFORM + " TEXT, "
                + Game.KEY_DATE + " TEXT, "
                + Game.KEY_STATUS + " TEXT, "
                + Game.KEY_NOTES + " TEXT )";

        db.execSQL(CREATE_TABLE_GAME);

        //Get the current date in numbered day-month-year format
        String curDate = getSimpleCurrentDate();

        // Fill database with Dummy Games

        String[] titles = context.getResources().getStringArray(R.array.game_titles);

        for (int i = 1; i < titles.length; i++) {
            ContentValues values = new ContentValues();

            values.put(Game.KEY_TITLE, titles[i]);
            values.put(Game.KEY_PLATFORM, "PC");
            values.put(Game.KEY_DATE, curDate);
            values.put(Game.KEY_STATUS, "Stalled");
            values.put(Game.KEY_NOTES, "I should stop playing");

            // Inserting Row
            db.insert(Game.TABLE, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Game.TABLE);

        // Create tables again
        onCreate(db);

    }

}


