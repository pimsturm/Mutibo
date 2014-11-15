package mutiboclient.moviesets.org.service;

import retrofit.RestAdapter;

public class MovieSetSvc {
    private static MovieSetSvcApi movieSetSvc_;

    public static synchronized MovieSetSvcApi init(String server, String user,
                                                String pass) {

        movieSetSvc_ =
                new RestAdapter.Builder()
                        .setEndpoint(server).setLogLevel(RestAdapter.LogLevel.FULL).build()
                        .create(MovieSetSvcApi.class);

        return movieSetSvc_;
    }
}
