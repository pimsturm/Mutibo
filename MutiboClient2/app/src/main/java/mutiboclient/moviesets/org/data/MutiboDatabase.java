package mutiboclient.moviesets.org.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MutiboDatabase extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "mutibo_data";

    public MutiboDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        MovieSetTable.onCreate(db);
        MovieTable.onCreate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MovieSetTable.onUpgrade(db, oldVersion, newVersion);
        MovieTable.onUpgrade(db, oldVersion, newVersion);
    }
}
