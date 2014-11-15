package mutiboclient.moviesets.org.mutibo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mutiboclient.moviesets.org.service.MovieSvc;
import mutiboclient.moviesets.org.service.MovieSvcApi;


public class Movies extends Activity {

    ArrayList<String> movieList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_listview);


        movieList.add("Movie 1");
        movieList.add("Movie 2");

        ListView myList=(ListView)findViewById(android.R.id.list);
        myList.setTextFilterEnabled(true);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String selectedItem = ((TextView) view.findViewById(R.id.movie_listview_custom_row_title_textView)).getText().toString();
                Toast.makeText(getApplicationContext(),
                        selectedItem, Toast.LENGTH_SHORT).show();
            }

        });
        new HttpGetTask().execute();
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

    public void test(View view) {
    }

    private class HttpGetTask extends AsyncTask<Void, Void, List<String>> {
        MovieSvcApi movieSvc = MovieSvc.init("http://10.0.2.2:8080", "user", "pass");
        private static final String TAG = "HttpGetTask";

        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                Collection<Movie> movies = movieSvc.getMovieList();
                List<String> titles = new ArrayList<String>();
                for (Movie movie : movies) {
                    titles.add(movie.getMovieTitle());
                }
                return titles;
            } catch (Exception e){
                Log.e(TAG, "Error invoking callable in AsyncTask HttpGetTask", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            ListView myList=(ListView)findViewById(android.R.id.list);
            myList.setAdapter(new ArrayAdapter<String>(Movies.this,  R.layout.movie_listview_custom_row,
                    R.id.movie_listview_custom_row_title_textView, result));

        }
    }
}
