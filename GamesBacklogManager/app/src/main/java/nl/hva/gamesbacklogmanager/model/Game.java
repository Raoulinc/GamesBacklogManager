package nl.hva.gamesbacklogmanager.model;

import java.io.Serializable;

/**
 * Created by Raoul on 15-4-2016.
 */
public class Game implements Serializable {

    // Labels table name
    public static final String TABLE = "games";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_PLATFORM = "platform";
    public static final String KEY_DATE = "date";
    public static final String KEY_STATUS = "status";
    public static final String KEY_NOTES = "notes";

    // property help us to keep data
    public int id;
    public String title;
    public String platform;
    public String dateAdded; // String, since you cannot save date/time values in SQLite
    public String gameStatus;
    public String notes;

    public Game() {
    }

    public Game(int id, String title, String platform, String dateAdded, String gameStatus, String notes) {
        this.id = id;
        this.title = title;
        this.platform = platform;
        this.dateAdded = dateAdded;
        this.gameStatus = gameStatus;
        this.notes = notes;
    }

    public final long getId() {
        return (long) id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final String getPlatform() {
        return platform;
    }

    public final void setPlatform(String platform) {
        this.platform = platform;
    }

    public final String getDateAdded() {
        return dateAdded;
    }

    public final void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public final String getGameStatus() {
        return gameStatus;
    }

    public final void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public final String getNotes() {
        return notes;
    }

    public final void setNotes(String notes) {
        this.notes = notes;
    }
}


