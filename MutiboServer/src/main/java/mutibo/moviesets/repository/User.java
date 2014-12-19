package mutibo.moviesets.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@Column(name="user_id")
	private String userId;
	
	@Column(name="highscore")
	private Integer highScore;
	
	public User() {}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}

	public void setHighScore(Integer highScore) {
		this.highScore = highScore;
	}
	
	public Integer getHighScore() {
		return this.highScore;
	}
}
