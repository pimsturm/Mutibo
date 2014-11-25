package mutiboclient.moviesets.org.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import mutiboclient.moviesets.org.data.MovieTable;
import mutiboclient.moviesets.org.data.MutiboDatabase;
import mutiboclient.moviesets.org.data.MovieSetTable;
import mutiboclient.moviesets.org.data.RatingTable;
import mutiboclient.moviesets.org.util.RandomInteger;

public class MutiboProvider extends ContentProvider {

    private MutiboDatabase mDB;
    private static final String AUTHORITY = "mutiboclient.moviesets.org.contentprovider.MutiboProvider";
    public static final int MOVIESETS = 100;
    public static final int MOVIESETS_ID = 110;
    public static final int MOVIES = 200;
    public static final int MOVIES_ID = 210;
    public static final int RATING = 300;
    public static final int RATING_MOVIESETS = 400;

    private static final String MOVIESETS_BASE_PATH = "moviesets";
    private static final String MOVIES_BASE_PATH = "movies";
    private static final String RATING_BASE_PATH = "rating";
    private static final String RATING_MOVIESETS_BASE_PATH = "ratingmoviesets";
    public static final Uri CONTENT_MOVIESET_URI = Uri.parse("content://" + AUTHORITY
            + "/" + MOVIESETS_BASE_PATH);
    public static final Uri CONTENT_MOVIE_URI = Uri.parse("content://" + AUTHORITY
            + "/" + MOVIES_BASE_PATH);
    public static final Uri CONTENT_RATING_URI = Uri.parse("content://" + AUTHORITY
            + "/" + RATING_BASE_PATH);
    public static final Uri CONTENT_RATING_MOVIESETS_URI = Uri.parse("content://" + AUTHORITY
            + "/" + RATING_MOVIESETS_BASE_PATH);

