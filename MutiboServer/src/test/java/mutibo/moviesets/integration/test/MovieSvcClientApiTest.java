package mutibo.moviesets.integration.test;

import static org.junit.Assert.*;

import java.util.Collection;

import mutibo.moviesets.client.MovieSvcApi;
import mutibo.moviesets.repository.Movie;
import mutibo.moviesets.controller.test.TestData;

import org.junit.Test;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

public class MovieSvcClientApiTest {

	private final String TEST_URL = "http://localhost:8080";

	private MovieSvcApi movieService = new RestAdapter.Builder()
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(MovieSvcApi.class);

	private Movie movie = mutibo.moviesets.controller.test.TestData.randomMovie();
	
	/**
	 * This test creates a Video, adds the Video to the VideoSvc, and then
	 * checks that the Video is included in the list when getVideoList() is
	 * called.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testVideoAddAndList() throws Exception {
		
		// Add the movie
		Movie testMovie = movieService.addMovie(movie);
		assertEquals(testMovie, movie);

		// We should get back the movie that we added above
		Collection<Movie> movies = movieService.getMovieList();
		assertTrue(movies.contains(movie));
	}


}
