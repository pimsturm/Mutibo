package mutibo.moviesets.controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import mutibo.moviesets.repository.Movie;
import mutibo.moviesets.repository.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

@Controller
public class MovieSvc {

	public static final String MOVIE_SVC_PATH = "/movie";
	public static final String MOVIE_DATA_PATH = MOVIE_SVC_PATH + "/{id}";

	@Autowired
	private MovieRepository movies;
	
	public MovieSvc() {
		
	}

	// POST /movie
	// Store in a JPA repository
	@RequestMapping(value=MOVIE_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody Movie addMovie(@RequestBody Movie movie) {
		return movies.save(movie);
	}

	// GET /movie
	// Returns the list of movies that have been added to the server as JSON
	@RequestMapping(value=MOVIE_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<Movie> getMovieList(){
		return Lists.newArrayList(movies.findAll());
	}

	// GET /movie/{id}
	// Returns the movie with the given id or 404 if the movie is not found.
	@RequestMapping(value=MOVIE_DATA_PATH, method=RequestMethod.GET)
	public @ResponseBody Movie getData(@PathVariable("id") long id, HttpServletResponse response) {
		Movie movie = movies.findOne(id);
		if (movie == null) {
			// movie does not exist
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null; 
		}
		return movie;
	}

}
