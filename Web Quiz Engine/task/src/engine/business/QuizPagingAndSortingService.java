package engine.business;

import engine.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizPagingAndSortingService {
    private final IQuizRepository quizRepository;
    private final IQuizPagingAndSortingRepository quizPagingAndSortingRepository;
    private final IAnswersRepository answersRepository;
    private final IOptionsRepository optionsRepository;

    @Autowired
    public QuizPagingAndSortingService(IOptionsRepository optionsRepository,
                                       IAnswersRepository answersRepository,
                                       IQuizRepository quizRepository,
                                       IQuizPagingAndSortingRepository quizPagingAndSortingRepository) {
        this.quizRepository = quizRepository;
        this.answersRepository = answersRepository;
        this.optionsRepository = optionsRepository;
        this.quizPagingAndSortingRepository = quizPagingAndSortingRepository;
    }

    public Object getAllQuiz(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Quiz> pagedResult = quizPagingAndSortingRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            List<Object> quizCollection = new ArrayList<>();
            pagedResult.getContent().forEach(quiz -> {
                updateQuiz(quiz);
                quizCollection.add(Map.of(
                        "id", quiz.getId(),
                        "title", quiz.getTitle(),
                        "text", quiz.getText(),
                        "options",quiz.getOptions()
                ));
            });
            return Map.of(
                    "content",quizCollection
            );
        } else {
            return Map.of(
                    "content",new ArrayList<>()
            );
        }


    }

    private void updateQuiz(Quiz quizToUpdate) {
        Set<Answers> answersSet = quizToUpdate.getQuizAnswers();
        List<Options> optionsList = quizToUpdate.getQuizOptions();

        for (Answers a: answersSet) {
            quizToUpdate.addAnswer(a.getAnswer());
        }

        for (Options o: optionsList) {
            quizToUpdate.addOption(o.getOption());
        }
    }

}
