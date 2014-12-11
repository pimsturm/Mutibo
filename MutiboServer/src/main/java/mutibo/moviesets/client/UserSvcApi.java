package mutibo.moviesets.client;

import mutibo.moviesets.repository.User;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface UserSvcApi {
	public static final String USER_SVC_PATH = "/user";
	public static final String USER_DATA_PATH = USER_SVC_PATH + "/findbyuserid";

	@GET(USER_DATA_PATH)
	public User findByUserId(@Query("user_id") String userId);
	
	@POST(USER_SVC_PATH)
	public Boolean updateUser(User user);
}
