package mutiboclient.moviesets.org.mutibo;

public class Movie {

    private long id;

    private String movieTitle;

    public Movie() {

    }

    public Movie(String title) {
        this.movieTitle = title;

    }

    public void setMovieTitle(String title) {
        this.movieTitle = title;
    }

    public String getMovieTitle() {
        return this.movieTitle;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

}
