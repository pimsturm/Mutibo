package mutibo.moviesets.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {

	public ArrayList<Rating> findByUserId(String userId);
}

