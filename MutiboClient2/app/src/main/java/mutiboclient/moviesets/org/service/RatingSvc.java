package mutiboclient.moviesets.org.service;

import mutiboclient.moviesets.org.unsafe.EasyHttpClient;
import retrofit.RestAdapter;
import retrofit.client.ApacheClient;

public class RatingSvc {
    private static RatingSvcApi ratingSvc_;
    public static final String CLIENT_ID = "mobile";

    public static synchronized RatingSvcApi init(String server, String user,
                                                String pass) {

        ratingSvc_ = new SecuredRestBuilder()
                .setLoginEndpoint(server + RatingSvcApi.TOKEN_PATH)
                .setUsername(user)
                .setPassword(pass)
                .setClientId(CLIENT_ID)
                .setClient(
                        new ApacheClient(new EasyHttpClient()))
                .setEndpoint(server).setLogLevel(RestAdapter.LogLevel.FULL).build()
                .create(RatingSvcApi.class);


        return ratingSvc_;
    }
}
