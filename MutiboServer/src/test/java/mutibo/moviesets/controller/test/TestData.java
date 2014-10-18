package mutibo.moviesets.controller.test;

import java.util.UUID;

import mutibo.moviesets.repository.Movie;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestData {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * Construct and return a Movie object with a
	 * random name, 
	 * 
	 * @return
	 */
	public static Movie randomMovie() {
		// Information about the movie
		// Construct a random identifier using Java's UUID class
		String id = UUID.randomUUID().toString();
		String title = "Movie-"+id;
		return new Movie(title);
	}
	
	/**
	 *  Convert an object to JSON using Jackson's ObjectMapper
	 *  
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static String toJson(Object o) throws Exception{
		return objectMapper.writeValueAsString(o);
	}

}
