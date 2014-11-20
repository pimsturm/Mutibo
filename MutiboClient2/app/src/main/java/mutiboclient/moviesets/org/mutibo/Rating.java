package mutiboclient.moviesets.org.mutibo;

public class Rating {
    private long id;

    private String userId;

    private Long movieSetId;

    private int rating;

    public Rating() {}

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setMovieSetId(Long movieSetId) {
        this.movieSetId = movieSetId;
    }

    public Long getMovieSetId() {
        return this.movieSetId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return this.rating;
    }
}
