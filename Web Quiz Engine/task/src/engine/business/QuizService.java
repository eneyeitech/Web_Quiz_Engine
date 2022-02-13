package engine.business;

import engine.persistence.IAnswersRepository;
import engine.persistence.IOptionsRepository;
import engine.persistence.IQuizRepository;
import engine.persistence.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {
    //private final QuizRepository quizRepository;
    private final IQuizRepository quizRepository;
    private final IAnswersRepository answersRepository;
    private final IOptionsRepository optionsRepository;

    @Autowired
    public QuizService(IOptionsRepository optionsRepository,
                       IAnswersRepository answersRepository,
                       IQuizRepository quizRepository) {
        this.quizRepository = quizRepository;
        this.answersRepository = answersRepository;
        this.optionsRepository = optionsRepository;
    }

    public Quiz save(Quiz toSave) {
        return quizRepository.save(toSave);
    }

    public Quiz saveQuiz(Quiz toSave) {
        updateQuizAnswers(toSave);
        Quiz savedQuiz = quizRepository.save(toSave);
        updateQuizOptions(savedQuiz);
        return savedQuiz;
    }

    public Answers saveAnswers(Answers toSave) {
        return answersRepository.save(toSave);
    }

    public Options saveOptions(Options toSave) {
        return optionsRepository.save(toSave);
    }

    public Quiz findQuizById(long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        Quiz quizToReturn = quiz.orElse(null);
        if (quizToReturn != null) {
            updateQuiz(quizToReturn);
        }
        return quizToReturn;
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

    public Collection<Object> all() {
        Collection<Object> quizCollection = new ArrayList<>();
        quizRepository.findAll().forEach(quiz -> {
            updateQuiz(quiz);
            quizCollection.add(Map.of(
                    "id", quiz.getId(),
                    "title", quiz.getTitle(),
                    "text", quiz.getText(),
                    "options",quiz.getOptions()
            ));
        });
        return quizCollection;
    }

    private void updateQuizAnswers(Quiz quizToUpdate) {
        List<Integer> answers = quizToUpdate.getAnswer();

        for (Integer i: answers) {
            Answers answerToSave = new Answers();
            answerToSave.setAnswer(i);
            Answers savedAnswer = saveAnswers(answerToSave);
            quizToUpdate.addQuizAnswer(savedAnswer);
        }


    }

    private void updateQuizOptions(Quiz quizToUpdate) {
        List<String> options = quizToUpdate.getOptions();
        for (String s: options) {
            Options optionToSave = new Options();
            optionToSave.setOption(s);
            optionToSave.setQuiz(quizToUpdate);
            Options savedOption = saveOptions(optionToSave);
            quizToUpdate.addQuizOption(savedOption);
        }
    }
}
