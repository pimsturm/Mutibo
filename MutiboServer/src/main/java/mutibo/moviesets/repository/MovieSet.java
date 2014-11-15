package mutibo.moviesets.repository;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="movieset")
public class MovieSet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="movieset_id")
	private long id;
	
	// set of four titles, referring to movie
	//@ElementCollection
	//@CollectionTable
	//private Set<Movie> movies = new HashSet<Movie>();
	@Column(name="movie1_id")
	private long movie1Id;
	
	@Column(name="movie2_id")
	private long movie2Id;

	@Column(name="movie3_id")
	private long movie3Id;

	@Column(name="movie4_id")
	private long movie4Id;

	// The id of the movie that is the correct answer
	private int correctAnswer;
	
	// Explanation of the correct answer
	private String explanation;
	
	// Hint to the correct answer
	private String hint;
	
	// List of ratings with a count per rating
	
	public MovieSet() {
		
	}
	
	public void setMovie1Id(long movieId){
		this.movie1Id = movieId;
	}
	
	public long getMovie1Id() {
		return this.movie1Id;
	}
	
	public void setMovie2Id(long movieId){
		this.movie2Id = movieId;
	}
	
	public long getMovie2Id() {
		return this.movie2Id;
	}
	
	public void setMovie3Id(long movieId){
		this.movie3Id = movieId;
	}
	
	public long getMovie3Id() {
		return this.movie3Id;
	}
	
	public void setMovie4Id(long movieId){
		this.movie4Id = movieId;
	}
	
	public long getMovie4Id() {
		return this.movie4Id;
	}
	
	public void setCorrectAnswer(int answerId) {
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
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	//	public void setMovies(Set<Movie> movies) {
//		this.movies = movies;
//	}
//	
//	public Set<Movie> getMovies() {
//		return this.movies;
//	}

}
