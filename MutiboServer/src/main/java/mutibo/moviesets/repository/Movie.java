package mutibo.moviesets.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String movieTitle;
	
	public Movie() {
		
	}
	
	public Movie(String title) {
		this.movieTitle = title;
		
	}
	
	public void setMovieTitle(String title) {
		this.movieTitle = title;
	}
	
	public String getMovieTitle() {
		return this.movieTitle;
	}

}
