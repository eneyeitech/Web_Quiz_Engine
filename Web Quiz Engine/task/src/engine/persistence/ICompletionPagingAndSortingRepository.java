package engine.persistence;

import engine.business.Completion;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompletionPagingAndSortingRepository extends PagingAndSortingRepository<Completion, Long> {
}
