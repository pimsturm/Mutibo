package mutiboclient.moviesets.org.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MovieTable {
    // Database table
    public static final String TABLE_MOVIE = "movie";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MOVIE_TITLE = "movie_title";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MOVIE
            + "("
            + COLUMN_ID + " integer primary key, "
            + COLUMN_MOVIE_TITLE + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(MovieSetTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(database);
    }

}
