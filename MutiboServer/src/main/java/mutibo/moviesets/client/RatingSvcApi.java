package mutibo.moviesets.client;

import mutibo.moviesets.repository.Rating;

import java.util.ArrayList;
import java.util.Collection;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;


public interface RatingSvcApi {
	public static final String RATING_SVC_PATH = "/rating";
	public static final String RATING_DATA_PATH = RATING_SVC_PATH + "/{user_id}";

	@POST(RATING_SVC_PATH)
	public Boolean addRating(@Body ArrayList<Rating> rating);

	// POST /rating/{user_id}
	// Store in a JPA repository
	@POST(RATING_DATA_PATH)
	public ArrayList<Rating> findRatingByUser(@Query("usr_id") String userId);
}
