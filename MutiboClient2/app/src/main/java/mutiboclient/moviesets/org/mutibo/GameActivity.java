package mutiboclient.moviesets.org.mutibo;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.util.Log;

import java.util.ArrayList;

import mutiboclient.moviesets.org.contentprovider.MutiboProvider;
import mutiboclient.moviesets.org.data.MovieSetTable;

enum GameInfoStatus {NONE, HINT, EXPLANATION}

public class GameActivity extends Activity
    implements LoaderManager.LoaderCallbacks<Cursor>{

    private ArrayList<MovieSet> movieSets;
    private GameStatus gameStatus;
    private GameInfoStatus gameInfoStatus;
    private static final String TAG = "GameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final Button btnGameSubmit = (Button) findViewById(R.id.btnGameSubmit);

        btnGameSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processAnswer();
                refreshStatus();
                refreshMovieSet();
            }
        });

        final Button btnGameNext = (Button) findViewById(R.id.btnNext);

        btnGameNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
                refreshStatus();
                refreshMovieSet();
            }
        });

        final Button btnGameHint = (Button) findViewById(R.id.btnGameHint);
        btnGameHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processHint();
                refreshStatus();
                refreshMovieSet();
            }
        });

        final RatingBar rbGameRating = (RatingBar) findViewById(R.id.rbGameRating);
        rbGameRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                MovieSet movieSet = getCurrentMovieSet();
                movieSet.setRating((int) ratingBar.getRating());
                Log.v(TAG, Float.toString(ratingBar.getRating()));

            }
        });

        loadGameData();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshStatus() {
        final TextView txtUserName = (TextView) findViewById(R.id.txtUsername);
        txtUserName.setText(getString(R.string.game_user_name) + gameStatus.getUserName());

        final TextView txtScore = (TextView) findViewById(R.id.txtScore);
        txtScore.setText(getString(R.string.game_score) + gameStatus.getScore());

        final TextView txtHints = (TextView) findViewById(R.id.txtHints);
        txtHints.setText(getString(R.string.game_hints) + gameStatus.getAvailableHints());

        final TextView txtWrong = (TextView) findViewById(R.id.txtWrong);
        txtWrong.setText(new StringBuilder().append(getString(R.string.game_wrong)).append(gameStatus.getWrongAnswers()));

    }

    private void refreshMovieSet(){
        MovieSet movieSet = getCurrentMovieSet();
        int[] radioButtons = {R.id.rbtnGameOption1, R.id.rbtnGameOption2,
                R.id.rbtnGameOption3, R.id.rbtnGameOption4};

        final RadioGroup groupGameOptions = (RadioGroup) findViewById(R.id.rgGameOptions);
        groupGameOptions.clearCheck();

        int radioButtonNumber = 0;
        for (String movieTitle : movieSet.getMovieTitles()) {
            final RadioButton radioButton = (RadioButton) findViewById(radioButtons[radioButtonNumber]);
            radioButton.setText(movieTitle);
            radioButton.setBackgroundColor(groupGameOptions.getDrawingCacheBackgroundColor());
            radioButtonNumber++;
        }

        final RatingBar rbGameRating = (RatingBar) findViewById(R.id.rbGameRating);
        final Button btnSubmit = (Button) findViewById(R.id.btnGameSubmit);
        int givenAnswer = movieSet.getGivenAnswer();
        if (givenAnswer > -1) {
            final RadioButton selectedButton = (RadioButton) findViewById(radioButtons[givenAnswer]);
            selectedButton.setChecked(true);
            if (movieSet.isCorrectAnswer()) {
                selectedButton.setBackgroundColor(Color.GREEN);
            } else {
                selectedButton.setBackgroundColor(Color.RED);
            }

            btnSubmit.setEnabled(false);
            rbGameRating.setEnabled(true);
        } else {
            btnSubmit.setEnabled(true);
            rbGameRating.setEnabled(false);
        }

        rbGameRating.setRating(movieSet.getRating());

        final TextView txtGameInfo = (TextView) findViewById(R.id.txtGameInfo);
        switch (gameInfoStatus) {
            case NONE:
                txtGameInfo.setText("");
                break;
            case HINT:
                txtGameInfo.setText(movieSet.getHint());
                break;
            case EXPLANATION:
                txtGameInfo.setText(movieSet.getExplanation());
                break;
        }

        final Button btnGameHint = (Button) findViewById(R.id.btnGameHint);
        btnGameHint.setEnabled(gameInfoStatus == GameInfoStatus.NONE &&
            gameStatus.getAvailableHints() > 0);


    }

    private void nextQuestion() {
        // Move to the next question
        gameStatus.nextQuestion();
        // Reset the status of the infobox at the bottom
        gameInfoStatus = GameInfoStatus.NONE;

        MovieSet movieSet = getCurrentMovieSet();
        if (movieSet.getAskedHint()) {
            gameInfoStatus = GameInfoStatus.HINT;
        }
        if (movieSet.getGivenAnswer() == movieSet.getCorrectAnswer()){
            gameInfoStatus = GameInfoStatus.EXPLANATION;
        }

    }

    private void processHint() {
        MovieSet movieSet = getCurrentMovieSet();
        movieSet.setAskedHint(true);
        gameInfoStatus = GameInfoStatus.HINT;
        gameStatus.setAvailableHints(gameStatus.getAvailableHints() - 1);
    }

    private int getSelectedAnswer() {
        final RadioGroup groupGameOptions = (RadioGroup) findViewById(R.id.rgGameOptions);

        int selectedOption = groupGameOptions.getCheckedRadioButtonId();

        if (selectedOption > -1) {
            View radioButton = groupGameOptions.findViewById(selectedOption);
            return groupGameOptions.indexOfChild(radioButton);
        }
        else {
            return -1;
        }

    }

    private void processAnswer() {
        MovieSet movieSet = getCurrentMovieSet();
        movieSet.setGivenAnswer(getSelectedAnswer());

        if (movieSet.isCorrectAnswer()) {
            gameStatus.setScore(gameStatus.getScore() + 1);
        } else {
            gameStatus.setWrongAnswers(gameStatus.getWrongAnswers() + 1);
        }

        gameInfoStatus = GameInfoStatus.EXPLANATION;

    }

    private MovieSet getCurrentMovieSet() {
        return movieSets.get(gameStatus.getCurrentQuestion()- 1);
    }

    private void loadGameData() {
        android.util.Log.d(TAG, "loadGameData");
        getLoaderManager().initLoader(0, null, this);
        gameInfoStatus = GameInfoStatus.NONE;

        gameStatus = new GameStatus();
        gameStatus.setUserName("Pim");
        gameStatus.setAvailableHints(3);
        gameStatus.setCurrentQuestion(1);
        gameStatus.setHighScore(10);
        gameStatus.setScore(0);
        gameStatus.setWrongAnswers(0);

    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        android.util.Log.d(TAG, "onCreateLoader");
        return new CursorLoader(this,
                MutiboProvider.CONTENT_MOVIESET_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        android.util.Log.d(TAG, "onLoadFinished");
        if (cursor != null && cursor.getCount() > 0) {
            android.util.Log.d(TAG, "cursor count: " + cursor.getCount());
            cursor.moveToFirst();
            int questionNr = 0;
            movieSets = new ArrayList<MovieSet>();
            do {
                questionNr++;
                movieSets.add(loadGameDataFromCursor(cursor, questionNr));
            } while (cursor.moveToNext());
            gameStatus.setQuestionCount(movieSets.size());
            android.util.Log.d(TAG, "Number of movie sets: " + movieSets.size());

            refreshStatus();

            refreshMovieSet();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // data is not available anymore, delete reference

    }

    private MovieSet loadGameDataFromCursor(Cursor cursor, int questionNr) {
        android.util.Log.d(TAG, "loadGameDataFromCursor");
        MovieSet movieSet = new MovieSet();

        movieSet.setId(questionNr);
        movieSet.setRating(cursor.getInt(cursor.getColumnIndex(MovieSetTable.COLUMN_RATING)));
        movieSet.setCorrectAnswer(cursor.getInt((cursor.getColumnIndex(MovieSetTable.COLUMN_CORRECT_ANSWER))));
        movieSet.setHint(cursor.getString(cursor.getColumnIndex(MovieSetTable.COLUMN_HINT)));
        movieSet.setExplanation(cursor.getString(cursor.getColumnIndex(MovieSetTable.COLUMN_EXPLANATION)));

        movieSet.addMovieTitle(cursor.getString(cursor.getColumnIndex("movie1")));
        movieSet.addMovieTitle(cursor.getString(cursor.getColumnIndex("movie2")));
        movieSet.addMovieTitle(cursor.getString(cursor.getColumnIndex("movie3")));
        movieSet.addMovieTitle(cursor.getString(cursor.getColumnIndex("movie4")));

        return movieSet;

    }
}
