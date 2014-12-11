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

import com.google.common.collect.Lists;


@Controller
public class RatingSvc {
	public static final String RATING_SVC_PATH = "/rating";
	public static final String RATING_DATA_PATH = RATING_SVC_PATH + "/{user_id}";
	public static final String RATING_DATA_LESSTHAN_PATH = RATING_SVC_PATH + "/lessthan/{rating}";
	public static final String RATING_DATA_AVG_PATH = RATING_SVC_PATH + "/avg";

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

	// GET /rating/{user_id}
	@RequestMapping(value=RATING_DATA_PATH, method=RequestMethod.GET)
	public @ResponseBody ArrayList<Rating> findRatingByUser(@PathVariable("usr_id") String userId) {
		return ratings.findByUserId(userId);
	}
	
	// GET /rating/lessthan/{rating}
	@RequestMapping(value=RATING_DATA_LESSTHAN_PATH, method=RequestMethod.GET)
	public @ResponseBody ArrayList<Rating> findByRatingLessThan(@PathVariable("rating") Integer rating) {
		return ratings.findByRatingLessThan(rating);
	}

	// GET /rating/avg
	@RequestMapping(value=RATING_DATA_AVG_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<Rating> getAverageRatingList() {
		List<Object[]> ratingList = ratings.getAverageRating();
		
		ArrayList<Rating> ratingResult = new ArrayList<Rating>();
		
		for (Object[] avgRating: ratingList){
			Rating rating = new Rating();
			rating.setMovieSetId((Long)avgRating[0]);
			Double d = (Double)avgRating[1];
			rating.setRating(d.intValue());
			ratingResult.add(rating);
		}
		return ratingResult;
	}

}
