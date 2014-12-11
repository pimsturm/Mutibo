package mutibo.moviesets.controller;

import java.util.Collection;

import org.springframework.stereotype.Controller;

import mutibo.moviesets.repository.User;
import mutibo.moviesets.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class UserSvc {
	public static final String USER_SVC_PATH = "/user";
	public static final String USER_DATA_PATH = USER_SVC_PATH + "/findbyuserid";

	@Autowired
	UserRepository users;
	
	public UserSvc(){}

	// GET /user/findbyuserid
	@RequestMapping(value=USER_DATA_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<User> findUser(@PathVariable("user_id") String userId) {
		return users.findByUserId(userId);
	}

	// POST /user
	// Store in a JPA repository
	@RequestMapping(value=USER_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody Boolean updateUser(@RequestBody User user) {

		Collection<User> storedUsers = users.findByUserId(user.getUserId());
		for (User retrievedUser : storedUsers) {
			retrievedUser.setHighScore(user.getHighScore());
		}
		users.save(storedUsers);
		
		return true;
	}

}
