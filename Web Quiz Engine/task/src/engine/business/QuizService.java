package engine.business;

import engine.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {
    //private final QuizRepository quizRepository;
    private final IQuizRepository quizRepository;
    private final IAnswersRepository answersRepository;
    private final IOptionsRepository optionsRepository;
    private final ICompletionRepository completionRepository;

    @Autowired
    public QuizService(IOptionsRepository optionsRepository,
                       IAnswersRepository answersRepository,
                       IQuizRepository quizRepository,
                       ICompletionRepository completionRepository) {
        this.quizRepository = quizRepository;
        this.answersRepository = answersRepository;
        this.optionsRepository = optionsRepository;
        this.completionRepository = completionRepository;
    }

    public Quiz save(Quiz toSave) {
        return quizRepository.save(toSave);
    }

    public void delete(long id) {
        Quiz quizToDelete = findQuizById(id);
        deleteOptions(quizToDelete);
        deleteCompletions(quizToDelete);
        quizRepository.delete(quizToDelete);
        deleteAnswers(quizToDelete);
    }

    public void deleteCompletions(Quiz toDelete){

        List<Completion> completionList = toDelete.getQuizCompletion();
        for (Completion c: completionList) {
            completionRepository.delete(c);
        }
    }

    public void deleteOptions(Quiz toDelete){

        List<Options> optionsList = toDelete.getQuizOptions();
        for (Options o: optionsList) {
            optionsRepository.delete(o);
        }
    }

    public void deleteAnswers(Quiz toDelete){
        Set<Answers> answersSet = toDelete.getQuizAnswers();


        for (Answers a: answersSet) {
            answersRepository.delete(a);
        }
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

    public boolean hasQuiz(long id) {
        Quiz quiz = findQuizById(id);
        if (quiz != null) {
            return true;
        }
        return false;
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
