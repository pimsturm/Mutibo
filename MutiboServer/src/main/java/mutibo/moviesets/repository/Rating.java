package mutibo.moviesets.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rating {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name="user_id")
	private String userId;

	@Column(name="movieset_id")
	private Long movieSetId;

	private int rating;
	
	public Rating() {}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setMovieSetId(Long movieSetId) {
		this.movieSetId = movieSetId;
	}
	
	public Long getMovieSetId() {
		return this.movieSetId;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public int getRating() {
		return this.rating;
	}
	
}
