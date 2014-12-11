package mutiboclient.moviesets.org.service;

import java.util.ArrayList;
import java.util.Collection;

import mutiboclient.moviesets.org.mutibo.Movie;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface MovieSvcApi {
    public static final String MOVIE_SVC_PATH = "/movie";
    public static final String MOVIE_DATA_PATH = MOVIE_SVC_PATH + "/{id}";
    public static final String MOVIE_DATA_USED_PATH = MOVIE_SVC_PATH + "/used";
    public static final String TOKEN_PATH = "/oauth/token";


    public static final String TITLE_PARAMETER = "title";

    // The path to search movies by title
    public static final String MOVIE_TITLE_SEARCH_PATH = MOVIE_SVC_PATH
            + "/find";

    @GET(MOVIE_SVC_PATH)
    public ArrayList<Movie> getMovieList();

    @POST(MOVIE_DATA_USED_PATH)
    public Collection<Movie> getUsedMovie(@Body ArrayList<Long> usedIds);

    @POST(MOVIE_SVC_PATH)
    public Movie addMovie(@Body Movie movie);

    @GET(MOVIE_TITLE_SEARCH_PATH)
    public Collection<Movie> findByTitle(@Query(TITLE_PARAMETER) String title);


}
