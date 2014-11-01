package mutiboclient.moviesets.org.mutibo;

import java.util.ArrayList;

public class MovieSet {
    private long id;

    private ArrayList<String> movieTitles;

    private String hint;

    private String explanation;

    private int rating;

    private int correctAnswer;

    MovieSet () {
        movieTitles = new ArrayList<String>();
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

    public String getExplanation() {return this.explanation;}

    public void setExplanation(String explanation) {this.explanation = explanation;}

    public int getRating() {return this.rating;}

    public void setRating(int rating) {this.rating = rating;}

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {this.correctAnswer = correctAnswer;}
}
