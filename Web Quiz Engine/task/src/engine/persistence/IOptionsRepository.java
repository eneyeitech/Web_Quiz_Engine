package engine.persistence;

import engine.business.Options;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOptionsRepository extends CrudRepository<Options, Long> {
}
