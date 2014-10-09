package mutibo.moviesets.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MovieSet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

}
