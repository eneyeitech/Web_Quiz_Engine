package engine.persistence;

import engine.business.Completion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompletionRepository extends CrudRepository<Completion, Long> {
}
