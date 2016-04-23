package nl.hva.gamesbacklogmanager.utility;

/**
 * Created by Raoul on 3-2-2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nl.hva.gamesbacklogmanager.model.Game;


class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 2;

    // User Name
    private static final String DATABASE_NAME = "Game.db";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public final void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_GAME = "CREATE TABLE " + Game.TABLE + '('
                + Game.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Game.KEY_TITLE + " TEXT, "
                + Game.KEY_PLATFORM + " TEXT, "
                + Game.KEY_DATE + " TEXT, "
                + Game.KEY_STATUS + " TEXT, "
                + Game.KEY_NOTES + " TEXT )";

        db.execSQL(CREATE_TABLE_GAME);
    }

    @Override
    public final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Game.TABLE);

        // Create tables again
        onCreate(db);

    }

}


