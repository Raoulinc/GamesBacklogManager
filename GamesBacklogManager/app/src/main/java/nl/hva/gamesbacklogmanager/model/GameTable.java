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
    private long id;
    private String title;
    private String platform;
    private Date dateAdded;
    private String gameStatus;
    private String notes = "";
}
