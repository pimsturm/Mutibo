package mutiboclient.moviesets.org.mutibo;

/**
 * Created by Pim on 21-10-2014.
 */
import java.util.Collection;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface MovieSvcApi {
    public static final String TITLE_PARAMETER = "title";

    // The path where we expect the MovieSvc to live
    public static final String MOVIE_SVC_PATH = "/movie";

    // The path to search movies by title
    public static final String MOVIE_TITLE_SEARCH_PATH = MOVIE_SVC_PATH
            + "/find";

    @GET(MOVIE_SVC_PATH)
    public Collection<Movie> getMovieList();

    @POST(MOVIE_SVC_PATH)
    public Movie addMovie(@Body Movie movie);

    @GET(MOVIE_TITLE_SEARCH_PATH)
    public Collection<Movie> findByTitle(@Query(TITLE_PARAMETER) String title);


}
