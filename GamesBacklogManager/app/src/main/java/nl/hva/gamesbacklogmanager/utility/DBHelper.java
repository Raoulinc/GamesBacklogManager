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

import nl.hva.gamesbacklogmanager.model.Game;


class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 5;

    // User Name
    private static final String DATABASE_NAME = "Game.db";
    private static final int num_default_entries = 13;

    DBHelper(Context context) {
        super(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
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
        String curDate = DBHelper.getSimpleCurrentDate();

        for (int i = 1; i < DBHelper.num_default_entries; i++) {
            ContentValues values = new ContentValues();
            values.put(Game.KEY_TITLE, "Test" + i);
            values.put(Game.KEY_PLATFORM, "PC");
            values.put(Game.KEY_DATE, curDate);
            values.put(Game.KEY_STATUS, "Stalled");
            values.put(Game.KEY_NOTES, "I should stop playing");

            // Inserting Row
            db.insert(Game.TABLE, null, values);
        }
    }

    private static String getSimpleCurrentDate() {
        String curDateString = null;
        //formatter that will convert dates into the day-month-year format
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        //Today's date, but with time included, which we don't want
        Date today = new Date();
        //format.format returns a string
        curDateString = format.format(today);
        return curDateString;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Game.TABLE);

        // Create tables again
        onCreate(db);

    }

}


