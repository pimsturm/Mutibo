package mutiboclient.moviesets.org.service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

import mutiboclient.moviesets.org.contentprovider.MutiboProvider;
import mutiboclient.moviesets.org.data.MovieSetTable;
import mutiboclient.moviesets.org.data.MovieTable;
import mutiboclient.moviesets.org.data.RatingTable;
import mutiboclient.moviesets.org.mutibo.Movie;
import mutiboclient.moviesets.org.mutibo.MovieSet;
import mutiboclient.moviesets.org.mutibo.Rating;

public class MutiboSyncService extends Service {
    private static final String TAG = "MutiboSyncService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new MutiboSyncTask().execute();
        return Service.START_FLAG_REDELIVERY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class MutiboSyncTask extends AsyncTask<Void, Void, Boolean> {

        private static final String TAG = "MutiboSyncService$MutiboSyncTask";
        MovieSvcApi movieSvc = MovieSvc.init("http://10.0.2.2:8080", "user", "pass");
        MovieSetSvcApi movieSetSvc = MovieSetSvc.init("http://10.0.2.2:8080", "user", "pass");
        RatingSvcApi ratingSvc = RatingSvc.init("http://10.0.2.2:8080", "user", "pass");

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean success = false;
            int rowsUpdated;
            ContentValues movieValues = new ContentValues();
            ContentValues movieSetValues = new ContentValues();
            try {
                Log.d(TAG, "MovieSetList");
                Collection<MovieSet> movieSets = movieSetSvc.getMovieSetList();
                Log.d(TAG, "MovieSetList count:" + movieSets.size());
                // List of all movie id's used in the movie sets
                ArrayList<Long> movieUsedId = new ArrayList<Long>();
                for (MovieSet movieSet : movieSets) {
                    movieSetValues.put(MovieSetTable.COLUMN_ID, movieSet.getId());
                    movieSetValues.put(MovieSetTable.COLUMN_MOVIE1, movieSet.getMovie1Id());
                    movieSetValues.put(MovieSetTable.COLUMN_MOVIE2, movieSet.getMovie2Id());
                    movieSetValues.put(MovieSetTable.COLUMN_MOVIE3, movieSet.getMovie3Id());
                    movieSetValues.put(MovieSetTable.COLUMN_MOVIE4, movieSet.getMovie4Id());
                    movieSetValues.put(MovieSetTable.COLUMN_CORRECT_ANSWER, movieSet.getCorrectAnswer());
                    movieSetValues.put(MovieSetTable.COLUMN_EXPLANATION, movieSet.getExplanation());
                    movieSetValues.put(MovieSetTable.COLUMN_HINT, movieSet.getHint());
                    movieSetValues.put(MovieSetTable.COLUMN_RATING, movieSet.getRating());
                    movieSetValues.put(MovieSetTable.COLUMN_GIVEN_ANSWER, movieSet.getGivenAnswer());

                    movieUsedId.add(movieSet.getMovie1Id());
                    movieUsedId.add(movieSet.getMovie2Id());
                    movieUsedId.add(movieSet.getMovie3Id());
                    movieUsedId.add(movieSet.getMovie4Id());

                    Log.d(TAG, "MovieSet id: " + movieSet.getId());
                    rowsUpdated = getContentResolver().update(Uri.parse(MutiboProvider.CONTENT_MOVIESET_URI + "/" + movieSet.getId()), movieSetValues, null, null);
                    Log.d(TAG, "MovieSet updated: " + rowsUpdated);
                    if (rowsUpdated == 0) {
                        Uri result = getContentResolver().insert(MutiboProvider.CONTENT_MOVIESET_URI, movieSetValues);
                        Log.d(TAG, "MovieSet inserted: " + result);
                    }
                }

                Log.d(TAG, "MovieList");
                Collection<Movie> movies = movieSvc.getUsedMovie(movieUsedId);
                Log.d(TAG, "MovieList count:" + movies.size());
                for (Movie movie : movies) {
                    movieValues.put(MovieTable.COLUMN_ID, movie.getId());
                    movieValues.put(MovieTable.COLUMN_MOVIE_TITLE, movie.getMovieTitle());
                    rowsUpdated = getContentResolver().update(Uri.parse(MutiboProvider.CONTENT_MOVIE_URI + "/" + movie.getId()), movieValues, null, null);
                    Log.d(TAG, "Movies updated: " + rowsUpdated);
                    if (rowsUpdated == 0) {
                        Uri result = getContentResolver().insert(MutiboProvider.CONTENT_MOVIE_URI, movieValues);
                        Log.d(TAG, "Movies inserted: " + result);
                    }
                }

                Log.d(TAG, "Rating");
                Cursor cursor = getContentResolver().query(MutiboProvider.CONTENT_RATING_URI, null, null, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    android.util.Log.d(TAG, "cursor count: " + cursor.getCount());
                    cursor.moveToFirst();
                    ArrayList<Rating> ratings = new ArrayList<Rating>();
                    do {
                        Rating rating = new Rating();
                        rating.setId(cursor.getLong(cursor.getColumnIndex(RatingTable.COLUMN_ID)));
                        rating.setUserId(cursor.getString(cursor.getColumnIndex(RatingTable.COLUMN_USER_ID)));
                        rating.setMovieSetId(cursor.getLong(cursor.getColumnIndex(RatingTable.COLUMN_MOVIESET_ID)));
                        rating.setRating(cursor.getInt(cursor.getColumnIndex(RatingTable.COLUMN_RATING)));
                        ratings.add(rating);
                    } while (cursor.moveToNext());
                    ratingSvc.addRating(ratings);
                }


                success = true;
            } catch (Exception e){
                Log.e(TAG, "Error invoking callable in AsyncTask HttpGetTask", e);
            }
            return success;
        }
        @Override
        protected void onPostExecute(Boolean success) {
            String resultText;
            if (success) {
                resultText = "Synchronisation completed";
            } else {
                resultText = "Synchronisation failed";
            }
            Toast.makeText(MutiboSyncService.this, resultText, Toast.LENGTH_LONG).show();
        }


    }
}
