package mutiboclient.moviesets.org.service;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

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
