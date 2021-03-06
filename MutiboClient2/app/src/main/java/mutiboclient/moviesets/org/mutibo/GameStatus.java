package mutiboclient.moviesets.org.mutibo;

import android.util.Log;

public class GameStatus {
    private static final String TAG = "GameStatus";

    private String userName;

    private int availableHints = 3;

    private int wrongAnswers = 0;

    private int highScore = 0;

    private int score = 0;

    private int currentQuestion = 1;

    // Number of questions in a game
    private int QuestionCount = 10;

    private Boolean gameEnded = false;

    GameStatus() {}

    public void setAvailableHints(int availableHints) {
        this.availableHints = availableHints;
    }

    public int getAvailableHints() {
        return availableHints;
    }

    public void setQuestionCount(int questionCount) {this.QuestionCount = questionCount;}

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public void nextQuestion() {
        int currentQuestion = getCurrentQuestion();
        if (currentQuestion == this.QuestionCount) {
            setCurrentQuestion(1);
        } else {
            setCurrentQuestion(currentQuestion + 1);
        }
        Log.d(TAG, "Moved to question: " + getCurrentQuestion());
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
        if (this.wrongAnswers > 2) {
            this.setGameEnded(true);
        }
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public Boolean getGameEnded() {return this.gameEnded;}

    public void setGameEnded(Boolean gameEnded) {this.gameEnded = gameEnded;}
}
