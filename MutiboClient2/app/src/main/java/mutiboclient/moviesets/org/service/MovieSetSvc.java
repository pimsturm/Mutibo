package mutiboclient.moviesets.org.service;

import mutiboclient.moviesets.org.unsafe.EasyHttpClient;
import retrofit.RestAdapter;
import retrofit.client.ApacheClient;

public class MovieSetSvc {
    private static MovieSetSvcApi movieSetSvc_;

    public static final String CLIENT_ID = "mobile";

    public static synchronized MovieSetSvcApi init(String server, String user,
                                                String pass) {

        movieSetSvc_ =
                new RestAdapter.Builder()
                        .setEndpoint(server).setLogLevel(RestAdapter.LogLevel.FULL).build()
                        .create(MovieSetSvcApi.class);

        movieSetSvc_ = new SecuredRestBuilder()
                .setLoginEndpoint(server + MovieSetSvcApi.TOKEN_PATH)
                .setUsername(user)
                .setPassword(pass)
                .setClientId(CLIENT_ID)
                .setClient(
                        new ApacheClient(new EasyHttpClient()))
                .setEndpoint(server).setLogLevel(RestAdapter.LogLevel.FULL).build()
                .create(MovieSetSvcApi.class);


        return movieSetSvc_;
    }
}
