package mutiboclient.moviesets.org.mutibo;

public class User {
    private String userId;

    private Integer highScore;

    public User() {}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setHighScore(Integer highScore) {
        this.highScore = highScore;
    }

    public Integer getHighScore() {
        return this.highScore;
    }
}
