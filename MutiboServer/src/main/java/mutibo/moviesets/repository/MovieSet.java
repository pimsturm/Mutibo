package mutibo.moviesets.repository;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MovieSet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	// set of four titles, referring to movie
	@ElementCollection
	@CollectionTable
	private Set<Movie> movies = new HashSet<Movie>();
	
	// The id of the movie that is the correct answer
	private long correctAnswer;
	
	// Explanation of the correct answer
	private String explanation;
	
	// Hint to the correct answer
	private String hint;
	
	// List of ratings with a count per rating
	
	public MovieSet() {
		
	}
	
	public void setCorrectAnswer(long answerId) {
		this.correctAnswer = answerId;
	}
	
	public long getCorrectAnswer() {
		return this.correctAnswer;
	}
	
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	public String getExplanation() {
		return this.explanation;
	}
	
	public void setHint(String hint) {
		this.hint = hint;
	}
	
	public String getHint() {
		return this.hint;
	}
	
	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}
	
	public Set<Movie> getMovies() {
		return this.movies;
	}

}
