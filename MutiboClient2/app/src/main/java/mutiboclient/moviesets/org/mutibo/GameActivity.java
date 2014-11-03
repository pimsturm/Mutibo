package mutiboclient.moviesets.org.mutibo;

import android.app.Activity;
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

enum GameInfoStatus {NONE, HINT, EXPLANATION}

public class GameActivity extends Activity {

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

        refreshStatus();

        refreshMovieSet();

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
        txtScore.setText(new StringBuilder().append(getString(R.string.game_score)).append(gameStatus.getScore()).toString());

        final TextView txtHints = (TextView) findViewById(R.id.txtHints);
        txtHints.setText(new StringBuilder().append(getString(R.string.game_hints)).append(gameStatus.getAvailableHints()).toString());

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
        gameInfoStatus = GameInfoStatus.NONE;

        gameStatus = new GameStatus();
        gameStatus.setUserName("Pim");
        gameStatus.setAvailableHints(3);
        gameStatus.setCurrentQuestion(1);
        gameStatus.setHighScore(10);
        gameStatus.setScore(0);
        gameStatus.setWrongAnswers(0);


        movieSets = new ArrayList<MovieSet>();
        MovieSet movieSet = new MovieSet();

        movieSet.setId(1L);
        movieSet.addMovieTitle("Hackers");
        movieSet.addMovieTitle("War Games");
        movieSet.addMovieTitle("Tron");
        movieSet.addMovieTitle("Snow White");

        movieSet.setCorrectAnswer(3);

        movieSet.setExplanation("Snow White is the only movie in this set that's not about computer science.");

        movieSet.setHint("Computer science");

        movieSets.add(movieSet);

        movieSet = new MovieSet();
        movieSet.setId(2L);
        movieSet.clearMovieTitles();
        movieSet.addMovieTitle("The Grand Budapest Hotel");
        movieSet.addMovieTitle("Around the World in 80 Days");
        movieSet.addMovieTitle("The Other Guys");
        movieSet.addMovieTitle("Zoolander");

        movieSet.setCorrectAnswer(2);

        movieSet.setExplanation("The actor Owen Wilson had a role in all movies except in The Other Guys.");

        movieSet.setHint("Actor");

        movieSets.add(movieSet);

        gameStatus.setQuestionCount(movieSets.size());

    }
}
