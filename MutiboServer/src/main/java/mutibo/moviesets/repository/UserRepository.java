package mutibo.moviesets.repository;


import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, String> {
	public Collection<User> findByUserId(@Param("user_id") String userId);

}
