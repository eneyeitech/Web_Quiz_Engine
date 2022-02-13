package engine.persistence;

import engine.business.Quiz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizRepository extends CrudRepository<Quiz, Long> {
}
