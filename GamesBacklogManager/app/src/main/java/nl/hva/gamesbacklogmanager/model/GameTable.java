package nl.hva.gamesbacklogmanager.model;

import java.util.Date;

/**
 * Created by Raoul on 18-4-2016.
 */
public class GameTable {

    // Labels table name
    public static final String TABLE = "GameList";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_PLATFORM = "platform";
    public static final String KEY_DATE = "date";
    public static final String KEY_STATUS = "status";
    public static final String KEY_NOTES = "notes";

    // property help us to keep data
    public long id;
    public String title;
    public String platform;
    public String dateAdded;
    public String gameStatus;
    public String notes = "";
}
