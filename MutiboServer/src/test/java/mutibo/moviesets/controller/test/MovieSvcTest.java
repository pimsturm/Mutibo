package mutibo.moviesets.controller.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import mutibo.moviesets.controller.MovieSvc;
import mutibo.moviesets.repository.Movie;
import mutibo.moviesets.repository.MovieRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MovieSvcTest {
	@Mock
	private MovieRepository movieRepository;

	@InjectMocks
	private MovieSvc movieSvc;
	
	private Movie movie = TestData.randomMovie();
	
	@Before
	public void setUp() {
		// Process mock annotations and inject the mock MovieRepository
		// into the MovieSvc object
		MockitoAnnotations.initMocks(this);

		when(movieRepository.findAll()).thenReturn(Arrays.asList(movie));
	}

	@Test
	public void testMovieAddAndList() throws Exception {

		// Ensure that calling addMovie works
		Movie testMovie = movieSvc.addMovie(movie);
		assertEquals(testMovie, movie);

		// Make sure that the Movie we added is in the list
		Collection<Movie> movies = movieSvc.getMovieList();
		assertTrue(movies.contains(movie));
	}

}
