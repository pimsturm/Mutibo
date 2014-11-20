package mutibo.moviesets.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import mutibo.moviesets.repository.Rating;
import mutibo.moviesets.repository.RatingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class RatingSvc {
	public static final String RATING_SVC_PATH = "/rating";
	public static final String RATING_DATA_PATH = RATING_SVC_PATH + "/{user_id}";

	@Autowired
	private RatingRepository ratings;
	
	public RatingSvc() {}
	
	// POST /rating
	// Store in a JPA repository
	@RequestMapping(value=RATING_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody Boolean addRating(@RequestBody ArrayList<Rating> rating) {
		
		// New ratings by the same user always replace the previous ratings
		ratings.delete(rating);
		ratings.save(rating);
		
		return true;
	}

	// POST /rating/{user_id}
	// Store in a JPA repository
	@RequestMapping(value=RATING_DATA_PATH, method=RequestMethod.POST)
	public @ResponseBody ArrayList<Rating> findRatingByUser(@PathVariable("usr_id") String userId) {
		return ratings.findByUserId(userId);
	}


}
