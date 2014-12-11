package mutiboclient.moviesets.org.service;

import mutiboclient.moviesets.org.unsafe.EasyHttpClient;
import retrofit.RestAdapter;
import retrofit.client.ApacheClient;

public class UserSvc {
    private static UserSvcApi userSvc_;
    public static final String CLIENT_ID = "mobile";

    public static synchronized UserSvcApi init(String server, String user,
                                                 String pass) {

        userSvc_ = new SecuredRestBuilder()
                .setLoginEndpoint(server + UserSvcApi.TOKEN_PATH)
                .setUsername(user)
                .setPassword(pass)
                .setClientId(CLIENT_ID)
                .setClient(
                        new ApacheClient(new EasyHttpClient()))
                .setEndpoint(server).setLogLevel(RestAdapter.LogLevel.FULL).build()
                .create(UserSvcApi.class);


        return userSvc_;
    }
}
