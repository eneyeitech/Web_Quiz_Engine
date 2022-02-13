package engine.persistence;

import engine.business.Answers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnswersRepository extends CrudRepository<Answers, Long> {
}
