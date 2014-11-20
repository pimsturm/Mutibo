package mutiboclient.moviesets.org.service;

import retrofit.RestAdapter;

public class RatingSvc {
    private static RatingSvcApi ratingSvc_;

    public static synchronized RatingSvcApi init(String server, String user,
                                                String pass) {

        ratingSvc_ =
                new RestAdapter.Builder()
                        .setEndpoint(server).setLogLevel(RestAdapter.LogLevel.FULL).build()
                        .create(RatingSvcApi.class);

        return ratingSvc_;
    }
}
