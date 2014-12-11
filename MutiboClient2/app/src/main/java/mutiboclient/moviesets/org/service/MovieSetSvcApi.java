package mutiboclient.moviesets.org.service;


import java.util.Collection;

import mutiboclient.moviesets.org.mutibo.MovieSet;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface MovieSetSvcApi {
    // The path where we expect the MovieSetSvc to live
    public static final String MOVIESET_SVC_PATH = "/movieset";
    public static final String TOKEN_PATH = "/oauth/token";

    @GET(MOVIESET_SVC_PATH)
    public Collection<MovieSet> getMovieSetList();

    @POST(MOVIESET_SVC_PATH)
    public MovieSet addMovieSet(@Body MovieSet movieSet);

}
