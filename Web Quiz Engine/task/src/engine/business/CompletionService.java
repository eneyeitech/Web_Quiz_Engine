package engine.business;

import engine.persistence.ICompletionPagingAndSortingRepository;
import engine.persistence.ICompletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompletionService {

    private final ICompletionRepository completionRepository;
    private final ICompletionPagingAndSortingRepository completionPagingAndSortingRepository;

    @Autowired
    public CompletionService(
            ICompletionRepository completionRepository,
            ICompletionPagingAndSortingRepository completionPagingAndSortingRepository
    ) {
      this.completionRepository = completionRepository;
      this.completionPagingAndSortingRepository = completionPagingAndSortingRepository;
    }

    public Completion save(Completion toSave) {
        return completionRepository.save(toSave);
    }
    public Object getAllCompletion(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        Page<Completion> pagedResult = completionPagingAndSortingRepository.findAll(paging);

        UserDetailImpl details = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;
        if(details != null) {
            user = details.getUser();
        }
        if(pagedResult.hasContent()) {
            List<Object> completionCollection = new ArrayList<>();
            User finalUser = user;
            pagedResult.getContent().forEach(completion -> {
                if (completion.getUserId() == finalUser.getId()) {
                    completionCollection.add(Map.of(
                            "id", completion.getQuiz().getId(),
                            "completedAt", completion.getCompletedAt()
                    ));
                }
            });

            return Map.of(
                    "content",completionCollection
            );
        } else {
            return Map.of(
                    "content",new ArrayList<>()
            );
        }


    }
}
