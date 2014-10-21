package mutiboclient.moviesets.org.mutibo;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import mutiboclient.moviesets.org.mutibo.MovieSvcApi;
import retrofit.converter.GsonConverter;

public class MovieSvc {
    private static MovieSvcApi movieSvc_;

    public static synchronized MovieSvcApi init(String server, String user,
                                                String pass) {

        movieSvc_ =
                new RestAdapter.Builder()
                        .setEndpoint(server).setLogLevel(LogLevel.FULL).build()
                        .create(MovieSvcApi.class);

        return movieSvc_;
    }
}
