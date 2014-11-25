package mutibo.moviesets.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {

	public ArrayList<Rating> findByUserId(String userId);
	
	public ArrayList<Rating> findByRatingLessThan(Integer rating);
	
	@Query("Select movieSetId, avg(rating) from Rating group by movieSetId")
	List<Object[]> getAverageRating();
}

