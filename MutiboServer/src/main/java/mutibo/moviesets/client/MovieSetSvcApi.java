package mutibo.moviesets.client;

import java.util.Collection;

import mutibo.moviesets.repository.MovieSet;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface MovieSetSvcApi {
	// The path where we expect the MovieSetSvc to live
	public static final String MOVIESET_SVC_PATH = "/movieset";

	@GET(MOVIESET_SVC_PATH)
	public Collection<MovieSet> getMovieSetList();

	@POST(MOVIESET_SVC_PATH)
	public MovieSet addMovieSet(@Body MovieSet movieSet);

}
