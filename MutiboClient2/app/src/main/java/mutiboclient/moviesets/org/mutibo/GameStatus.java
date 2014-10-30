package mutiboclient.moviesets.org.mutibo;

public class GameStatus {
    private String userName;

    private int availableHints = 3;

    private int wrongAnswers = 0;

    private int highScore = 0;

    private int score = 0;

    private int currentQuestion = 1;

    GameStatus() {}

    public void setAvailableHints(int availableHints) {
        this.availableHints = availableHints;
    }

    public int getAvailableHints() {
        return availableHints;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
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
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }
}