    private static final String TAG = "MutiboProvider";

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, MOVIESETS_BASE_PATH, MOVIESETS);
        sURIMatcher.addURI(AUTHORITY, MOVIESETS_BASE_PATH + "/#", MOVIESETS_ID);
        sURIMatcher.addURI(AUTHORITY, MOVIES_BASE_PATH, MOVIES);
        sURIMatcher.addURI(AUTHORITY, MOVIES_BASE_PATH + "/#", MOVIES_ID);
        sURIMatcher.addURI(AUTHORITY, RATING_BASE_PATH, RATING);
        sURIMatcher.addURI(AUTHORITY, RATING_MOVIESETS_BASE_PATH, RATING_MOVIESETS);
    }

    @Override
    public boolean onCreate() {
        mDB = new MutiboDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        android.util.Log.d(TAG, "query");

        String fieldList = getFieldList(projection);
        String queryString =
                "select " +
                        fieldList +
                        " from " + MovieSetTable.TABLE_MOVIESET +
                        " join " + MovieTable.TABLE_MOVIE + " M1 on " + MovieSetTable.TABLE_MOVIESET + "." + MovieSetTable.COLUMN_MOVIE1 + " = M1." + MovieTable.COLUMN_ID +
                        " join " + MovieTable.TABLE_MOVIE + " M2 on " + MovieSetTable.TABLE_MOVIESET + "." + MovieSetTable.COLUMN_MOVIE2 + " = M2." + MovieTable.COLUMN_ID +
                        " join " + MovieTable.TABLE_MOVIE + " M3 on " + MovieSetTable.TABLE_MOVIESET + "." + MovieSetTable.COLUMN_MOVIE3 + " = M3." + MovieTable.COLUMN_ID +
                        " join " + MovieTable.TABLE_MOVIE + " M4 on " + MovieSetTable.TABLE_MOVIESET + "." + MovieSetTable.COLUMN_MOVIE4 + " = M4." + MovieTable.COLUMN_ID;

        Cursor cursor;
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case MOVIESETS_ID:
                queryString = queryString +
                        " where " + MovieSetTable.TABLE_MOVIESET + "." + MovieSetTable.COLUMN_ID + " = ?";
                cursor = mDB.getReadableDatabase().
                        rawQuery(queryString, new String[]{uri.getLastPathSegment()});
                android.util.Log.d(TAG, "Moviesets_id: " + cursor.getCount());
                break;
            case MOVIESETS:
                String[] movieSetIds = randomMovieSet();
                queryString = queryString +
                        " where " + MovieSetTable.TABLE_MOVIESET + "." + MovieSetTable.COLUMN_ID +
                        " in (" + makePlaceholders(movieSetIds.length) + ")";
                android.util.Log.d(TAG, "movieSetIds.length: " + movieSetIds.length);
                android.util.Log.d(TAG, queryString);
                cursor = mDB.getReadableDatabase().
                        rawQuery(queryString, movieSetIds);
                android.util.Log.d(TAG, "Moviesets: " + cursor.getCount());

                break;
            case RATING_MOVIESETS:
                android.util.Log.d(TAG, queryString);
                queryString = queryString +
                        " where " + MovieSetTable.TABLE_MOVIESET + "." + MovieSetTable.COLUMN_AVG_RATING +
                        " > 0 order by " + MovieSetTable.COLUMN_AVG_RATING;
                android.util.Log.d(TAG, queryString);
                cursor = mDB.getReadableDatabase().
                        rawQuery(queryString, null);
                android.util.Log.d(TAG, "Rating Moviesets: " + cursor.getCount());
                break;
            case RATING:
                cursor = mDB.getReadableDatabase().
                        rawQuery("select " +
                                RatingTable.TABLE_RATING + "." + RatingTable.COLUMN_ID + "," +
                                RatingTable.TABLE_RATING + "." + RatingTable.COLUMN_USER_ID + "," +
                                RatingTable.TABLE_RATING + "." + RatingTable.COLUMN_MOVIESET_ID + "," +
                                RatingTable.TABLE_RATING + "." + RatingTable.COLUMN_RATING +
                                " from " + RatingTable.TABLE_RATING, null);
                android.util.Log.d(TAG, "Ratings: " + cursor.getCount());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    // Build the field list for the query
    private String getFieldList(String[] projection) {
        String fieldList = "";

        if (projection != null) {
            checkColumns(projection);
            for (String field : projection) {
                fieldList = fieldList + MovieSetTable.TABLE_MOVIESET + "." + field + ", ";
            }
            if (fieldList.length() > 0) {
                // Remove last comma
                fieldList = fieldList.substring(0, fieldList.length() - 2);
                Log.d(TAG, "Projection to fieldList:" + fieldList);
            }
        } else {
            fieldList =
                    MovieSetTable.TABLE_MOVIESET + "." + MovieSetTable.COLUMN_ID + "," +
                    "M1." + MovieTable.COLUMN_MOVIE_TITLE + " as movie1, " +
                    "M2." + MovieTable.COLUMN_MOVIE_TITLE + " as movie2, " +
                    "M3." + MovieTable.COLUMN_MOVIE_TITLE + " as movie3, " +
                    "M4." + MovieTable.COLUMN_MOVIE_TITLE + " as movie4, " +
                    MovieSetTable.TABLE_MOVIESET + "." + MovieSetTable.COLUMN_EXPLANATION + "," +
                    MovieSetTable.TABLE_MOVIESET + "." + MovieSetTable.COLUMN_HINT + "," +
                    MovieSetTable.TABLE_MOVIESET + "." + MovieSetTable.COLUMN_CORRECT_ANSWER + "," +
                    MovieSetTable.TABLE_MOVIESET + "." + MovieSetTable.COLUMN_GIVEN_ANSWER + "," +
                    MovieSetTable.TABLE_MOVIESET + "." + MovieSetTable.COLUMN_RATING + "," +
                    MovieSetTable.TABLE_MOVIESET + "." + MovieSetTable.COLUMN_AVG_RATING;
        }

        return fieldList;
    }

    // Select (at most) 10 random movie sets
    private String[] randomMovieSet() {
        // Create a cursor with the id's of all movie sets.
        Cursor idList = mDB.getReadableDatabase().
                rawQuery("select " + MovieSetTable.COLUMN_ID +
                        " from " + MovieSetTable.TABLE_MOVIESET, null);

        List<String> result = new ArrayList<String>();

        if (idList.getCount() > 0) {
            // Generate 10 random int's in the range 0 ... idList.getCount() - 1
            int[] randomIds = RandomInteger.generate(Math.min(10, idList.getCount()), idList.getCount());
            for (int idx = 0; idx < randomIds.length; idx++) {
                result.add(Integer.toString(randomIds[idx]));
            }

        }
        return result.toArray(new String[result.size()]);
    }

    private String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    private void checkColumns(String[] projection) {
        String[] available = {MovieSetTable.COLUMN_ID,
                MovieSetTable.COLUMN_RATING, MovieSetTable.COLUMN_AVG_RATING,
                MovieSetTable.COLUMN_CORRECT_ANSWER};
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        long id;
        Uri resultUri;
        switch (uriType) {
            case MOVIESETS:
                id = sqlDB.insert(MovieSetTable.TABLE_MOVIESET, null, values);
                resultUri = Uri.parse(MOVIESETS_BASE_PATH + "/" + id);
                break;
            case MOVIES:
                id = sqlDB.insert(MovieTable.TABLE_MOVIE, null, values);
                resultUri = Uri.parse(MOVIES_BASE_PATH + "/" + id);
                break;
            case RATING:
                id = sqlDB.insert(RatingTable.TABLE_RATING, null, values);
                resultUri = Uri.parse(RATING_BASE_PATH + "/" + id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        int rowsDeleted;
        switch (uriType) {
            case MOVIESETS:
                rowsDeleted = sqlDB.delete(MovieSetTable.TABLE_MOVIESET, selection,
                        selectionArgs);
                break;
            case MOVIES:
                rowsDeleted = sqlDB.delete(MovieTable.TABLE_MOVIE, selection,
                        selectionArgs);
                break;
            case MOVIESETS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(MovieSetTable.TABLE_MOVIESET,
                            MovieSetTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(MovieSetTable.TABLE_MOVIESET,
                            MovieSetTable.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        int rowsUpdated;
        String id;
        switch (uriType) {
            case MOVIESETS:
                rowsUpdated = sqlDB.update(MovieSetTable.TABLE_MOVIESET,
                        values,
                        selection,
                        selectionArgs);
                break;
            case MOVIES:
                rowsUpdated = sqlDB.update(MovieTable.TABLE_MOVIE,
                        values,
                        selection,
                        selectionArgs);
                break;
            case RATING:
                rowsUpdated = sqlDB.update(RatingTable.TABLE_RATING,
                        values,
                        selection,
                        selectionArgs);
                break;
            case MOVIESETS_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(MovieSetTable.TABLE_MOVIESET,
                            values,
                            MovieSetTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(MovieSetTable.TABLE_MOVIESET,
                            values,
                            MovieSetTable.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            case MOVIES_ID:
                id = uri.getLastPathSegment();
                rowsUpdated = sqlDB.update(MovieTable.TABLE_MOVIE,
                        values,
                        MovieTable.COLUMN_ID + "=" + id,
                        null);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

}
