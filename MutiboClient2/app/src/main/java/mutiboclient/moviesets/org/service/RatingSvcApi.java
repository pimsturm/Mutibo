package mutiboclient.moviesets.org.service;

import java.util.ArrayList;
import java.util.Collection;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

import mutiboclient.moviesets.org.mutibo.Rating;

public interface RatingSvcApi {
    public static final String RATING_SVC_PATH = "/rating";
    public static final String RATING_DATA_PATH = RATING_SVC_PATH + "/{user_id}";
    public static final String RATING_DATA_AVG_PATH = RATING_SVC_PATH + "/avg";
    public static final String TOKEN_PATH = "/oauth/token";

    @POST(RATING_SVC_PATH)
    public Boolean addRating(@Body ArrayList<Rating> rating);

    // POST /rating/{user_id}
    // Store in a JPA repository
    @POST(RATING_DATA_PATH)
    public ArrayList<Rating> findRatingByUser(@Query("user_id") String userId);

    // GET /rating/avg
    @GET(RATING_DATA_AVG_PATH)
    public ArrayList<Rating> getAverageRating();
}
