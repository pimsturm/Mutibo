package mutiboclient.moviesets.org.mutibo;

import java.util.ArrayList;

public class MovieSet {
    private long id;

    private long movie1Id;
    private long movie2Id;
    private long movie3Id;
    private long movie4Id;

    private ArrayList<String> movieTitles;

    private String hint;

    private String explanation;

    private int rating;

    private int correctAnswer;

    private int givenAnswer;

    private Boolean askedHint;

    MovieSet () {
        movieTitles = new ArrayList<String>();
        givenAnswer = -1;
        askedHint = false;
        rating = 0;
    }

    public void setMovie1Id(long movieId){
        this.movie1Id = movieId;
    }

    public long getMovie1Id() {
        return this.movie1Id;
    }

    public void setMovie2Id(long movieId){
        this.movie2Id = movieId;
    }

    public long getMovie2Id() {
        return this.movie2Id;
    }

    public void setMovie3Id(long movieId){
        this.movie3Id = movieId;
    }

    public long getMovie3Id() {
        return this.movie3Id;
    }

    public void setMovie4Id(long movieId){
        this.movie4Id = movieId;
    }

    public long getMovie4Id() {
        return this.movie4Id;
    }

    public void addMovieTitle(String title) {
        this.movieTitles.add(title);
    }

    public void clearMovieTitles() {
        this.movieTitles.clear();
    }

    public ArrayList<String> getMovieTitles() {
        return this.movieTitles;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public String getHint() {return this.hint;}

    public void setHint (String hint) {this.hint = hint;}

    public Boolean getAskedHint() {return this.askedHint;}

    public void setAskedHint (Boolean askedHint) {this.askedHint = askedHint;}

    public String getExplanation() {return this.explanation;}

    public void setExplanation(String explanation) {this.explanation = explanation;}

    public int getRating() {return this.rating;}

    public void setRating(int rating) {this.rating = rating;}

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {this.correctAnswer = correctAnswer;}

    public int getGivenAnswer() {return this.givenAnswer;}

    public void setGivenAnswer(int givenAnswer) {this.givenAnswer = givenAnswer;}

    public Boolean isCorrectAnswer() {
        return this.getCorrectAnswer() == this. getGivenAnswer();
    }
}
