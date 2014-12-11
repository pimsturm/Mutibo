package mutiboclient.moviesets.org.service;

import java.util.Collection;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

import mutiboclient.moviesets.org.mutibo.User;

public interface UserSvcApi {
    public static final String USER_SVC_PATH = "/user";
    public static final String USER_DATA_PATH = USER_SVC_PATH + "/findbyuserid";
    public static final String TOKEN_PATH = "/oauth/token";

    @GET(USER_DATA_PATH)
    public Collection<User> findByUserId(@Query("user_id") String userId);

    @POST(USER_SVC_PATH)
    public Boolean updateUser(User user);
}
