package engine.persistence;

import engine.business.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizPagingAndSortingRepository extends PagingAndSortingRepository<Quiz, Long> {
}
