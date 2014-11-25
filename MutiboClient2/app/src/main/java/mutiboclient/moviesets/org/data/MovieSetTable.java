package mutiboclient.moviesets.org.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MovieSetTable {
    // Database table
    public static final String TABLE_MOVIESET = "movieset";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MOVIE1 = "movie1";
    public static final String COLUMN_MOVIE2 = "movie2";
    public static final String COLUMN_MOVIE3 = "movie3";
    public static final String COLUMN_MOVIE4 = "movie4";
    public static final String COLUMN_HINT = "hint";
    public static final String COLUMN_EXPLANATION = "explanation";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_AVG_RATING = "avg_rating";
    public static final String COLUMN_CORRECT_ANSWER = "correct_answer";
    public static final String COLUMN_GIVEN_ANSWER = "given_answer";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MOVIESET
            + "("
            + COLUMN_ID + " integer primary key, "
            + COLUMN_MOVIE1 + " integer not null, "
            + COLUMN_MOVIE2 + " integer not null, "
            + COLUMN_MOVIE3 + " integer not null, "
            + COLUMN_MOVIE4 + " integer not null, "
            + COLUMN_HINT + " text not null, "
            + COLUMN_EXPLANATION + " text not null, "
            + COLUMN_RATING + " integer not null, "
            + COLUMN_AVG_RATING + " integer not null, "
            + COLUMN_CORRECT_ANSWER + " integer not null, "
            + COLUMN_GIVEN_ANSWER + " integer not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(MovieSetTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIESET);
        onCreate(database);
    }
}
