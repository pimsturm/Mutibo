package mutibo.moviesets.controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import mutibo.moviesets.repository.MovieSet;
import mutibo.moviesets.repository.MovieRepository;
import mutibo.moviesets.repository.MovieSetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

@Controller
public class MovieSetSvc {
	public static final String MOVIESET_SVC_PATH = "/movieset";
	public static final String MOVIESET_DATA_PATH = MOVIESET_SVC_PATH + "/{id}";

	@Autowired
	private MovieSetRepository movieSets;
	
	public MovieSetSvc() {
		
	}

	// POST /movieset
	// Store in a JPA repository
	@RequestMapping(value=MOVIESET_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody MovieSet addMovieSet(@RequestBody MovieSet movieSet) {
		return movieSets.save(movieSet);
	}

	// GET /movieset
	// Returns the list of movie sets that have been added to the server as JSON
	@RequestMapping(value=MOVIESET_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<MovieSet> getMovieSetList(){
		return Lists.newArrayList(movieSets.findAll());
	}

	// GET /movieset/{id}
	// Returns the movie set with the given id or 404 if the movie set is not found.
	@RequestMapping(value=MOVIESET_DATA_PATH, method=RequestMethod.GET)
	public @ResponseBody MovieSet getData(@PathVariable("id") long id, HttpServletResponse response) {
		MovieSet movieSet = movieSets.findOne(id);
		if (movieSet == null) {
			// movie set does not exist
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null; 
		}
		return movieSet;
	}

}
