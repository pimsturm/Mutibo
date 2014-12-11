package mutiboclient.moviesets.org.service;

import mutiboclient.moviesets.org.unsafe.EasyHttpClient;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;

public class MovieSvc {
    private static MovieSvcApi movieSvc_;
    public static final String CLIENT_ID = "mobile";

    public static synchronized MovieSvcApi init(String server, String user,
                                                String pass) {

        movieSvc_ = new SecuredRestBuilder()
                .setLoginEndpoint(server + MovieSvcApi.TOKEN_PATH)
                .setUsername(user)
                .setPassword(pass)
                .setClientId(CLIENT_ID)
                .setClient(
                        new ApacheClient(new EasyHttpClient()))
                .setEndpoint(server).setLogLevel(RestAdapter.LogLevel.FULL).build()
                .create(MovieSvcApi.class);


        return movieSvc_;
    }
}
