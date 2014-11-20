package mutiboclient.moviesets.org.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RatingTable {
    // Database table
    public static final String TABLE_RATING = "rating";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_MOVIESET_ID = "movieset_id";
    public static final String COLUMN_RATING = "rating";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_RATING
            + "("
            + COLUMN_ID + " integer primary key, "
            + COLUMN_USER_ID + " text not null,"
            + COLUMN_MOVIESET_ID + " integer not null,"
            + COLUMN_RATING + " integer not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(MovieSetTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RATING);
        onCreate(database);
    }

}
