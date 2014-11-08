package mutiboclient.moviesets.org.mutibo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import mutiboclient.moviesets.org.contentprovider.MutiboProvider;
import mutiboclient.moviesets.org.data.MovieSetTable;
import mutiboclient.moviesets.org.data.MovieTable;


public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demoData();
        final Button btnStartGame = (Button) findViewById(R.id.btnStartGame);

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGame = new Intent(MainActivity.this, GameActivity.class);
                startActivity(startGame);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private void demoData() {
        android.util.Log.d(TAG, "demoData");
        //Remove old data
        getContentResolver().delete(MutiboProvider.CONTENT_MOVIESET_URI, null, null);
        getContentResolver().delete(MutiboProvider.CONTENT_MOVIE_URI, null, null);

        // First set
        ContentValues values = new ContentValues();
        values.put(MovieSetTable.COLUMN_EXPLANATION, "Snow White is the only movie in this set that's not about computer science.");
        values.put(MovieSetTable.COLUMN_HINT, "Computer science");
        values.put(MovieSetTable.COLUMN_CORRECT_ANSWER, 3);
        values.put(MovieSetTable.COLUMN_GIVEN_ANSWER, -1);
        values.put(MovieSetTable.COLUMN_RATING, 0);

        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieTable.COLUMN_MOVIE_TITLE, "Hackers");
        Uri uri = getContentResolver().insert(MutiboProvider.CONTENT_MOVIE_URI, movieValues);
        android.util.Log.d(TAG, uri.getLastPathSegment());
        values.put(MovieSetTable.COLUMN_MOVIE1, Integer.parseInt(uri.getLastPathSegment()));

        movieValues.clear();
        movieValues.put(MovieTable.COLUMN_MOVIE_TITLE, "War Games");
        uri = getContentResolver().insert(MutiboProvider.CONTENT_MOVIE_URI, movieValues);
        values.put(MovieSetTable.COLUMN_MOVIE2, Integer.parseInt(uri.getLastPathSegment()));

        movieValues.clear();
        movieValues.put(MovieTable.COLUMN_MOVIE_TITLE, "Tron");
        uri = getContentResolver().insert(MutiboProvider.CONTENT_MOVIE_URI, movieValues);
        values.put(MovieSetTable.COLUMN_MOVIE3, Integer.parseInt(uri.getLastPathSegment()));

        movieValues.clear();
        movieValues.put(MovieTable.COLUMN_MOVIE_TITLE, "Snow White");
        uri = getContentResolver().insert(MutiboProvider.CONTENT_MOVIE_URI, movieValues);
        values.put(MovieSetTable.COLUMN_MOVIE4, Integer.parseInt(uri.getLastPathSegment()));

        getContentResolver().insert(MutiboProvider.CONTENT_MOVIESET_URI, values);


        //Second set
        values.clear();
        values.put(MovieSetTable.COLUMN_EXPLANATION, "The actor Owen Wilson had a role in all movies except in The Other Guys");
        values.put(MovieSetTable.COLUMN_HINT, "Actor");
        values.put(MovieSetTable.COLUMN_CORRECT_ANSWER, 2);
        values.put(MovieSetTable.COLUMN_GIVEN_ANSWER, -1);
        values.put(MovieSetTable.COLUMN_RATING, 0);

        movieValues.clear();
        movieValues.put(MovieTable.COLUMN_MOVIE_TITLE, "The Grand Budapest Hotel");
        uri = getContentResolver().insert(MutiboProvider.CONTENT_MOVIE_URI, movieValues);
        values.put(MovieSetTable.COLUMN_MOVIE1, Integer.parseInt(uri.getLastPathSegment()));

        movieValues.clear();
        movieValues.put(MovieTable.COLUMN_MOVIE_TITLE, "Around the World in 80 Days");
        uri = getContentResolver().insert(MutiboProvider.CONTENT_MOVIE_URI, movieValues);
        values.put(MovieSetTable.COLUMN_MOVIE2, Integer.parseInt(uri.getLastPathSegment()));

        movieValues.clear();
        movieValues.put(MovieTable.COLUMN_MOVIE_TITLE, "The Other Guys");
        uri = getContentResolver().insert(MutiboProvider.CONTENT_MOVIE_URI, movieValues);
        values.put(MovieSetTable.COLUMN_MOVIE3, Integer.parseInt(uri.getLastPathSegment()));

        movieValues.clear();
        movieValues.put(MovieTable.COLUMN_MOVIE_TITLE, "Zoolander");
        uri = getContentResolver().insert(MutiboProvider.CONTENT_MOVIE_URI, movieValues);
        values.put(MovieSetTable.COLUMN_MOVIE4, Integer.parseInt(uri.getLastPathSegment()));

        getContentResolver().insert(MutiboProvider.CONTENT_MOVIESET_URI, values);

    }
}
