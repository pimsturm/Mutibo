package mutiboclient.moviesets.org.mutibo;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mutiboclient.moviesets.org.contentprovider.MutiboProvider;
import mutiboclient.moviesets.org.data.MovieSetTable;
import mutiboclient.moviesets.org.service.MovieSvc;
import mutiboclient.moviesets.org.service.MovieSvcApi;


public class Movies extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "List ratings";
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_listview);

        ListView myList = (ListView) findViewById(android.R.id.list);
        myList.setTextFilterEnabled(true);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String selectedItem = ((TextView) view.findViewById(R.id.movie_listview_custom_row_title_textView)).getText().toString();
                Toast.makeText(getApplicationContext(),
                        selectedItem, Toast.LENGTH_SHORT).show();
            }

        });

        mAdapter = new SimpleCursorAdapter(this, R.layout.movie_listview_custom_row, null,
                new String[]{MovieSetTable.COLUMN_ID,
                        "movie1",
                        MovieSetTable.COLUMN_AVG_RATING},
                new int[]{R.id.movie_listview_custom_row_KEY_ID_textView,
                        R.id.movie_listview_custom_row_title_textView,
                        R.id.movie_listview_custom_row_creation_time_textView}, 0);

        myList.setAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
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

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(
                this,
                MutiboProvider.CONTENT_RATING_MOVIESETS_URI,
                null,
                null,
                null,
                null);
        return loader;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "Number of ratings:" + cursor.getCount());
        mAdapter.swapCursor(cursor);
        mAdapter.notifyDataSetChanged();
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
